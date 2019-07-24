/*
 * This file is generated by jOOQ.
 */
package com.mgmtp.internship.experiences.model.tables.tables;


import com.mgmtp.internship.experiences.model.tables.Indexes;
import com.mgmtp.internship.experiences.model.tables.Keys;
import com.mgmtp.internship.experiences.model.tables.Public;
import com.mgmtp.internship.experiences.model.tables.tables.records.ActivityRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * activity table
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Activity extends TableImpl<ActivityRecord> {

    private static final long serialVersionUID = 1221524532;

    /**
     * The reference instance of <code>public.activity</code>
     */
    public static final Activity ACTIVITY = new Activity();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ActivityRecord> getRecordType() {
        return ActivityRecord.class;
    }

    /**
     * The column <code>public.activity.id</code>.
     */
    public final TableField<ActivityRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('activity_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.activity.name</code>.
     */
    public final TableField<ActivityRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.activity.address</code>.
     */
    public final TableField<ActivityRecord, String> ADDRESS = createField("address", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.activity.description</code>.
     */
    public final TableField<ActivityRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.activity.created_by_user_id</code>.
     */
    public final TableField<ActivityRecord, Long> CREATED_BY_USER_ID = createField("created_by_user_id", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.activity.updated_by_user_id</code>.
     */
    public final TableField<ActivityRecord, Long> UPDATED_BY_USER_ID = createField("updated_by_user_id", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.activity.created_date</code>.
     */
    public final TableField<ActivityRecord, Timestamp> CREATED_DATE = createField("created_date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>public.activity.updated_date</code>.
     */
    public final TableField<ActivityRecord, Timestamp> UPDATED_DATE = createField("updated_date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>public.activity.active_date</code>.
     */
    public final TableField<ActivityRecord, Timestamp> ACTIVE_DATE = createField("active_date", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * Create a <code>public.activity</code> table reference
     */
    public Activity() {
        this(DSL.name("activity"), null);
    }

    /**
     * Create an aliased <code>public.activity</code> table reference
     */
    public Activity(String alias) {
        this(DSL.name(alias), ACTIVITY);
    }

    /**
     * Create an aliased <code>public.activity</code> table reference
     */
    public Activity(Name alias) {
        this(alias, ACTIVITY);
    }

    private Activity(Name alias, Table<ActivityRecord> aliased) {
        this(alias, aliased, null);
    }

    private Activity(Name alias, Table<ActivityRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("activity table"));
    }

    public <O extends Record> Activity(Table<O> child, ForeignKey<O, ActivityRecord> key) {
        super(child, key, ACTIVITY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PK_ACTIVITY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ActivityRecord, Long> getIdentity() {
        return Keys.IDENTITY_ACTIVITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ActivityRecord> getPrimaryKey() {
        return Keys.PK_ACTIVITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ActivityRecord>> getKeys() {
        return Arrays.<UniqueKey<ActivityRecord>>asList(Keys.PK_ACTIVITY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ActivityRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ActivityRecord, ?>>asList(Keys.ACTIVITY__FK_ACTIVITY_USER_CREATED_BY, Keys.ACTIVITY__FK_ACTIVITY_USER_UPDATED_BY);
    }

    public User activity_FkActivityUserCreatedBy() {
        return new User(this, Keys.ACTIVITY__FK_ACTIVITY_USER_CREATED_BY);
    }

    public User activity_FkActivityUserUpdatedBy() {
        return new User(this, Keys.ACTIVITY__FK_ACTIVITY_USER_UPDATED_BY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Activity as(String alias) {
        return new Activity(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Activity as(Name alias) {
        return new Activity(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Activity rename(String name) {
        return new Activity(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Activity rename(Name name) {
        return new Activity(name, null);
    }
}
