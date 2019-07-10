package com.mgmtp.internship.experiences.repositories;

import com.mgmtp.internship.experiences.dto.ActivityDTO;
import com.mgmtp.internship.experiences.dto.ActivityDetailDTO;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record5;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.mgmtp.internship.experiences.model.tables.tables.Activity.ACTIVITY;
import static com.mgmtp.internship.experiences.model.tables.tables.ActivityImage.ACTIVITY_IMAGE;
import static com.mgmtp.internship.experiences.model.tables.tables.Image.IMAGE;
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
        return dslContext.select(ACTIVITY.ID, ACTIVITY.NAME, IMAGE.ID.as("imageId"))
                .from(ACTIVITY)
                .leftJoin(ACTIVITY_IMAGE)
                .on(ACTIVITY.ID.eq(ACTIVITY_IMAGE.ACTIVITY_ID))
                .leftJoin(IMAGE).on(ACTIVITY_IMAGE.IMAGE_ID.eq(IMAGE.ID))
                .orderBy(ACTIVITY.ID)
                .fetchInto(ActivityDTO.class);
    }

    public ActivityDetailDTO findById(long activityId) {
        Record5<Long, String, String, BigDecimal, Long> activity = dslContext.select(ACTIVITY.ID,
                ACTIVITY.NAME, ACTIVITY.DESCRIPTION, round(avg(RATING.VALUE), 1).as("rating"), IMAGE.ID.as("imageId"))
                .from(ACTIVITY)
                .leftJoin(RATING)
                .on(ACTIVITY.ID.eq(RATING.ACTIVITY_ID))
                .leftJoin(ACTIVITY_IMAGE)
                .on(ACTIVITY.ID.eq(ACTIVITY_IMAGE.ACTIVITY_ID))
                .leftJoin(IMAGE).on(ACTIVITY_IMAGE.IMAGE_ID.eq(IMAGE.ID))
                .where(ACTIVITY.ID.eq(activityId))
                .groupBy(ACTIVITY.ID, IMAGE.ID).fetchOne();
        return activity == null ? null : activity.into(ActivityDetailDTO.class);
    }

    public int update(ActivityDetailDTO activityDetailDTO) {
        return dslContext.update(ACTIVITY)
                .set(ACTIVITY.NAME, activityDetailDTO.getName())
                .set(ACTIVITY.DESCRIPTION, activityDetailDTO.getDescription())
                .set(ACTIVITY.UPDATED_BY_USER_ID, activityDetailDTO.getUpdatedByUserId())
                .where(ACTIVITY.ID.eq(activityDetailDTO.getId())).execute();
    }

    public int create(ActivityDetailDTO activityDetailDTO) {
        return dslContext.insertInto(ACTIVITY, ACTIVITY.NAME, ACTIVITY.DESCRIPTION, ACTIVITY.CREATED_BY_USER_ID, ACTIVITY.UPDATED_BY_USER_ID)
                .values(activityDetailDTO.getName(), activityDetailDTO.getDescription(), activityDetailDTO.getCreatedByUserId(), activityDetailDTO.getCreatedByUserId())
                .execute();
    }

    public boolean checkExistName(String activityName) {
        return dslContext.fetchExists(dslContext.selectFrom(ACTIVITY)
                .where(ACTIVITY.NAME.likeIgnoreCase(activityName)));
    }

    public boolean checkExistNameForUpdate(long activityId, String activityName) {
        return dslContext.fetchExists(dslContext.selectFrom(ACTIVITY)
                .where(ACTIVITY.NAME.likeIgnoreCase(activityName).and(ACTIVITY.ID.notEqual(activityId))));
    }

    public List<ActivityDTO> search(String text) {
        final String unaccentFunc = "unaccent";
        Field<String> keySearch = DSL.function(unaccentFunc, String.class, DSL.val(text.trim()));
        return dslContext.selectFrom(ACTIVITY)
                .where(DSL.function(unaccentFunc, String.class, ACTIVITY.NAME).containsIgnoreCase(keySearch))
                .or(DSL.function(unaccentFunc, String.class, ACTIVITY.DESCRIPTION).containsIgnoreCase(keySearch))
                .fetchInto(ActivityDTO.class);
    }
}