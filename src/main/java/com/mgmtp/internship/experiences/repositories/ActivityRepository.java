package com.mgmtp.internship.experiences.repositories;

import com.mgmtp.internship.experiences.constants.EnumSort;
import com.mgmtp.internship.experiences.dto.ActivityDTO;
import com.mgmtp.internship.experiences.dto.ActivityDetailDTO;
import com.mgmtp.internship.experiences.dto.CommentDTO;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import static com.mgmtp.internship.experiences.constants.ApplicationConstant.FUNC_UNACCENT;
import static com.mgmtp.internship.experiences.constants.ApplicationConstant.RECORD_OF_LIST;
import static com.mgmtp.internship.experiences.model.tables.Tables.COMMENT;
import static com.mgmtp.internship.experiences.model.tables.tables.Activity.ACTIVITY;
import static com.mgmtp.internship.experiences.model.tables.tables.ActivityImage.ACTIVITY_IMAGE;
import static com.mgmtp.internship.experiences.model.tables.tables.Image.IMAGE;
import static com.mgmtp.internship.experiences.model.tables.tables.Rating.RATING;
import static com.mgmtp.internship.experiences.model.tables.tables.User.USER;
import static org.jooq.impl.DSL.*;

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

    public ActivityDetailDTO findById(long activityId) {
        return dslContext
                .select(ACTIVITY.ID,
                        ACTIVITY.NAME,
                        ACTIVITY.DESCRIPTION,
                        ACTIVITY.ADDRESS,
                        round(avg(RATING.VALUE), 1).as("rating"),
                        IMAGE.ID.as(IMAGE_ID_PROPERTY),
                        ACTIVITY.CREATED_BY_USER_ID,
                        ACTIVITY.UPDATED_BY_USER_ID)
                .from(ACTIVITY)
                .leftJoin(RATING)
                .on(ACTIVITY.ID.eq(RATING.ACTIVITY_ID))
                .leftJoin(ACTIVITY_IMAGE)
                .on(ACTIVITY.ID.eq(ACTIVITY_IMAGE.ACTIVITY_ID))
                .leftJoin(IMAGE).on(ACTIVITY_IMAGE.IMAGE_ID.eq(IMAGE.ID))
                .where(ACTIVITY.ID.eq(activityId))
                .groupBy(ACTIVITY.ID, IMAGE.ID).fetchOneInto(ActivityDetailDTO.class);
    }

    public int update(ActivityDetailDTO activityDetailDTO) {
        return dslContext.update(ACTIVITY)
                .set(ACTIVITY.NAME, activityDetailDTO.getName())
                .set(ACTIVITY.DESCRIPTION, activityDetailDTO.getDescription())
                .set(ACTIVITY.UPDATED_BY_USER_ID, activityDetailDTO.getUpdatedByUserId())
                .set(ACTIVITY.ADDRESS, activityDetailDTO.getAddress())
                .set(ACTIVITY.UPDATED_DATE, currentTimestamp())
                .where(ACTIVITY.ID.eq(activityDetailDTO.getId())).execute();
    }

    public int create(ActivityDetailDTO activityDetailDTO) {
        return dslContext.insertInto(ACTIVITY, ACTIVITY.NAME, ACTIVITY.DESCRIPTION, ACTIVITY.CREATED_BY_USER_ID, ACTIVITY.UPDATED_BY_USER_ID, ACTIVITY.ADDRESS)
                .values(activityDetailDTO.getName(), activityDetailDTO.getDescription(), activityDetailDTO.getCreatedByUserId(), activityDetailDTO.getCreatedByUserId(), activityDetailDTO.getAddress())
                .execute();
    }

    public long getIdActivity(String name) {
        return dslContext.select(ACTIVITY.ID)
                .from(ACTIVITY)
                .where(ACTIVITY.NAME.eq(name)).fetchOneInto(Integer.class);
    }

    public ActivityDetailDTO findByName(String activityName) {
        Field<String> keyName = DSL.function(FUNC_UNACCENT, String.class, DSL.val(activityName));
        return dslContext
                .select(ACTIVITY.ID,
                        ACTIVITY.NAME,
                        ACTIVITY.DESCRIPTION,
                        IMAGE.ID.as(IMAGE_ID_PROPERTY),
                        ACTIVITY.ADDRESS)
                .from(ACTIVITY)
                .leftJoin(ACTIVITY_IMAGE)
                .on(ACTIVITY.ID.eq(ACTIVITY_IMAGE.ACTIVITY_ID))
                .leftJoin(IMAGE)
                .on(ACTIVITY_IMAGE.IMAGE_ID.eq(IMAGE.ID))
                .where(DSL.function(FUNC_UNACCENT, String.class, ACTIVITY.NAME).likeIgnoreCase(keyName))
                .fetchOneInto(ActivityDetailDTO.class);
    }

    public List<ActivityDTO> search(String text, int currentPage, EnumSort sortType) {
        Field<String> keySearch = DSL.function(FUNC_UNACCENT, String.class, DSL.val(text.trim()));
        return dslContext
                .select(ACTIVITY.ID,
                        ACTIVITY.NAME,
                        IMAGE.ID.as(IMAGE_ID_PROPERTY))
                .from(ACTIVITY)
                .leftJoin(ACTIVITY_IMAGE)
                .on(ACTIVITY.ID.eq(ACTIVITY_IMAGE.ACTIVITY_ID))
                .leftJoin(IMAGE).on(ACTIVITY_IMAGE.IMAGE_ID.eq(IMAGE.ID))
                .leftJoin(RATING).on(ACTIVITY.ID.eq(RATING.ACTIVITY_ID))
                .where(DSL.function(FUNC_UNACCENT, String.class, ACTIVITY.NAME).containsIgnoreCase(keySearch))
                .or(DSL.function(FUNC_UNACCENT, String.class, ACTIVITY.DESCRIPTION).containsIgnoreCase(keySearch))
                .or(DSL.function(FUNC_UNACCENT, String.class, ACTIVITY.ADDRESS).containsIgnoreCase(keySearch))
                .groupBy(ACTIVITY.ID, IMAGE.ID)
                .orderBy(hashMapSortType(sortType))
                .offset((currentPage - 1) * RECORD_OF_LIST)
                .limit(RECORD_OF_LIST)
                .fetchInto(ActivityDTO.class);
    }

    public int countTotalRecordSearch(String text) {
        Field<String> keySearch = DSL.function(FUNC_UNACCENT, String.class, DSL.val(text.trim()));
        return dslContext.selectCount()
                .from(ACTIVITY)
                .where(DSL.function(FUNC_UNACCENT, String.class, ACTIVITY.NAME).containsIgnoreCase(keySearch))
                .or(DSL.function(FUNC_UNACCENT, String.class, ACTIVITY.DESCRIPTION).containsIgnoreCase(keySearch))
                .fetchAny(0, Integer.class);
    }

    public List<ActivityDTO> getActivities(int currentPage, EnumSort sortType) {
        return dslContext.select(ACTIVITY.ID, ACTIVITY.NAME, IMAGE.ID.as(IMAGE_ID_PROPERTY))
                .from(ACTIVITY)
                .leftJoin(ACTIVITY_IMAGE)
                .on(ACTIVITY.ID.eq(ACTIVITY_IMAGE.ACTIVITY_ID))
                .leftJoin(IMAGE).on(ACTIVITY_IMAGE.IMAGE_ID.eq(IMAGE.ID))
                .leftJoin(RATING).on(ACTIVITY.ID.eq(RATING.ACTIVITY_ID))
                .groupBy(ACTIVITY.ID, IMAGE.ID)
                .orderBy(hashMapSortType(sortType))
                .offset((currentPage - 1) * RECORD_OF_LIST)
                .limit(RECORD_OF_LIST)
                .fetchInto(ActivityDTO.class);
    }

    public int countTotalRecordActivity() {
        return dslContext.selectCount()
                .from(ACTIVITY)
                .fetchAny(0, Integer.class);
    }

    private SortField[] hashMapSortType(EnumSort sortType) {
        EnumMap<EnumSort, List<SortField>> enumMapSort = new EnumMap<>(EnumSort.class);

        enumMapSort.put(EnumSort.NEWEST_FIRST, Collections.singletonList(DSL.field(ACTIVITY.CREATED_DATE).desc().nullsLast()));
        enumMapSort.put(EnumSort.ACTIVE_FIRST, Arrays.asList(DSL.field(ACTIVITY.ACTIVE_DATE).desc().nullsLast(),
                DSL.field(ACTIVITY.UPDATED_DATE).desc().nullsLast()));
        enumMapSort.put(EnumSort.RATING_FIRST, Arrays.asList(DSL.field(round(avg(RATING.VALUE), 1)).desc().nullsLast(),
                DSL.field(count(RATING.ID)).desc().nullsLast()));

        return enumMapSort.get(sortType).toArray(new SortField[]{});
    }

    public int updatedActiveDate(Long activityId) {
        return dslContext.update(ACTIVITY)
                .set(ACTIVITY.ACTIVE_DATE, currentTimestamp())
                .where(ACTIVITY.ID.eq(activityId)).execute();
    }

    public List<ActivityDTO> getListActivityByUserId(long id, int currentPage) {
        return dslContext
                .select(ACTIVITY.ID, ACTIVITY.NAME, ACTIVITY_IMAGE.IMAGE_ID)
                .from(ACTIVITY)
                .leftJoin(USER)
                .on(ACTIVITY.CREATED_BY_USER_ID.eq(USER.ID))
                .leftJoin(ACTIVITY_IMAGE)
                .on(ACTIVITY.ID.eq(ACTIVITY_IMAGE.ACTIVITY_ID))
                .where(USER.ID.eq(id))
                .orderBy(ACTIVITY.CREATED_DATE.desc())
                .offset((currentPage - 1) * RECORD_OF_LIST)
                .limit(RECORD_OF_LIST)
                .fetchInto(ActivityDTO.class);
    }

    public int countTotalRecordActivitybyUserId(long id) {
        return dslContext.selectCount()
                .from(ACTIVITY)
                .join(USER).on(ACTIVITY.CREATED_BY_USER_ID.eq(USER.ID))
                .where(USER.ID.eq(id))
                .fetchAny(0, Integer.class);
    }

    @Transactional
    public int deleteActivity(long activityId) {
        dslContext.deleteFrom(IMAGE)
                .where(IMAGE.ID.in(dslContext.select(ACTIVITY_IMAGE.IMAGE_ID)
                        .from(ACTIVITY_IMAGE)
                        .where(ACTIVITY_IMAGE.ACTIVITY_ID.eq(activityId)))).execute();

        return dslContext.deleteFrom(ACTIVITY).where(ACTIVITY.ID.eq(activityId)).execute();
    }

    public List<CommentDTO> getAllCommentById(long activityId) {
        return dslContext.select(COMMENT.ID, USER.IMAGE_ID, USER.DISPLAY_NAME, COMMENT.CONTENT, COMMENT.DATE_CREATE)
                .from(COMMENT)
                .join(USER)
                .on(COMMENT.USER_ID.eq(USER.ID))
                .where(COMMENT.ACTIVITY_ID.eq(activityId))
                .orderBy(COMMENT.DATE_CREATE.desc(), COMMENT.ID.desc())
                .fetch()
                .map(record -> new CommentDTO(record.get(COMMENT.ID), record.get(USER.IMAGE_ID), record.get(USER.DISPLAY_NAME), record.get(COMMENT.CONTENT), record.get(COMMENT.DATE_CREATE)));
    }

    public int addComment(CommentDTO commentDTO, long activityId, long userId) {
        return dslContext.insertInto(COMMENT, COMMENT.CONTENT, COMMENT.DATE_CREATE, COMMENT.ACTIVITY_ID, COMMENT.USER_ID)
                .values(commentDTO.getContent(), commentDTO.getDateCreate(), activityId, userId)
                .execute();
    }

    public boolean checkExistedCommentOfUserInActitvity(long userId, long activityId) {
        return dslContext.fetchExists(COMMENT, COMMENT.ACTIVITY_ID.eq(activityId).and(COMMENT.USER_ID.eq(userId)));
    }
}