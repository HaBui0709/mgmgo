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
 * Unit Test for Search Controller.
 *
 * @author ttkngo.
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchControllerTest {

    private static final String URL = "/search/";
    private static final String URL_VIEW = "search";
    private static final String KEY_SEARCH = "abc";
    private static final List<ActivityDTO> EXPECTED_ACTIVITY_DTO = Collections.singletonList(new ActivityDTO(1L, "name", null));
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchControllerTest.class);

    private MockMvc mockMvc;
    @Mock
    private ActivityServiceImpl activityService;
    @InjectMocks
    private SearchController searchController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @Test
    public void shouldGetListActivitiesShowOnSearchPage() {
        Mockito.when(activityService.search(KEY_SEARCH)).thenReturn(EXPECTED_ACTIVITY_DTO);

        try {
            mockMvc.perform(MockMvcRequestBuilders.get(URL).param("searchInfor", KEY_SEARCH))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.model().attribute("keySearch", KEY_SEARCH))
                    .andExpect(MockMvcResultMatchers.model().attribute("activities", EXPECTED_ACTIVITY_DTO))
                    .andExpect(MockMvcResultMatchers.view().name(URL_VIEW));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void shouldGetNullActivityShowOnSearchPage() {
        Mockito.when(activityService.search(KEY_SEARCH)).thenReturn(null);

        try {
            mockMvc.perform(MockMvcRequestBuilders.get(URL).param("searchInfor", KEY_SEARCH))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.model().attribute("keySearch", KEY_SEARCH))
                    .andExpect(MockMvcResultMatchers.model().attribute("activities", null))
                    .andExpect(MockMvcResultMatchers.view().name(URL_VIEW));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

}