package com.dbserver.lunchtime.data;

import android.provider.BaseColumns;

/**
 * Represents the Restaurant contract to define its table and columns
 *
 * @author Thúlio Araújo (thuliolins@gmail.com)
 * @since 2/13/2017
 */
public final class RestaurantContract {

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + RestaurantEntry.TABLE_NAME;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + RestaurantEntry.TABLE_NAME + " (" +
                    RestaurantEntry.RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    RestaurantEntry.RESTAURANT_NAME + " TEXT, " +
                    RestaurantEntry.RESTAURANT_ADDRESS + " TEXT, " +
                    RestaurantEntry.RESTAURANT_LATITUDE + " DOUBLE, " +
                    RestaurantEntry.RESTAURANT_LONGITUDE + " DOUBLE, " +
                    RestaurantEntry.RESTAURANT_REGISTER_DT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");";

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private RestaurantContract() {
    }

    /* Inner class that defines the table contents */
    public static class RestaurantEntry implements BaseColumns {
        public static final String TABLE_NAME = "restaurant";
        public static final String RESTAURANT_ID = "restaurant_id";
        public static final String RESTAURANT_NAME = "restaurant_name";
        public static final String RESTAURANT_ADDRESS = "restaurant_address";
        public static final String RESTAURANT_LATITUDE = "restaurant_latitude";
        public static final String RESTAURANT_LONGITUDE = "restaurant_longitude";
        public static final String RESTAURANT_REGISTER_DT = "restaurant_register_dt";
    }

    public static String getSqlDeleteEntries() {
        return SQL_DELETE_ENTRIES;
    }

    public static String getSqlCreateEntries() {
        return SQL_CREATE_ENTRIES;
    }
}
