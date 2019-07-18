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
    private static final String EXIST_NAME = "new name";
    private static final int CREATED_BY_USER_ID = 1;
    private static final int UPDATED_BY_USER_ID = 1;
    private static final long IMAGE_ID = 1;
    private static final double RATING = 2.5;
    private static final ActivityDetailDTO EXPECTED_ACTIVITY_DETAIL_DTO = new ActivityDetailDTO(ACTIVITY_ID, "new", "Description", RATING, IMAGE_ID, CREATED_BY_USER_ID, UPDATED_BY_USER_ID);
    private static final ActivityDetailDTO EXISTED_ACTIVITY_DETAIL_DTO = new ActivityDetailDTO(2, "new", "Descriptionabc", RATING, IMAGE_ID, CREATED_BY_USER_ID, UPDATED_BY_USER_ID);
    private static final String KEY_SEARCH = "abc";
    private static final List<ActivityDTO> EXPECTED_LIST_ACTIVITY_DTO = Collections.singletonList(new ActivityDTO(1L, "name", IMAGE_ID));
    private static final int CURRENT_PAGE = 1;
    private static final int USER_ID = 1;
    private static final int EXPECTED_RECORD = 3;


    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityServiceImpl activityService;

    @Test
    public void shouldReturnAllActivities() {
        List<ActivityDTO> expectedListActivityDTO = Collections.singletonList(new ActivityDTO(ACTIVITY_ID, "name", IMAGE_ID));
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
        Mockito.when(activityRepository.update(EXPECTED_ACTIVITY_DETAIL_DTO)).thenReturn(UPDATE_SUCCESS);

        Assert.assertEquals(UPDATE_SUCCESS, activityService.update(EXPECTED_ACTIVITY_DETAIL_DTO));
    }

    @Test
    public void shouldReturn0IfUpdateFailed() {
        final int UPDATE_FAIL = 0;
        EXPECTED_ACTIVITY_DETAIL_DTO.setUpdatedByUserId(1);
        Mockito.when(activityRepository.update(EXPECTED_ACTIVITY_DETAIL_DTO)).thenReturn(UPDATE_FAIL);

        Assert.assertEquals(UPDATE_FAIL, activityService.update(EXPECTED_ACTIVITY_DETAIL_DTO));
    }

    @Test
    public void shouldReturn1IfInsertSuccess() {
        final int INSERT_SUCCESS = 1;
        Mockito.when(activityRepository.create(EXPECTED_ACTIVITY_DETAIL_DTO)).thenReturn(INSERT_SUCCESS);

        Assert.assertEquals(INSERT_SUCCESS, activityService.create(EXPECTED_ACTIVITY_DETAIL_DTO));
    }

    @Test
    public void shouldReturn0IfInsertFailed() {
        final int INSERT_SUCCESS = 0;
        Mockito.when(activityRepository.create(EXPECTED_ACTIVITY_DETAIL_DTO)).thenReturn(INSERT_SUCCESS);

        Assert.assertEquals(INSERT_SUCCESS, activityService.create(EXPECTED_ACTIVITY_DETAIL_DTO));
    }

    @Test
    public void shouldReturnExistedActivityIfNameExistWhenCreate() {
        Mockito.when(activityRepository.findByName(EXIST_NAME)).thenReturn(EXISTED_ACTIVITY_DETAIL_DTO);

        Assert.assertEquals(EXISTED_ACTIVITY_DETAIL_DTO, activityService.checkExistNameForCreate(EXIST_NAME));
    }

    @Test
    public void shouldReturnNullIfNameNotExistWhenCreate() {
        Mockito.when(activityRepository.findByName(EXPECTED_ACTIVITY_DETAIL_DTO.getName())).thenReturn(null);

        Assert.assertEquals(null, activityService.checkExistNameForCreate(EXPECTED_ACTIVITY_DETAIL_DTO.getName()));
    }

    @Test
    public void shouldReturnExistedActivityIfNameExistWhenUpdate() {
        Mockito.when(activityRepository.findByName(EXIST_NAME)).thenReturn(EXISTED_ACTIVITY_DETAIL_DTO);
        Assert.assertEquals(EXISTED_ACTIVITY_DETAIL_DTO, activityService.checkExistNameForUpdate(EXPECTED_ACTIVITY_DETAIL_DTO.getId(), EXIST_NAME));
    }

    @Test
    public void shouldReturnNullIfNameNotExistWhenUpdate() {
        Mockito.when(activityRepository.findByName(EXPECTED_ACTIVITY_DETAIL_DTO.getName())).thenReturn(null);

        Assert.assertEquals(null, activityService.checkExistNameForUpdate(EXPECTED_ACTIVITY_DETAIL_DTO.getId(), EXPECTED_ACTIVITY_DETAIL_DTO.getName()));
    }

    @Test
    public void shouldReturnListActivitiesWhenKeySearchCorrect() {
        Mockito.when(activityRepository.search(KEY_SEARCH, CURRENT_PAGE)).thenReturn(EXPECTED_LIST_ACTIVITY_DTO);

        List<ActivityDTO> actualListActivityDTO = activityService.search(KEY_SEARCH, CURRENT_PAGE);

        Assert.assertEquals(EXPECTED_LIST_ACTIVITY_DTO, actualListActivityDTO);
    }

    @Test
    public void shouldReturnNullWhenKeySearchIncorrect() {
        Mockito.when(activityRepository.search(KEY_SEARCH, CURRENT_PAGE)).thenReturn(null);

        List<ActivityDTO> actualListActivityDTO = activityService.search(KEY_SEARCH, CURRENT_PAGE);

        Assert.assertEquals(null, actualListActivityDTO);
    }

    @Test
    public void shouldReturnTotalRecordActivitiesIfKeySearchCorrect() {
        int expectedRecord = 3;
        Mockito.when(activityRepository.countTotalRecordSearch(KEY_SEARCH)).thenReturn(expectedRecord);

        int actualRecord = activityService.countTotalRecordSearch(KEY_SEARCH);

        Assert.assertEquals(expectedRecord, actualRecord);
    }

    @Test
    public void shouldReturnZeroRecordActivitiesIfKeySearchIncorrect() {
        int expectedRecord = 0;
        Mockito.when(activityRepository.countTotalRecordSearch(KEY_SEARCH)).thenReturn(expectedRecord);

        int actualRecord = activityService.countTotalRecordSearch(KEY_SEARCH);

        Assert.assertEquals(expectedRecord, actualRecord);
    }

    @Test
    public void shouldReturnActivitiesOfPage() {
        Mockito.when(activityRepository.getActivities(CURRENT_PAGE)).thenReturn(EXPECTED_LIST_ACTIVITY_DTO);

        List<ActivityDTO> actualActivities = activityService.getActivities(CURRENT_PAGE);

        Assert.assertEquals(EXPECTED_LIST_ACTIVITY_DTO, actualActivities);
    }

    @Test
    public void shouldReturnEmptyListActivitiesIfPageIncorrect() {
        List<ActivityDTO> expectedActivities = Collections.emptyList();

        List<ActivityDTO> actualActivities = activityService.getActivities(-1);

        Assert.assertEquals(expectedActivities, actualActivities);
    }

    @Test
    public void shouldReturnTotalRecordOfActivities() {
        Mockito.when(activityRepository.countTotalRecordActivity()).thenReturn(EXPECTED_RECORD);

        int actualPageSize = activityService.countTotalRecordActivity();

        Assert.assertEquals(EXPECTED_RECORD, actualPageSize);
    }

    @Test
    public void shouldReturnZeroRecordIfHaveNotActivity() {
        int expectedRecord = 0;
        Mockito.when(activityRepository.countTotalRecordActivity()).thenReturn(expectedRecord);

        int actualPageSize = activityService.countTotalRecordActivity();

        Assert.assertEquals(expectedRecord, actualPageSize);
    }

    @Test
    public void shouldReturnActivityIdIfGetByName() {
        Mockito.when(activityRepository.getIdActivity(EXPECTED_ACTIVITY_DETAIL_DTO.getName())).thenReturn(ACTIVITY_ID);

        Assert.assertEquals(ACTIVITY_ID, activityService.getIdActivity(EXPECTED_ACTIVITY_DETAIL_DTO.getName()));
    }

    @Test
    public void shouldReturnListActivityIfGetByUserId() {
        Mockito.when(activityRepository.getListActivityByUserId(USER_ID, CURRENT_PAGE)).thenReturn(EXPECTED_LIST_ACTIVITY_DTO);

        List<ActivityDTO> actualListActivityDTO = activityService.getListActivityByUserId(USER_ID, CURRENT_PAGE);

        Assert.assertEquals(EXPECTED_LIST_ACTIVITY_DTO, actualListActivityDTO);
    }

    @Test
    public void shouldReturnEmptyListIfCurrentPageLessThanOne() {
        Mockito.when(activityRepository.getListActivityByUserId(USER_ID, 0)).thenReturn(EXPECTED_LIST_ACTIVITY_DTO);

        Assert.assertEquals(0, activityService.getListActivityByUserId(USER_ID, 0).size());
    }

    @Test
    public void shouldReturnEmptyListIfNotFindUserId() {
        Mockito.when(activityRepository.getListActivityByUserId(USER_ID, CURRENT_PAGE)).thenReturn(Collections.emptyList());

        Assert.assertEquals(0, activityService.getListActivityByUserId(USER_ID, 1).size());
    }

    @Test
    public void shouldReturnZeroRecordIfHaveNoActivityByUserId() {
        Mockito.when(activityRepository.countTotalRecordActivitybyUserId(USER_ID)).thenReturn(0);

        int actualResult = activityService.countTotalRecordActivitybyUserId(USER_ID);

        Assert.assertEquals(0, actualResult);
    }

    @Test
    public void shouldReturnRecordIfHaveActivitiesByUserId() {
        Mockito.when(activityRepository.countTotalRecordActivitybyUserId(USER_ID)).thenReturn(EXPECTED_RECORD);

        int actualResult = activityService.countTotalRecordActivitybyUserId(USER_ID);

        Assert.assertEquals(EXPECTED_RECORD, actualResult);
    }
}
