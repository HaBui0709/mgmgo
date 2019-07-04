/*
 * This file is generated by jOOQ.
 */
package com.mgmtp.internship.experiences.model.tables;


import com.mgmtp.internship.experiences.model.tables.tables.Activity;
import com.mgmtp.internship.experiences.model.tables.tables.Image;
import com.mgmtp.internship.experiences.model.tables.tables.Rating;
import com.mgmtp.internship.experiences.model.tables.tables.User;
import com.mgmtp.internship.experiences.model.tables.tables.records.ActivityRecord;
import com.mgmtp.internship.experiences.model.tables.tables.records.ImageRecord;
import com.mgmtp.internship.experiences.model.tables.tables.records.RatingRecord;
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
    public static final Identity<ImageRecord, Long> IDENTITY_IMAGE = Identities0.IDENTITY_IMAGE;
    public static final Identity<RatingRecord, Long> IDENTITY_RATING = Identities0.IDENTITY_RATING;
    public static final Identity<UserRecord, Long> IDENTITY_USER = Identities0.IDENTITY_USER;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ActivityRecord> PK_ACTIVITY = UniqueKeys0.PK_ACTIVITY;
    public static final UniqueKey<ImageRecord> PK_IMAGE = UniqueKeys0.PK_IMAGE;
    public static final UniqueKey<RatingRecord> PK_RATING = UniqueKeys0.PK_RATING;
    public static final UniqueKey<UserRecord> PK_USER = UniqueKeys0.PK_USER;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<ActivityRecord, UserRecord> ACTIVITY__FK_ACTIVITY_USER_CREATED_BY = ForeignKeys0.ACTIVITY__FK_ACTIVITY_USER_CREATED_BY;
    public static final ForeignKey<ActivityRecord, UserRecord> ACTIVITY__FK_ACTIVITY_USER_UPDATED_BY = ForeignKeys0.ACTIVITY__FK_ACTIVITY_USER_UPDATED_BY;
    public static final ForeignKey<RatingRecord, ActivityRecord> RATING__FK_RATING_ACTIVITY = ForeignKeys0.RATING__FK_RATING_ACTIVITY;
    public static final ForeignKey<RatingRecord, UserRecord> RATING__FK_RATING_USER = ForeignKeys0.RATING__FK_RATING_USER;
    public static final ForeignKey<UserRecord, ImageRecord> USER__FK_USER_IMAGE = ForeignKeys0.USER__FK_USER_IMAGE;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<ActivityRecord, Long> IDENTITY_ACTIVITY = Internal.createIdentity(Activity.ACTIVITY, Activity.ACTIVITY.ID);
        public static Identity<ImageRecord, Long> IDENTITY_IMAGE = Internal.createIdentity(Image.IMAGE, Image.IMAGE.ID);
        public static Identity<RatingRecord, Long> IDENTITY_RATING = Internal.createIdentity(Rating.RATING, Rating.RATING.ID);
        public static Identity<UserRecord, Long> IDENTITY_USER = Internal.createIdentity(User.USER, User.USER.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<ActivityRecord> PK_ACTIVITY = Internal.createUniqueKey(Activity.ACTIVITY, "pk_activity", Activity.ACTIVITY.ID);
        public static final UniqueKey<ImageRecord> PK_IMAGE = Internal.createUniqueKey(Image.IMAGE, "pk_image", Image.IMAGE.ID);
        public static final UniqueKey<RatingRecord> PK_RATING = Internal.createUniqueKey(Rating.RATING, "pk_rating", Rating.RATING.ID);
        public static final UniqueKey<UserRecord> PK_USER = Internal.createUniqueKey(User.USER, "pk_user", User.USER.ID);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<ActivityRecord, UserRecord> ACTIVITY__FK_ACTIVITY_USER_CREATED_BY = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_USER, Activity.ACTIVITY, "activity__fk_activity_user_created_by", Activity.ACTIVITY.CREATED_BY_USER_ID);
        public static final ForeignKey<ActivityRecord, UserRecord> ACTIVITY__FK_ACTIVITY_USER_UPDATED_BY = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_USER, Activity.ACTIVITY, "activity__fk_activity_user_updated_by", Activity.ACTIVITY.UPDATED_BY_USER_ID);
        public static final ForeignKey<RatingRecord, ActivityRecord> RATING__FK_RATING_ACTIVITY = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_ACTIVITY, Rating.RATING, "rating__fk_rating_activity", Rating.RATING.ACTIVITY_ID);
        public static final ForeignKey<RatingRecord, UserRecord> RATING__FK_RATING_USER = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_USER, Rating.RATING, "rating__fk_rating_user", Rating.RATING.USER_ID);
        public static final ForeignKey<UserRecord, ImageRecord> USER__FK_USER_IMAGE = Internal.createForeignKey(com.mgmtp.internship.experiences.model.tables.Keys.PK_IMAGE, User.USER, "user__fk_user_image", User.USER.IMAGE_ID);
    }
}
