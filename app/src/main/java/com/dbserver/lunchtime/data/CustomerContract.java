package com.dbserver.lunchtime.data;

import android.provider.BaseColumns;

/**
 * Represents the Customer contract to define its table and columns
 *
 * @author Thúlio Araújo (thuliolins@gmail.com)
 * @since 2/13/2017
 */
public final class CustomerContract {

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + CustomerEntry.TABLE_NAME;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CustomerEntry.TABLE_NAME + " (" +
                    CustomerEntry.CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CustomerEntry.CUSTOMER_NAME + " TEXT, " +
                    CustomerEntry.CUSTOMER_EMAIL + " TEXT, " +
                    CustomerEntry.CUSTOMER_PASSWORD + " TEXT, " +
                    CustomerEntry.CUSTOMER_REGISTER_DT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");";

    private static final String[] COLUMNS = {CustomerEntry.CUSTOMER_ID,
            CustomerEntry.CUSTOMER_NAME,
            CustomerEntry.CUSTOMER_EMAIL,
            CustomerEntry.CUSTOMER_PASSWORD,
            CustomerEntry.CUSTOMER_REGISTER_DT};

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private CustomerContract() {
    }

    public static String getSqlDeleteEntries() {
        return SQL_DELETE_ENTRIES;
    }

    public static String getSqlCreateEntries() {
        return SQL_CREATE_ENTRIES;
    }

    public static String[] getCOLUMNS() {
        return COLUMNS;
    }

    /* Inner class that defines the table contents */
    public static class CustomerEntry implements BaseColumns {
        public static final String TABLE_NAME = "customer";
        public static final String CUSTOMER_ID = "customer_id";
        public static final String CUSTOMER_NAME = "customer_name";
        public static final String CUSTOMER_EMAIL = "customer_email";
        public static final String CUSTOMER_PASSWORD = "customer_password";
        public static final String CUSTOMER_REGISTER_DT = "customer_register_dt";
    }
}
