package com.mgmtp.internship.experiences.repositories;

import com.mgmtp.internship.experiences.dto.ActivityDTO;
import com.mgmtp.internship.experiences.dto.ActivityDetailDTO;
import org.jooq.DSLContext;
import org.jooq.Record4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.mgmtp.internship.experiences.model.tables.tables.Activity.ACTIVITY;
import static com.mgmtp.internship.experiences.model.tables.tables.Rating.RATING;
import static org.jooq.impl.DSL.avg;
import static org.jooq.impl.DSL.round;

/**
 * Activity Repository.
 *
 * @author thuynh
 */
@Component
public class ActivityRepository {

    @Autowired
    private DSLContext dslContext;

    public List<ActivityDTO> findAll() {
        return dslContext.selectFrom(ACTIVITY)
                .orderBy(ACTIVITY.ID)
                .fetch().stream()
                .map(ActivityDTO::new)
                .collect(Collectors.toList());
    }

    public ActivityDetailDTO findById(long activityId) {
        Record4<Long, String, String, BigDecimal> activity = dslContext.select(ACTIVITY.ID,
                ACTIVITY.NAME, ACTIVITY.DESCRIPTION, round(avg(RATING.VALUE), 1).as("rating"))
                .from(ACTIVITY)
                .leftJoin(RATING)
                .on(ACTIVITY.ID.eq(RATING.ACTIVITY_ID))
                .where(ACTIVITY.ID.eq(activityId))
                .groupBy(ACTIVITY.ID).fetchOne();

        return activity == null ? null : activity.into(ActivityDetailDTO.class);
    }

    public int update(ActivityDetailDTO activityDetailDTO) {
        return dslContext.update(ACTIVITY)
                .set(ACTIVITY.NAME, activityDetailDTO.getName())
                .set(ACTIVITY.DESCRIPTION, activityDetailDTO.getDescription())
                .set(ACTIVITY.UPDATED_BY_USER_ID, activityDetailDTO.getUpdatedByUserId())
                .where(ACTIVITY.ID.eq(activityDetailDTO.getId())).execute();
    }

    public int create(ActivityDetailDTO activityDetailDTO){
        return dslContext.insertInto(ACTIVITY, ACTIVITY.NAME, ACTIVITY.DESCRIPTION, ACTIVITY.CREATED_BY_USER_ID, ACTIVITY.UPDATED_BY_USER_ID)
                .values(activityDetailDTO.getName(),activityDetailDTO.getDescription(), activityDetailDTO.getCreatedByUserId(), activityDetailDTO.getCreatedByUserId())
                .execute();
    }

    public boolean checkExistName(String activityName){
        return dslContext.fetchExists(dslContext.selectFrom(ACTIVITY)
                .where(ACTIVITY.NAME.likeIgnoreCase(activityName)));
    }

    public boolean checkExistNameForUpdate(long activityId, String activityName) {
        return dslContext.fetchExists(dslContext.selectFrom(ACTIVITY)
                .where(ACTIVITY.NAME.likeIgnoreCase(activityName)
                        .and(ACTIVITY.ID.notEqual(activityId))));
    }
    public List<ActivityDTO> search(String text) {
        return dslContext.selectFrom(ACTIVITY)
                .where(ACTIVITY.NAME.likeIgnoreCase('%' + text.trim() + '%'))
                .or(ACTIVITY.DESCRIPTION.likeIgnoreCase('%' + text.trim() + '%'))
                .fetchInto(ActivityDTO.class);
    }
}
