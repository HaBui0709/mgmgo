/*
 * This file is generated by jOOQ.
 */
package com.mgmtp.internship.experiences.model.tables.tables.records;


import com.mgmtp.internship.experiences.model.tables.tables.ActivityTag;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.TableRecordImpl;


/**
 * activity tag table
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ActivityTagRecord extends TableRecordImpl<ActivityTagRecord> implements Record2<Long, Long> {

    private static final long serialVersionUID = -608976571;

    /**
     * Setter for <code>public.activity_tag.activity_id</code>.
     */
    public void setActivityId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.activity_tag.activity_id</code>.
     */
    public Long getActivityId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.activity_tag.tag_id</code>.
     */
    public void setTagId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.activity_tag.tag_id</code>.
     */
    public Long getTagId() {
        return (Long) get(1);
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Long, Long> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return ActivityTag.ACTIVITY_TAG.ACTIVITY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return ActivityTag.ACTIVITY_TAG.TAG_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
        return getActivityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component2() {
        return getTagId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getActivityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getTagId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityTagRecord value1(Long value) {
        setActivityId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityTagRecord value2(Long value) {
        setTagId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityTagRecord values(Long value1, Long value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ActivityTagRecord
     */
    public ActivityTagRecord() {
        super(ActivityTag.ACTIVITY_TAG);
    }

    /**
     * Create a detached, initialised ActivityTagRecord
     */
    public ActivityTagRecord(Long activityId, Long tagId) {
        super(ActivityTag.ACTIVITY_TAG);

        set(0, activityId);
        set(1, tagId);
    }
}