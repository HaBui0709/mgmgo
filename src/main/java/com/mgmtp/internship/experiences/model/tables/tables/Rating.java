/*
 * This file is generated by jOOQ.
 */
package com.mgmtp.internship.experiences.model.tables.tables;


import com.mgmtp.internship.experiences.model.tables.Indexes;
import com.mgmtp.internship.experiences.model.tables.Keys;
import com.mgmtp.internship.experiences.model.tables.Public;
import com.mgmtp.internship.experiences.model.tables.tables.records.RatingRecord;

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
 * rating table
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Rating extends TableImpl<RatingRecord> {

    private static final long serialVersionUID = -1032340981;

    /**
     * The reference instance of <code>public.rating</code>
     */
    public static final Rating RATING = new Rating();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RatingRecord> getRecordType() {
        return RatingRecord.class;
    }

    /**
     * The column <code>public.rating.id</code>.
     */
    public final TableField<RatingRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('rating_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.rating.activity_id</code>.
     */
    public final TableField<RatingRecord, Long> ACTIVITY_ID = createField("activity_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.rating.user_id</code>.
     */
    public final TableField<RatingRecord, Long> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.rating.value</code>.
     */
    public final TableField<RatingRecord, Integer> VALUE = createField("value", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>public.rating</code> table reference
     */
    public Rating() {
        this(DSL.name("rating"), null);
    }

    /**
     * Create an aliased <code>public.rating</code> table reference
     */
    public Rating(String alias) {
        this(DSL.name(alias), RATING);
    }

    /**
     * Create an aliased <code>public.rating</code> table reference
     */
    public Rating(Name alias) {
        this(alias, RATING);
    }

    private Rating(Name alias, Table<RatingRecord> aliased) {
        this(alias, aliased, null);
    }

    private Rating(Name alias, Table<RatingRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("rating table"));
    }

    public <O extends Record> Rating(Table<O> child, ForeignKey<O, RatingRecord> key) {
        super(child, key, RATING);
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
        return Arrays.<Index>asList(Indexes.PK_RATING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<RatingRecord, Long> getIdentity() {
        return Keys.IDENTITY_RATING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<RatingRecord> getPrimaryKey() {
        return Keys.PK_RATING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<RatingRecord>> getKeys() {
        return Arrays.<UniqueKey<RatingRecord>>asList(Keys.PK_RATING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<RatingRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<RatingRecord, ?>>asList(Keys.RATING__FK_RATING_ACTIVITY, Keys.RATING__FK_RATING_USER);
    }

    public Activity activity() {
        return new Activity(this, Keys.RATING__FK_RATING_ACTIVITY);
    }

    public User user() {
        return new User(this, Keys.RATING__FK_RATING_USER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rating as(String alias) {
        return new Rating(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rating as(Name alias) {
        return new Rating(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Rating rename(String name) {
        return new Rating(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Rating rename(Name name) {
        return new Rating(name, null);
    }
}