package com.dbserver.lunchtime.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class implements the functionality to create and update the database of LunchTime application.
 *
 * @author Thúlio Araújo (thuliolins@gmail.com)
 * @since 2/13/2017
 */
public class LunchTimeDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LunchTime.db";

    public LunchTimeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CustomerContract.getSqlCreateEntries());
        sqLiteDatabase.execSQL(RestaurantContract.getSqlCreateEntries());
        sqLiteDatabase.execSQL(VoteContract.getSqlCreateEntries());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        sqLiteDatabase.execSQL(VoteContract.getSqlDeleteEntries());
        sqLiteDatabase.execSQL(RestaurantContract.getSqlDeleteEntries());
        sqLiteDatabase.execSQL(CustomerContract.getSqlDeleteEntries());
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
