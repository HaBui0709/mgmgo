package com.mgmtp.internship.experiences.services;

import com.mgmtp.internship.experiences.dto.ActivityDTO;
import com.mgmtp.internship.experiences.dto.ActivityDetailDTO;

import java.util.List;

/**
 * Activity Service interface.
 *
 * @author thuynh
 */
public interface ActivityService {
    List<ActivityDTO> findAll();

    ActivityDetailDTO findById(long activityId);

    int update(ActivityDetailDTO activityDetailDTO);

    int create(ActivityDetailDTO activityDetailDTO);

    ActivityDetailDTO checkExistNameForCreate(String activityName);

    ActivityDetailDTO checkExistNameForUpdate(long activityId, String activityName);

    List<ActivityDTO> search(String text, int currentPage);

    int countTotalRecordSearch(String text);

    List<ActivityDTO> getActivities(int currentPage);

    int countTotalRecordActivity();
}
