package com.mgmtp.internship.experiences.controllers.app;

import com.mgmtp.internship.experiences.config.security.CustomUserDetails;
import com.mgmtp.internship.experiences.dto.UserProfileDTO;
import com.mgmtp.internship.experiences.services.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit test for user profile controller.
 *
 * @author thuynh
 */
@RunWith(MockitoJUnitRunner.class)
public class UserProfileControllerTest {

    private static final String PROFILE_URL = "/profile";
    private static final UserProfileDTO USER_PROFILE_DTO = new UserProfileDTO(1L, "display name");
    private static final String USER_PROFILE_MODEL_TAG = "userProfile";
    private static final String USERNAME_MODEL_TAG = "username";
    private static final String DISPLAY_NAME_FIELD = "displayName";
    private static final String IMAGE_ID_FIELD = "imageId";
    private static final String EXPECTED_VIEW_NAME = "user/profile";
    private static final CustomUserDetails CUSTOM_USER_DETAILS = new CustomUserDetails(1L, new UserProfileDTO(1L, "display"), "name", "pass", Collections.emptyList());
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileControllerTest.class);


    private MockMvc mockMvc;
    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserProfileController userProfileController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileController).build();
    }

    @Test
    public void shouldGetProfileShowOnProfilePage() {
        when(userService.getCurrentUser()).thenReturn(CUSTOM_USER_DETAILS);

        try {
            mockMvc.perform(get(PROFILE_URL))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute(USER_PROFILE_MODEL_TAG, CUSTOM_USER_DETAILS.getUserProfile()))
                    .andExpect(model().attribute(USERNAME_MODEL_TAG, CUSTOM_USER_DETAILS.getUsername()))
                    .andExpect(view().name(EXPECTED_VIEW_NAME));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void shouldRedirectLoginPageIfNotLogged() {
        when(userService.getCurrentUser()).thenReturn(null);

        try {
            mockMvc.perform(get(PROFILE_URL))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/login"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void shouldRedirectLoginPageOnUpdateProfileIfNotLogged() {
        when(userService.getCurrentUser()).thenReturn(null);

        try {
            mockMvc.perform(post(PROFILE_URL)
                    .param(IMAGE_ID_FIELD, String.valueOf(USER_PROFILE_DTO.getImageId()))
                    .param(DISPLAY_NAME_FIELD, USER_PROFILE_DTO.getDisplayName()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/login"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void shouldShowProfilePageIfUpdateProfileSuccess() {
        when(userService.getCurrentUser()).thenReturn(CUSTOM_USER_DETAILS);
        when(userService.checkExitDisplayName(USER_PROFILE_DTO.getDisplayName(), CUSTOM_USER_DETAILS.getId())).thenReturn(false);
        when(userService.updateProfile(CUSTOM_USER_DETAILS.getId(), USER_PROFILE_DTO)).thenReturn(true);

        try {
            mockMvc.perform(post(PROFILE_URL)
                    .param(IMAGE_ID_FIELD, String.valueOf(USER_PROFILE_DTO.getImageId()))
                    .param(DISPLAY_NAME_FIELD, USER_PROFILE_DTO.getDisplayName()))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("success", "Update profile success."))
                    .andExpect(model().attribute(USER_PROFILE_MODEL_TAG, USER_PROFILE_DTO))
                    .andExpect(model().attribute(USERNAME_MODEL_TAG, CUSTOM_USER_DETAILS.getUsername()))
                    .andExpect(view().name(EXPECTED_VIEW_NAME));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void shouldShowErrorOnProfilePageIfUpdateProfileFailed() {
        when(userService.getCurrentUser()).thenReturn(CUSTOM_USER_DETAILS);
        when(userService.checkExitDisplayName(USER_PROFILE_DTO.getDisplayName(), CUSTOM_USER_DETAILS.getId())).thenReturn(false);
        when(userService.updateProfile(CUSTOM_USER_DETAILS.getId(), USER_PROFILE_DTO)).thenReturn(false);

        try {
            mockMvc.perform(post(PROFILE_URL)
                    .param(IMAGE_ID_FIELD, String.valueOf(USER_PROFILE_DTO.getImageId()))
                    .param(DISPLAY_NAME_FIELD, USER_PROFILE_DTO.getDisplayName()))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("error", "Can't update profile."))
                    .andExpect(model().attribute(USER_PROFILE_MODEL_TAG, USER_PROFILE_DTO))
                    .andExpect(model().attribute(USERNAME_MODEL_TAG, CUSTOM_USER_DETAILS.getUsername()))
                    .andExpect(view().name(EXPECTED_VIEW_NAME));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void shouldShowErrorOnProfilePageIfDisplayNameAlready() {
        when(userService.getCurrentUser()).thenReturn(CUSTOM_USER_DETAILS);
        when(userService.checkExitDisplayName(USER_PROFILE_DTO.getDisplayName(), CUSTOM_USER_DETAILS.getId())).thenReturn(true);

        try {
            mockMvc.perform(post(PROFILE_URL)
                    .param(IMAGE_ID_FIELD, String.valueOf(USER_PROFILE_DTO.getImageId()))
                    .param(DISPLAY_NAME_FIELD, USER_PROFILE_DTO.getDisplayName()))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeHasFieldErrorCode(USER_PROFILE_MODEL_TAG, DISPLAY_NAME_FIELD, "error." + USER_PROFILE_MODEL_TAG))
                    .andExpect(model().attributeHasFieldErrors(USER_PROFILE_MODEL_TAG, DISPLAY_NAME_FIELD))
                    .andExpect(model().attribute(USER_PROFILE_MODEL_TAG, USER_PROFILE_DTO))
                    .andExpect(model().attribute(USERNAME_MODEL_TAG, CUSTOM_USER_DETAILS.getUsername()))
                    .andExpect(view().name(EXPECTED_VIEW_NAME));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
