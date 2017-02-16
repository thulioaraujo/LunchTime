package com.dbserver.lunchtime.data;

import android.provider.BaseColumns;

/**
 * Represents the Vote contract to define its table and columns
 *
 * @author Thúlio Araújo (thuliolins@gmail.com)
 * @since 2/13/2017
 */
public final class VoteContract {

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + VoteEntry.TABLE_NAME;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + VoteEntry.TABLE_NAME + " (" +
                    VoteEntry.VOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    VoteEntry.VOTE_RESTAURANT_ID + " INTEGER, " +
                    VoteEntry.VOTE_CUSTOMER_ID + " INTEGER, " +
                    VoteEntry.VOTE_DAY + " DATE" +
                    VoteEntry.VOTE_REGISTER_DT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                    "FOREIGN KEY (" + VoteEntry.VOTE_RESTAURANT_ID + ") REFERENCES " + VoteEntry.RESTAURANT_TABLE_NAME + "(" + VoteEntry.RESTAURANT_ID + "), " +
                    "FOREIGN KEY (" + VoteEntry.VOTE_CUSTOMER_ID + ") REFERENCES " + VoteEntry.CUSTOMER_TABLE_NAME + "(" + VoteEntry.CUSTOMER_ID + ")" +
                    ");";

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private VoteContract() {
    }

    /* Inner class that defines the table contents */
    public static class VoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "vote";
        public static final String VOTE_ID = "vote_id";
        public static final String VOTE_RESTAURANT_ID = "vote_restaurant_id";
        public static final String VOTE_CUSTOMER_ID = "vote_customer_id";
        public static final String VOTE_DAY = "vote_day";
        public static final String VOTE_REGISTER_DT = "vote_register_dt";

        // To create its foreign key
        public static final String RESTAURANT_TABLE_NAME = "restaurant";
        public static final String RESTAURANT_ID = "restaurant_id";
        public static final String CUSTOMER_TABLE_NAME = "customer";
        public static final String CUSTOMER_ID = "customer_id";
    }

    public static String getSqlDeleteEntries() {
        return SQL_DELETE_ENTRIES;
    }

    public static String getSqlCreateEntries() {
        return SQL_CREATE_ENTRIES;
    }
}
