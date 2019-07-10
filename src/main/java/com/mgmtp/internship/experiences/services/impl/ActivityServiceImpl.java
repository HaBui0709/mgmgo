package com.mgmtp.internship.experiences.services.impl;


import com.mgmtp.internship.experiences.dto.ActivityDTO;
import com.mgmtp.internship.experiences.dto.ActivityDetailDTO;
import com.mgmtp.internship.experiences.repositories.ActivityRepository;
import com.mgmtp.internship.experiences.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Activity service for Activity DTO.
 *
 * @author thuynh
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public List<ActivityDTO> findAll() {
        return activityRepository.findAll();
    }

    @Override
    public ActivityDetailDTO findById(long activityId) {
        return activityRepository.findById(activityId);
    }

    @Override
    public int update(ActivityDetailDTO activityDetailDTO) {
        return activityRepository.update(activityDetailDTO);
    }

    @Override
    public int create(ActivityDetailDTO activityDetailDTO) {
        return activityRepository.create(activityDetailDTO);
    }

    @Override
    public ActivityDetailDTO checkExistNameForCreate(String activityName) {
        return activityRepository.findByName(activityName);
    }

    @Override
    public ActivityDetailDTO checkExistNameForUpdate(long activityId, String activityName) {
        ActivityDetailDTO existedActivity = activityRepository.findByName(activityName);
        if (existedActivity != null && existedActivity.getId() == activityId){
                return null;
            }
        return existedActivity;
    }

    @Override
    public List<ActivityDTO> search(String text) {
        return activityRepository.search(text);
    }

    @Override
    public List<ActivityDTO> getActivities(int currentPage) {
        if (currentPage < 1) {
            return Collections.emptyList();
        }
        return activityRepository.getActivities(currentPage);
    }

    @Override
    public int countPages() {
        return activityRepository.countPages();
    }
}
