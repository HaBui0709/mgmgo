/*
 * This file is generated by jOOQ.
 */
package com.mgmtp.internship.experiences.model.tables;


import com.mgmtp.internship.experiences.model.tables.tables.Activity;
import com.mgmtp.internship.experiences.model.tables.tables.ActivityImage;
import com.mgmtp.internship.experiences.model.tables.tables.Comment;
import com.mgmtp.internship.experiences.model.tables.tables.Favorite;
import com.mgmtp.internship.experiences.model.tables.tables.Image;
import com.mgmtp.internship.experiences.model.tables.tables.Rating;
import com.mgmtp.internship.experiences.model.tables.tables.ReportActivity;
import com.mgmtp.internship.experiences.model.tables.tables.User;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in public
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * activity table
     */
    public static final Activity ACTIVITY = com.mgmtp.internship.experiences.model.tables.tables.Activity.ACTIVITY;

    /**
     * activity image table
     */
    public static final ActivityImage ACTIVITY_IMAGE = com.mgmtp.internship.experiences.model.tables.tables.ActivityImage.ACTIVITY_IMAGE;

    /**
     * comment table
     */
    public static final Comment COMMENT = com.mgmtp.internship.experiences.model.tables.tables.Comment.COMMENT;

    /**
     * favorite table
     */
    public static final Favorite FAVORITE = com.mgmtp.internship.experiences.model.tables.tables.Favorite.FAVORITE;

    /**
     * image table
     */
    public static final Image IMAGE = com.mgmtp.internship.experiences.model.tables.tables.Image.IMAGE;

    /**
     * rating table
     */
    public static final Rating RATING = com.mgmtp.internship.experiences.model.tables.tables.Rating.RATING;

    /**
     * report activity table
     */
    public static final ReportActivity REPORT_ACTIVITY = com.mgmtp.internship.experiences.model.tables.tables.ReportActivity.REPORT_ACTIVITY;

    /**
     * user table
     */
    public static final User USER = com.mgmtp.internship.experiences.model.tables.tables.User.USER;
}
