/*
 * This file is generated by jOOQ.
 */
package com.mgmtp.internship.experiences.model.tables;


import com.mgmtp.internship.experiences.model.tables.tables.Activity;
import com.mgmtp.internship.experiences.model.tables.tables.ActivityImage;
import com.mgmtp.internship.experiences.model.tables.tables.Favorite;
import com.mgmtp.internship.experiences.model.tables.tables.Image;
import com.mgmtp.internship.experiences.model.tables.tables.Rating;
import com.mgmtp.internship.experiences.model.tables.tables.ReportActivity;
import com.mgmtp.internship.experiences.model.tables.tables.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = -1013987866;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * activity table
     */
    public final Activity ACTIVITY = com.mgmtp.internship.experiences.model.tables.tables.Activity.ACTIVITY;

    /**
     * activity image table
     */
    public final ActivityImage ACTIVITY_IMAGE = com.mgmtp.internship.experiences.model.tables.tables.ActivityImage.ACTIVITY_IMAGE;

    /**
     * favorite table
     */
    public final Favorite FAVORITE = com.mgmtp.internship.experiences.model.tables.tables.Favorite.FAVORITE;

    /**
     * image table
     */
    public final Image IMAGE = com.mgmtp.internship.experiences.model.tables.tables.Image.IMAGE;

    /**
     * rating table
     */
    public final Rating RATING = com.mgmtp.internship.experiences.model.tables.tables.Rating.RATING;

    /**
     * report activity table
     */
    public final ReportActivity REPORT_ACTIVITY = com.mgmtp.internship.experiences.model.tables.tables.ReportActivity.REPORT_ACTIVITY;

    /**
     * user table
     */
    public final User USER = com.mgmtp.internship.experiences.model.tables.tables.User.USER;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        List result = new ArrayList();
        result.addAll(getSequences0());
        return result;
    }

    private final List<Sequence<?>> getSequences0() {
        return Arrays.<Sequence<?>>asList(
            Sequences.ACTIVITY_ID_SEQ,
            Sequences.IMAGE_ID_SEQ,
            Sequences.RATING_ID_SEQ,
            Sequences.USER_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Activity.ACTIVITY,
            ActivityImage.ACTIVITY_IMAGE,
            Favorite.FAVORITE,
            Image.IMAGE,
            Rating.RATING,
            ReportActivity.REPORT_ACTIVITY,
            User.USER);
    }
}
