package com.mgmtp.internship.experiences.services.impl;

import com.mgmtp.internship.experiences.dto.ActivityDTO;
import com.mgmtp.internship.experiences.dto.ActivityDetailDTO;
import com.mgmtp.internship.experiences.repositories.ActivityRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

/**
 * Unit test for activity service.
 *
 * @author thuynh
 */
@RunWith(MockitoJUnitRunner.class)
public class ActivityServiceImplTest {
    private static final long ACTIVITY_ID = 1;
    private static final ActivityDetailDTO EXPECTED_ACTIVITY_DETAIL_DTO = new ActivityDetailDTO(ACTIVITY_ID, "new", "Description", 5);

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityServiceImpl activityService;

    @Test
    public void shouldReturnAllActivities() {
        List<ActivityDTO> expectedListActivityDTO = Collections.singletonList(new ActivityDTO(ACTIVITY_ID, "name"));
        Mockito.when(activityRepository.findAll()).thenReturn(expectedListActivityDTO);

        List<ActivityDTO> actualListActivityDTO = activityService.findAll();

        Assert.assertEquals(expectedListActivityDTO, actualListActivityDTO);
    }

    @Test
    public void shouldReturnActivityById() {
        Mockito.when(activityRepository.findById(ACTIVITY_ID)).thenReturn(EXPECTED_ACTIVITY_DETAIL_DTO);

        ActivityDetailDTO actualActivityDetailDTO = activityService.findById(ACTIVITY_ID);

        Assert.assertEquals(EXPECTED_ACTIVITY_DETAIL_DTO, actualActivityDetailDTO);
    }

    @Test
    public void shouldReturnNullIfActivityNotFound() {
        Mockito.when(activityRepository.findById(ACTIVITY_ID)).thenReturn(null);

        ActivityDetailDTO actualActivityDetailDTO = activityService.findById(ACTIVITY_ID);

        Assert.assertEquals(null, actualActivityDetailDTO);
    }

    @Test
    public void shouldReturn1IfUpdateSuccess() {
        final int UPDATE_SUCCESS = 1;
        Mockito.when(activityRepository.updateActivity(ACTIVITY_ID, EXPECTED_ACTIVITY_DETAIL_DTO.getName(), EXPECTED_ACTIVITY_DETAIL_DTO.getDescription())).thenReturn(UPDATE_SUCCESS);

        Assert.assertEquals(UPDATE_SUCCESS, activityService.updateActivity(ACTIVITY_ID, EXPECTED_ACTIVITY_DETAIL_DTO.getName(), EXPECTED_ACTIVITY_DETAIL_DTO.getDescription()));
    }

    @Test
    public void shouldReturn0IfUpdateFailed() {
        final int UPDATE_FAIL = 0;
        Mockito.when(activityRepository.updateActivity(ACTIVITY_ID, EXPECTED_ACTIVITY_DETAIL_DTO.getName(), EXPECTED_ACTIVITY_DETAIL_DTO.getDescription())).thenReturn(UPDATE_FAIL);

        Assert.assertEquals(UPDATE_FAIL, activityService.updateActivity(ACTIVITY_ID, EXPECTED_ACTIVITY_DETAIL_DTO.getName(), EXPECTED_ACTIVITY_DETAIL_DTO.getDescription()));
    }
}
