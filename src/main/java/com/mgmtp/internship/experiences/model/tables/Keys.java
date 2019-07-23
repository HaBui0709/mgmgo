/*
 * This file is generated by jOOQ.
 */
package com.mgmtp.internship.experiences.model.tables;


import com.mgmtp.internship.experiences.model.tables.tables.Activity;
import com.mgmtp.internship.experiences.model.tables.tables.ActivityImage;
import com.mgmtp.internship.experiences.model.tables.tables.ActivityTag;
import com.mgmtp.internship.experiences.model.tables.tables.Comment;
import com.mgmtp.internship.experiences.model.tables.tables.Favorite;
import com.mgmtp.internship.experiences.model.tables.tables.Image;
import com.mgmtp.internship.experiences.model.tables.tables.Rating;
import com.mgmtp.internship.experiences.model.tables.tables.ReportActivity;
import com.mgmtp.internship.experiences.model.tables.tables.Tag;
import com.mgmtp.internship.experiences.model.tables.tables.User;
import com.mgmtp.internship.experiences.model.tables.tables.records.ActivityImageRecord;
import com.mgmtp.internship.experiences.model.tables.tables.records.ActivityRecord;
import com.mgmtp.internship.experiences.model.tables.tables.records.ActivityTagRecord;
import com.mgmtp.internship.experiences.model.tables.tables.records.CommentRecord;
import com.mgmtp.internship.experiences.model.tables.tables.records.FavoriteRecord;
import com.mgmtp.internship.experiences.model.tables.tables.records.ImageRecord;
import com.mgmtp.internship.experiences.model.tables.tables.records.RatingRecord;
import com.mgmtp.internship.experiences.model.tables.tables.records.ReportActivityRecord;
import com.mgmtp.internship.experiences.model.tables.tables.records.TagRecord;
import com.mgmtp.internship.experiences.model.tables.tables.records.UserRecord;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>public</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<ActivityRecord, Long> IDENTITY_ACTIVITY = Identities0.IDENTITY_ACTIVITY;
    public static final Identity<CommentRecord, Long> IDENTITY_COMMENT = Identities0.IDENTITY_COMMENT;
    public static final Identity<ImageRecord, Long> IDENTITY_IMAGE = Identities0.IDENTITY_IMAGE;
    public static final Identity<RatingRecord, Long> IDENTITY_RATING = Identities0.IDENTITY_RATING;
    public static final Identity<TagRecord, Long> IDENTITY_TAG = Identities0.IDENTITY_TAG;
    public static final Identity<UserRecord, Long> IDENTITY_USER = Identities0.IDENTITY_USER;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ActivityRecord> PK_ACTIVITY = UniqueKeys0.PK_ACTIVITY;
    public static final UniqueKey<CommentRecord> PK_COMMENT = UniqueKeys0.PK_COMMENT;
    public static final UniqueKey<ImageRecord> PK_IMAGE = UniqueKeys0.PK_IMAGE;
    public static final UniqueKey<RatingRecord> PK_RATING = UniqueKeys0.PK_RATING;
    public static final UniqueKey<TagRecord> PK_TAG = UniqueKeys0.PK_TAG;
    public static final UniqueKey<UserRecord> PK_USER = UniqueKeys0.PK_USER;
    public static final UniqueKey<UserRecord> USER_DISPLAY_NAME_KEY = UniqueKeys0.USER_DISPLAY_NAME_KEY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<ActivityRecord, UserRecord> ACTIVITY__FK_ACTIVITY_USER_CREATED_BY = ForeignKeys0.ACTIVITY__FK_ACTIVITY_USER_CREATED_BY;
    public static final ForeignKey<ActivityRecord, UserRecord> ACTIVITY__FK_ACTIVITY_USER_UPDATED_BY = ForeignKeys0.ACTIVITY__FK_ACTIVITY_USER_UPDATED_BY;
    public static final ForeignKey<ActivityImageRecord, ActivityRecord> ACTIVITY_IMAGE__FK_ACTIVITY_IMAGE_ACTIVITY = ForeignKeys0.ACTIVITY_IMAGE__FK_ACTIVITY_IMAGE_ACTIVITY;
    public static final ForeignKey<ActivityImageRecord, ImageRecord> ACTIVITY_IMAGE__FK_ACTIVITY_IMAGE_IMAGE = ForeignKeys0.ACTIVITY_IMAGE__FK_ACTIVITY_IMAGE_IMAGE;
    public static final ForeignKey<ActivityTagRecord, ActivityRecord> ACTIVITY_TAG__FK_ACTIVITY_TAG_ACTIVITY = ForeignKeys0.ACTIVITY_TAG__FK_ACTIVITY_TAG_ACTIVITY;
    public static final ForeignKey<ActivityTagRecord, TagRecord> ACTIVITY_TAG__FK_ACTIVITY_TAG_TAG = ForeignKeys0.ACTIVITY_TAG__FK_ACTIVITY_TAG_TAG;
    public static final ForeignKey<CommentRecord, ActivityRecord> COMMENT__FK_ACTIVITY_COMMENT = ForeignKeys0.COMMENT__FK_ACTIVITY_COMMENT;
    public static final ForeignKey<CommentRecord, UserRecord> COMMENT__FK_USER_COMMENT = ForeignKeys0.COMMENT__FK_USER_COMMENT;
    public static final ForeignKey<FavoriteRecord, ActivityRecord> FAVORITE__FK_FAVORITE_ACTIVITY = ForeignKeys0.FAVORITE__FK_FAVORITE_ACTIVITY;
    public static final ForeignKey<FavoriteRecord, UserRecord> FAVORITE__FK_FAVORITE_USER = ForeignKeys0.FAVORITE__FK_FAVORITE_USER;
    public static final ForeignKey<RatingRecord, ActivityRecord> RATING__FK_RATING_ACTIVITY = ForeignKeys0.RATING__FK_RATING_ACTIVITY;
    public static final ForeignKey<RatingRecord, UserRecord> RATING__FK_RATING_USER = ForeignKeys0.RATING__FK_RATING_USER;
    public static final ForeignKey<ReportActivityRecord, ActivityRecord> REPORT_ACTIVITY__FK_REPORT_ACTIVITY_ACTIVITY = ForeignKeys0.REPORT_ACTIVITY__FK_REPORT_ACTIVITY_ACTIVITY;
    public static final ForeignKey<ReportActivityRecord, UserRecord> REPORT_ACTIVITY__FK_ACTIVITY_IMAGE_USER = ForeignKeys0.REPORT_ACTIVITY__FK_ACTIVITY_IMAGE_USER;
    public static final ForeignKey<UserRecord, ImageRecord> USER__FK_USER_IMAGE = ForeignKeys0.USER__FK_USER_IMAGE;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<ActivityRecord, Long> IDENTITY_ACTIVITY = Internal.createIdentity(Activity.ACTIVITY, Activity.ACTIVITY.ID);
        public static Identity<CommentRecord, Long> IDENTITY_COMMENT = Internal.createIdentity(Comment.COMMENT, Comment.COMMENT.ID);
        public static Identity<ImageRecord, Long> IDENTITY_IMAGE = Internal.createIdentity(Image.IMAGE, Image.IMAGE.ID);
        public static Identity<RatingRecord, Long> IDENTITY_RATING = Internal.createIdentity(Rating.RATING, Rating.RATING.ID);
        public static Identity<TagRecord, Long> IDENTITY_TAG = Internal.createIdentity(Tag.TAG, Tag.TAG.ID);
        public static Identity<UserRecord, Long> IDENTITY_USER = Internal.createIdentity(User.USER, User.USER.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<ActivityRecord> PK_ACTIVITY = Internal.createUniqueKey(Activity.ACTIVITY, "pk_activity", Activity.ACTIVITY.ID);
        public static final UniqueKey<CommentRecord> PK_COMMENT = Internal.createUniqueKey(Comment.COMMENT, "pk_comment", Comment.COMMENT.ID);
        public static final UniqueKey<ImageRecord> PK_IMAGE = Internal.createUniqueKey(Image.IMAGE, "pk_image", Image.IMAGE.ID);
        public static final UniqueKey<RatingRecord> PK_RATING = Internal.createUniqueKey(Rating.RATING, "pk_rating", Rating.RATING.ID);
        public static final UniqueKey<TagRecord> PK_TAG = Internal.createUniqueKey(Tag.TAG, "pk_tag", Tag.TAG.ID);
        public static final UniqueKey<UserRecord> PK_USER = Internal.createUniqueKey(User.USER, "pk_user", User.USER.ID);
        public static final UniqueKey<UserRecord> USER_DISPLAY_NAME_KEY = Internal.createUniqueKey(User.USER, "user_display_name_key", User.USER.DISPLAY_NAME);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<ActivityRecord, UserRecord> ACTIVITY__FK_ACTIVITY_USER_CREATED_BY = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_USER, Activity.ACTIVITY, "activity__fk_activity_user_created_by", Activity.ACTIVITY.CREATED_BY_USER_ID);
        public static final ForeignKey<ActivityRecord, UserRecord> ACTIVITY__FK_ACTIVITY_USER_UPDATED_BY = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_USER, Activity.ACTIVITY, "activity__fk_activity_user_updated_by", Activity.ACTIVITY.UPDATED_BY_USER_ID);
        public static final ForeignKey<ActivityImageRecord, ActivityRecord> ACTIVITY_IMAGE__FK_ACTIVITY_IMAGE_ACTIVITY = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_ACTIVITY, ActivityImage.ACTIVITY_IMAGE, "activity_image__fk_activity_image_activity", ActivityImage.ACTIVITY_IMAGE.ACTIVITY_ID);
        public static final ForeignKey<ActivityImageRecord, ImageRecord> ACTIVITY_IMAGE__FK_ACTIVITY_IMAGE_IMAGE = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_IMAGE, ActivityImage.ACTIVITY_IMAGE, "activity_image__fk_activity_image_image", ActivityImage.ACTIVITY_IMAGE.IMAGE_ID);
        public static final ForeignKey<ActivityTagRecord, ActivityRecord> ACTIVITY_TAG__FK_ACTIVITY_TAG_ACTIVITY = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_ACTIVITY, ActivityTag.ACTIVITY_TAG, "activity_tag__fk_activity_tag_activity", ActivityTag.ACTIVITY_TAG.ACTIVITY_ID);
        public static final ForeignKey<ActivityTagRecord, TagRecord> ACTIVITY_TAG__FK_ACTIVITY_TAG_TAG = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_TAG, ActivityTag.ACTIVITY_TAG, "activity_tag__fk_activity_tag_tag", ActivityTag.ACTIVITY_TAG.TAG_ID);
        public static final ForeignKey<CommentRecord, ActivityRecord> COMMENT__FK_ACTIVITY_COMMENT = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_ACTIVITY, Comment.COMMENT, "comment__fk_activity_comment", Comment.COMMENT.ACTIVITY_ID);
        public static final ForeignKey<CommentRecord, UserRecord> COMMENT__FK_USER_COMMENT = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_USER, Comment.COMMENT, "comment__fk_user_comment", Comment.COMMENT.USER_ID);
        public static final ForeignKey<FavoriteRecord, ActivityRecord> FAVORITE__FK_FAVORITE_ACTIVITY = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_ACTIVITY, Favorite.FAVORITE, "favorite__fk_favorite_activity", Favorite.FAVORITE.ACTIVITY_ID);
        public static final ForeignKey<FavoriteRecord, UserRecord> FAVORITE__FK_FAVORITE_USER = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_USER, Favorite.FAVORITE, "favorite__fk_favorite_user", Favorite.FAVORITE.USER_ID);
        public static final ForeignKey<RatingRecord, ActivityRecord> RATING__FK_RATING_ACTIVITY = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_ACTIVITY, Rating.RATING, "rating__fk_rating_activity", Rating.RATING.ACTIVITY_ID);
        public static final ForeignKey<RatingRecord, UserRecord> RATING__FK_RATING_USER = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_USER, Rating.RATING, "rating__fk_rating_user", Rating.RATING.USER_ID);
        public static final ForeignKey<ReportActivityRecord, ActivityRecord> REPORT_ACTIVITY__FK_REPORT_ACTIVITY_ACTIVITY = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_ACTIVITY, ReportActivity.REPORT_ACTIVITY, "report_activity__fk_report_activity_activity", ReportActivity.REPORT_ACTIVITY.ACTIVITY_ID);
        public static final ForeignKey<ReportActivityRecord, UserRecord> REPORT_ACTIVITY__FK_ACTIVITY_IMAGE_USER = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_USER, ReportActivity.REPORT_ACTIVITY, "report_activity__fk_activity_image_user", ReportActivity.REPORT_ACTIVITY.USER_ID);
        public static final ForeignKey<UserRecord, ImageRecord> USER__FK_USER_IMAGE = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_IMAGE, User.USER, "user__fk_user_image", User.USER.IMAGE_ID);
    }
}
