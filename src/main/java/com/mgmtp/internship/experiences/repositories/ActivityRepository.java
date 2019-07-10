package com.mgmtp.internship.experiences.repositories;

import com.mgmtp.internship.experiences.dto.ActivityDTO;
import com.mgmtp.internship.experiences.dto.ActivityDetailDTO;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.mgmtp.internship.experiences.constants.ApplicationConstant.RECORD_OF_LIST;
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

    private static final String IMAGE_ID_PROPERTY = "imageId";

    @Autowired
    private DSLContext dslContext;

    public List<ActivityDTO> findAll() {
        return dslContext.select(ACTIVITY.ID, ACTIVITY.NAME, IMAGE.ID.as(IMAGE_ID_PROPERTY), ACTIVITY.ADDRESS)
                .from(ACTIVITY)
                .leftJoin(ACTIVITY_IMAGE)
                .on(ACTIVITY.ID.eq(ACTIVITY_IMAGE.ACTIVITY_ID))
                .leftJoin(IMAGE).on(ACTIVITY_IMAGE.IMAGE_ID.eq(IMAGE.ID))
                .orderBy(ACTIVITY.ID)
                .fetchInto(ActivityDTO.class);
    }

    public ActivityDetailDTO findById(long activityId) {
        Record6<Long, String, String, String, BigDecimal, Long> activity = dslContext.select(ACTIVITY.ID,
                ACTIVITY.NAME, ACTIVITY.DESCRIPTION, ACTIVITY.ADDRESS, round(avg(RATING.VALUE), 1).as("rating"), IMAGE.ID.as(IMAGE_ID_PROPERTY))
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
                .set(ACTIVITY.ADDRESS, activityDetailDTO.getAddress())
                .where(ACTIVITY.ID.eq(activityDetailDTO.getId())).execute();
    }

    public int create(ActivityDetailDTO activityDetailDTO) {
        return dslContext.insertInto(ACTIVITY, ACTIVITY.NAME, ACTIVITY.DESCRIPTION, ACTIVITY.CREATED_BY_USER_ID, ACTIVITY.UPDATED_BY_USER_ID, ACTIVITY.ADDRESS)
                .values(activityDetailDTO.getName(), activityDetailDTO.getDescription(), activityDetailDTO.getCreatedByUserId(), activityDetailDTO.getCreatedByUserId(), activityDetailDTO.getAddress())
                .execute();
    }

    public ActivityDetailDTO findByName(String activityName) {
        Record5<Long, String, String, Long, String> existedActivity = dslContext.select(ACTIVITY.ID,
                ACTIVITY.NAME, ACTIVITY.DESCRIPTION, IMAGE.ID.as(IMAGE_ID_PROPERTY), ACTIVITY.ADDRESS)
                .from(ACTIVITY)
                .leftJoin(ACTIVITY_IMAGE)
                .on(ACTIVITY.ID.eq(ACTIVITY_IMAGE.ACTIVITY_ID))
                .leftJoin(IMAGE)
                .on(ACTIVITY_IMAGE.IMAGE_ID.eq(IMAGE.ID))
                .where(ACTIVITY.NAME.likeIgnoreCase(activityName))
                .fetchOne();
        return existedActivity == null ? null : existedActivity.into(ActivityDetailDTO.class);
    }

    public List<ActivityDTO> search(String text) {
        final String unaccentFunc = "unaccent";
        Field<String> keySearch = DSL.function(unaccentFunc, String.class, DSL.val(text.trim()));
        return dslContext.select(ACTIVITY.ID, ACTIVITY.NAME, IMAGE.ID.as(IMAGE_ID_PROPERTY))
                .from(ACTIVITY)
                .leftJoin(ACTIVITY_IMAGE)
                .on(ACTIVITY.ID.eq(ACTIVITY_IMAGE.ACTIVITY_ID))
                .leftJoin(IMAGE).on(ACTIVITY_IMAGE.IMAGE_ID.eq(IMAGE.ID))
                .where(DSL.function(unaccentFunc, String.class, ACTIVITY.NAME).containsIgnoreCase(keySearch))
                .or(DSL.function(unaccentFunc, String.class, ACTIVITY.DESCRIPTION).containsIgnoreCase(keySearch))
                .or(DSL.function(unaccentFunc, String.class, ACTIVITY.ADDRESS).containsIgnoreCase(keySearch))
                .orderBy(ACTIVITY.ID)
                .fetchInto(ActivityDTO.class);
    }

    public List<ActivityDTO> getActivities(int currentPage) {
        return dslContext.select(ACTIVITY.ID, ACTIVITY.NAME, IMAGE.ID.as(IMAGE_ID_PROPERTY))
                .from(ACTIVITY)
                .leftJoin(ACTIVITY_IMAGE)
                .on(ACTIVITY.ID.eq(ACTIVITY_IMAGE.ACTIVITY_ID))
                .leftJoin(IMAGE).on(ACTIVITY_IMAGE.IMAGE_ID.eq(IMAGE.ID))
                .orderBy(ACTIVITY.ID)
                .offset((currentPage - 1) * RECORD_OF_LIST)
                .limit(RECORD_OF_LIST)
                .fetchInto(ActivityDTO.class);
    }

    public int countPages() {
        return (int) Math.ceil(dslContext.selectCount()
                .from(ACTIVITY)
                .fetchAny(0, Integer.class) * 1.0 / RECORD_OF_LIST);
    }
}
