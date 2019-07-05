package com.mgmtp.internship.experiences.controllers.api;

import com.mgmtp.internship.experiences.config.security.CustomUserDetails;
import com.mgmtp.internship.experiences.dto.ImageDTO;
import com.mgmtp.internship.experiences.dto.UserProfileDTO;
import com.mgmtp.internship.experiences.exceptions.ApiException;
import com.mgmtp.internship.experiences.services.ImageService;
import com.mgmtp.internship.experiences.services.UserService;
import com.mgmtp.internship.experiences.services.impl.ImageServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class ImageRestControllerTest {

    private static final MockMultipartFile FILE = new MockMultipartFile("photo", "test.jpg", "multipart/form-data", new byte[]{1});
    private static final Long OLD_IMAGE_ID = 2L;
    private static final UserProfileDTO USER_PROFILE_DTO = Mockito.spy(new UserProfileDTO(OLD_IMAGE_ID, "display"));
    private static final CustomUserDetails USER_DETAILS = Mockito.spy(new CustomUserDetails(1L, USER_PROFILE_DTO, "username", "pass", Collections.emptyList()));

    private MockMvc mockMvc;

    @Mock
    private ImageServiceImpl imageService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ImageRestController imageRestController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageRestController).build();
    }

    @Test
    public void addUserImageShouldThrowExceptionIfSizeTooLarge() {
        try {
            Mockito.when(imageService.validateProfilePicture(Mockito.any())).thenReturn(false);

            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/image/user").file(FILE))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().string("{\"status\":\"FAILED\",\"message\":\"The image is too large.\"}"));
        } catch (Exception e) {
        }
    }

    @Test
    public void addUserImageShouldThrowExceptionIfNotAnImage() {
        try {
            Mockito.when(imageService.validateProfilePicture(Mockito.any())).thenThrow(new IOException());

            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/image/user").file(FILE))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().string("{\"status\":\"FAILED\",\"message\":\"Can not process the image.\"}"));
        } catch (Exception e) {
        }
    }

    @Test
    public void addUserImageShouldNotChangeIdIfUpdateFail() {
        try {
            Mockito.when(imageService.validateProfilePicture(Mockito.any())).thenReturn(true);
            Mockito.when(userService.getCurrentUser()).thenReturn(USER_DETAILS);
            Mockito.when(imageService.updateUserImage(USER_DETAILS.getId(), OLD_IMAGE_ID, FILE.getBytes())).thenThrow(new RuntimeException());

            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/image/user").file(FILE))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().string("{\"status\":\"FAILED\",\"message\":\"Server error.\"}"));

            Mockito.verify(USER_PROFILE_DTO, Mockito.never()).setImageId(Mockito.anyLong());
        } catch (Exception e) {
        }
    }

    @Test
    public void addUserImageShouldChangeIdIfUpdateSucceed() {

        Long imageId = 1L;
        try {

            Mockito.when(imageService.validateProfilePicture(Mockito.any())).thenReturn(true);
            Mockito.when(userService.getCurrentUser()).thenReturn(USER_DETAILS);
            Mockito.when(imageService.updateUserImage(USER_DETAILS.getId(), OLD_IMAGE_ID, FILE.getBytes())).thenReturn(imageId);

            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/image/user").file(FILE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("{\"id\":1}"));

            Mockito.verify(USER_PROFILE_DTO).setImageId(imageId);
        } catch (Exception e) {
        }
    }

    @Test
    public void shouldReturnImageById() {
        long imageId = 1;
        ImageDTO expectedImageDTO = new ImageDTO(imageId, new byte[]{(byte) 0xd4, 0x4f});
        Mockito.when(imageService.findImageById(imageId)).thenReturn(expectedImageDTO);

        byte[] actualImage = imageRestController.getImageById(imageId).getBody();

        Assert.assertEquals(actualImage, expectedImageDTO.getImage());
    }

    @Test(expected = ApiException.class)
    public void shouldThrowNotFoundExceptionIfReturnNullImage() {
        long imageId = 1;
        Mockito.when(imageService.findImageById(imageId)).thenReturn(null);
        imageRestController.getImageById(imageId).getBody();
    }

    @Test(expected = ApiException.class)
    public void shouldThrowApiExceptionIfAddImageFailed() throws IOException {
        long activityId = 1;
        MockMultipartFile photo = new MockMultipartFile("data", "data.jpg", "image/jpg", new byte[]{0x4f, 0x3f});
        Mockito.when(imageService.updateActivityImage(activityId, photo.getBytes())).thenReturn(null);
        imageRestController.addImage(activityId, photo);
    }

}
