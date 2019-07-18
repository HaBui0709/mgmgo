package com.mgmtp.internship.experiences.repositories;

import com.mgmtp.internship.experiences.dto.ActivityDTO;
import com.mgmtp.internship.experiences.model.tables.tables.ActivityImage;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.mgmtp.internship.experiences.constants.ApplicationConstant.RECORD_OF_LIST;
import static com.mgmtp.internship.experiences.model.tables.Tables.ACTIVITY_IMAGE;
import static com.mgmtp.internship.experiences.model.tables.Tables.FAVORITE;
import static com.mgmtp.internship.experiences.model.tables.tables.Activity.ACTIVITY;
import static com.mgmtp.internship.experiences.model.tables.tables.Image.IMAGE;

/**
 * Favorite repository.
 *
 * @author thuynh
 */

@Component
public class FavoriteRepository {
    @Autowired
    private DSLContext dslContext;

    public List<ActivityDTO> getFavoriteActivitiesByUserId(long userId, int currentPage) {
        return dslContext.select(ACTIVITY.ID, ACTIVITY.NAME, IMAGE.ID.as("image_id"), ACTIVITY.ADDRESS)
                .from(FAVORITE)
                .join(ACTIVITY)
                .on(FAVORITE.ACTIVITY_ID.eq(ACTIVITY.ID))
                .leftJoin(ACTIVITY_IMAGE)
                .on(ACTIVITY.ID.eq(ActivityImage.ACTIVITY_IMAGE.ACTIVITY_ID))
                .leftJoin(IMAGE).on(ActivityImage.ACTIVITY_IMAGE.IMAGE_ID.eq(IMAGE.ID))
                .where(FAVORITE.USER_ID.eq(userId))
                .orderBy(ACTIVITY.ID)
                .offset((currentPage - 1) * RECORD_OF_LIST)
                .limit(RECORD_OF_LIST)
                .fetchInto(ActivityDTO.class);
    }

    public int countTotalRecord(long userId) {
        return dslContext.selectCount()
                .from(FAVORITE)
                .where(FAVORITE.USER_ID.eq(userId))
                .fetchAny(0, Integer.class);
    }

    public boolean checkFavorite(long activityId, long userId) {
        return dslContext.fetchExists(FAVORITE, FAVORITE.ACTIVITY_ID.eq(activityId).and(FAVORITE.USER_ID.eq(userId)));
    }

    public int addFavorite(long activityId, long userId) {
        return dslContext.insertInto(FAVORITE, FAVORITE.ACTIVITY_ID, FAVORITE.USER_ID)
                .values(activityId, userId)
                .execute();
    }

    public int deleteFavorite(long activityId, long userId) {
        return dslContext.deleteFrom(FAVORITE)
                .where(FAVORITE.ACTIVITY_ID.eq(activityId))
                .and(FAVORITE.USER_ID.eq(userId))
                .execute();
    }
}
