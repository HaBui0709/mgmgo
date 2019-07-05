package com.mgmtp.internship.experiences.controllers.app;

import com.mgmtp.internship.experiences.config.security.CustomUserDetails;
import com.mgmtp.internship.experiences.dto.ActivityDetailDTO;
import com.mgmtp.internship.experiences.dto.UserProfileDTO;
import com.mgmtp.internship.experiences.services.ActivityService;
import com.mgmtp.internship.experiences.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit Test for Activity Controller
 *
 * @author vhduong
 */
@RunWith(MockitoJUnitRunner.class)
public class ActivityControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityControllerTest.class);
    private static final String ACTIVITY_INFO_ATTRIBUTE = "activityInfo";
    private static final long ACTIVITY_ID = 1;
    private static final String UPDATE_URL = "/activity/update";
    private static final String CREATE_URL = "/activity/create";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String DESC_PARAM = "description";
    private static final ActivityDetailDTO EXPECTED_ACTIVITY_DETAIL_DTO = new ActivityDetailDTO(ACTIVITY_ID, "name", "des", 5, 1L);
    private static final CustomUserDetails EXPECTED_CUSTOM_USER_DETAIL = new CustomUserDetails(1L, new UserProfileDTO(1L, "display"), "username", "pass", Collections.emptyList());

    private MockMvc mockMvc;

    @Mock
    private ActivityService activityService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ActivityController activityController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(activityController).build();
    }

    @Test
    public void shouldGetActivityShowOnActivityPage() {
            Mockito.when(activityService.findById(ACTIVITY_ID)).thenReturn(EXPECTED_ACTIVITY_DETAIL_DTO);

        try {
            mockMvc.perform(get("/activity/1"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute(ACTIVITY_INFO_ATTRIBUTE, EXPECTED_ACTIVITY_DETAIL_DTO))
                    .andExpect(view().name("activity/detail"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void shouldShowErrorPageIfWrongActivityId() {
        Mockito.when(activityService.findById(ACTIVITY_ID)).thenReturn(null);
        final String errorPageName = "error";
        try {
            mockMvc.perform(get("/activity/1"))
                    .andExpect(status().isOk())
                    .andExpect(view().name(errorPageName));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void shouldShowEditPage() {
        Mockito.when(activityService.findById(ACTIVITY_ID)).thenReturn(EXPECTED_ACTIVITY_DETAIL_DTO);

        try {
            mockMvc.perform(get("/activity/update/1"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute(ACTIVITY_INFO_ATTRIBUTE, EXPECTED_ACTIVITY_DETAIL_DTO))
                    .andExpect(view().name("activity/update"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void shouldReturnShowActivityIfUpdateSuccess() {
        int updateSuccess = 1;
        Mockito.when(userService.getCurrentUser()).thenReturn(EXPECTED_CUSTOM_USER_DETAIL);
        EXPECTED_ACTIVITY_DETAIL_DTO.setUpdatedByUserId(EXPECTED_CUSTOM_USER_DETAIL.getId());
        Mockito.when(activityService.update(EXPECTED_ACTIVITY_DETAIL_DTO)).thenReturn(updateSuccess);

        try {
            mockMvc.perform(post(UPDATE_URL)
                    .param("id", ACTIVITY_ID + "")
                    .param("name", EXPECTED_ACTIVITY_DETAIL_DTO.getName())
                    .param(DESC_PARAM, EXPECTED_ACTIVITY_DETAIL_DTO.getDescription()))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/activity/" + EXPECTED_ACTIVITY_DETAIL_DTO.getId()))
                    .andExpect(redirectedUrl("/activity/" + EXPECTED_ACTIVITY_DETAIL_DTO.getId()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void shouldShowMessageErrorIfUpdateExistName() {
        Mockito.when(userService.getCurrentUser()).thenReturn(EXPECTED_CUSTOM_USER_DETAIL);
        EXPECTED_ACTIVITY_DETAIL_DTO.setCreatedByUserId(EXPECTED_CUSTOM_USER_DETAIL.getId());
        Mockito.when(activityService.checkExistNameForUpdate(EXPECTED_ACTIVITY_DETAIL_DTO.getId(), EXPECTED_ACTIVITY_DETAIL_DTO.getName())).thenReturn(true);

        try {
            mockMvc.perform(post(UPDATE_URL)
                    .param("id", ACTIVITY_ID + "")
                    .param("name", EXPECTED_ACTIVITY_DETAIL_DTO.getName())
                    .param(DESC_PARAM, EXPECTED_ACTIVITY_DETAIL_DTO.getDescription()))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(flash().attribute(ERROR_ATTRIBUTE, "This name already exists"))
                    .andExpect(flash().attribute(ACTIVITY_INFO_ATTRIBUTE, EXPECTED_ACTIVITY_DETAIL_DTO))
                    .andExpect(view().name("redirect:/activity/update/" + EXPECTED_ACTIVITY_DETAIL_DTO.getId()))
                    .andExpect(redirectedUrl("/activity/update/" + EXPECTED_ACTIVITY_DETAIL_DTO.getId()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void shouldShowMessageIfUpdateFail(){
        Mockito.when(userService.getCurrentUser()).thenReturn(EXPECTED_CUSTOM_USER_DETAIL);
        Mockito.when(activityService.update(EXPECTED_ACTIVITY_DETAIL_DTO)).thenThrow(DataIntegrityViolationException.class);
        try {
            mockMvc.perform(post(UPDATE_URL)
                    .param("id", ACTIVITY_ID + "")
                    .param("name", EXPECTED_ACTIVITY_DETAIL_DTO.getName())
                    .param(DESC_PARAM, EXPECTED_ACTIVITY_DETAIL_DTO.getDescription()))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(flash().attribute(ERROR_ATTRIBUTE, "Can't update Activity. Try again!"))
                    .andExpect(flash().attribute(ACTIVITY_INFO_ATTRIBUTE, EXPECTED_ACTIVITY_DETAIL_DTO))
                    .andExpect(view().name("redirect:/activity/update/" + EXPECTED_ACTIVITY_DETAIL_DTO.getId()))
                    .andExpect(redirectedUrl("/activity/update/" + EXPECTED_ACTIVITY_DETAIL_DTO.getId()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void shouldShowCreatePage() {
        try {
            mockMvc.perform(get(CREATE_URL))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute(ACTIVITY_INFO_ATTRIBUTE, new ActivityDetailDTO()))
                    .andExpect(view().name("activity/create"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void shouldReturnHomePageIfCreateSuccess() {
        int insertSuccess = 1;
        Mockito.when(userService.getCurrentUser()).thenReturn(EXPECTED_CUSTOM_USER_DETAIL);
        EXPECTED_ACTIVITY_DETAIL_DTO.setCreatedByUserId(EXPECTED_CUSTOM_USER_DETAIL.getId());

        Mockito.when(activityService.create(EXPECTED_ACTIVITY_DETAIL_DTO)).thenReturn(insertSuccess);

        try {
            mockMvc.perform(post(CREATE_URL)
                    .param("id", ACTIVITY_ID + "")
                    .param("name", EXPECTED_ACTIVITY_DETAIL_DTO.getName())
                    .param(DESC_PARAM, EXPECTED_ACTIVITY_DETAIL_DTO.getDescription()))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/"))
                    .andExpect(redirectedUrl("/"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

    }

    @Test
    public void shouldShowMessageErrorIfCreateExistName() {
        Mockito.when(userService.getCurrentUser()).thenReturn(EXPECTED_CUSTOM_USER_DETAIL);
        EXPECTED_ACTIVITY_DETAIL_DTO.setCreatedByUserId(EXPECTED_CUSTOM_USER_DETAIL.getId());
        Mockito.when(activityService.checkExistName(EXPECTED_ACTIVITY_DETAIL_DTO.getName())).thenReturn(true);

        try {
            mockMvc.perform(post(CREATE_URL)
                    .param("id", ACTIVITY_ID + "")
                    .param("name", EXPECTED_ACTIVITY_DETAIL_DTO.getName())
                    .param(DESC_PARAM, EXPECTED_ACTIVITY_DETAIL_DTO.getDescription()))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(flash().attribute(ERROR_ATTRIBUTE, "This name already exists"))
                    .andExpect(flash().attribute(ACTIVITY_INFO_ATTRIBUTE, EXPECTED_ACTIVITY_DETAIL_DTO))
                    .andExpect(view().name("redirect:/activity/create"))
                    .andExpect(redirectedUrl(CREATE_URL));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void shouldShowMessageIfCreateFail(){
        Mockito.when(userService.getCurrentUser()).thenReturn(EXPECTED_CUSTOM_USER_DETAIL);
        Mockito.when(activityService.create(EXPECTED_ACTIVITY_DETAIL_DTO)).thenThrow(DataIntegrityViolationException.class);
        try {
            mockMvc.perform(post(CREATE_URL)
                    .param("id", ACTIVITY_ID + "")
                    .param("name", EXPECTED_ACTIVITY_DETAIL_DTO.getName())
                    .param(DESC_PARAM, EXPECTED_ACTIVITY_DETAIL_DTO.getDescription()))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(flash().attribute(ERROR_ATTRIBUTE, "Can't create Activity. Try again!"))
                    .andExpect(flash().attribute(ACTIVITY_INFO_ATTRIBUTE, EXPECTED_ACTIVITY_DETAIL_DTO))
                    .andExpect(view().name("redirect:/activity/create"))
                    .andExpect(redirectedUrl(CREATE_URL));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

}
