package com.bewant2be.doit.jcentertest.sqlite;

import android.provider.BaseColumns;

/**
 * Created by user on 6/12/17.
 */
public final class UserInfo {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserInfo() {}

    /* Inner class that defines the table contents */
    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        public static final String COLUMN_NAME_BINARY_FEATURE = "feature";
        public static final String COLUMN_NAME_BINARY_IMG = "image";
    }
}
