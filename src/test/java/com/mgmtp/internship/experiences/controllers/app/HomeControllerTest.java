package com.mgmtp.internship.experiences.controllers.app;

import com.mgmtp.internship.experiences.dto.ActivityDTO;
import com.mgmtp.internship.experiences.services.impl.ActivityServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

/**
 * Unit test for home controller.
 *
 * @author thuynh
 */
@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeControllerTest.class);
    private static final int CURRENT_PAGE = 1;
    private static final int PAGE_SIZE = 5;
    private static final List<ActivityDTO> EXPECTED_ACTIVITIES = Collections.singletonList(new ActivityDTO(1L, "name", 1L));
    private MockMvc mockMvc;

    @Mock
    private ActivityServiceImpl activityService;

    @InjectMocks
    private HomeController homeController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void shouldGetActivitiesShowOnHomePage() {
        Mockito.when(activityService.getActivities(CURRENT_PAGE)).thenReturn(EXPECTED_ACTIVITIES);
        Mockito.when(activityService.countPages()).thenReturn(PAGE_SIZE);
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.model().attribute("activities", EXPECTED_ACTIVITIES))
                    .andExpect(MockMvcResultMatchers.model().attribute("sizeOfPages", PAGE_SIZE))
                    .andExpect(MockMvcResultMatchers.model().attribute("currentPage", 1))
                    .andExpect(MockMvcResultMatchers.view().name("home/index"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void shouldGetActivitiesShowOnFragmentListActivities() {
        Mockito.when(activityService.getActivities(CURRENT_PAGE)).thenReturn(EXPECTED_ACTIVITIES);
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/more/1")
                    .param("currentPage", String.valueOf(CURRENT_PAGE)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.model().attribute("activities", EXPECTED_ACTIVITIES))
                    .andExpect(MockMvcResultMatchers.view().name("activity/fragments/list-activities"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
