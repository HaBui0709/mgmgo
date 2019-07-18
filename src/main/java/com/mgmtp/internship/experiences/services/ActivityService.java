package com.mgmtp.internship.experiences.services;

import com.mgmtp.internship.experiences.constants.EnumSort;
import com.mgmtp.internship.experiences.dto.ActivityDTO;
import com.mgmtp.internship.experiences.dto.ActivityDetailDTO;

import java.util.List;

/**
 * Activity Service interface.
 *
 * @author thuynh
 */
public interface ActivityService {
    ActivityDetailDTO findById(long activityId);

    int update(ActivityDetailDTO activityDetailDTO);

    int create(ActivityDetailDTO activityDetailDTO);

    ActivityDetailDTO checkExistNameForCreate(String activityName);

    ActivityDetailDTO checkExistNameForUpdate(long activityId, String activityName);

    List<ActivityDTO> search(String text, int currentPage, EnumSort sortType);

    int countTotalRecordSearch(String text);

    List<ActivityDTO> getActivities(int currentPage, EnumSort sortType);

    int countTotalRecordActivity();

    List<ActivityDTO> getListActivityByUserId(long id, int currentPage);

    long getIdActivity(String name);

    int updatedActiveDate(Long activityId);

    public int countTotalRecordActivitybyUserId(long id);
}
