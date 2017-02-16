package com.dbserver.lunchtime.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dbserver.lunchtime.data.CustomerContract;
import com.dbserver.lunchtime.data.LunchTimeDbHelper;
import com.dbserver.lunchtime.model.Customer;
import com.dbserver.lunchtime.util.Utils;

/**
 * This class implements the Customer CRUD Interface
 *
 * @author Thúlio Araújo (thuliolins@gmail.com)
 * @since 2/13/2017
 */
public class CustomerDAOImpl extends LunchTimeDbHelper implements CustomerDAO {

    private final Context mContext;

    public CustomerDAOImpl(final Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public Customer customerLogin(String customerEmail, String customerPassword) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CustomerContract.CustomerEntry.TABLE_NAME, CustomerContract.getCOLUMNS(),
                CustomerContract.CustomerEntry.CUSTOMER_EMAIL + "=? AND " + CustomerContract.CustomerEntry.CUSTOMER_PASSWORD + "=?",
                new String[]{customerEmail, customerPassword}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Customer customer = new Customer(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), Utils.convertStringToDate(cursor.getString(4)));

        cursor.close();
        db.close();
        return customer;
    }

    @Override
    public boolean createCustomer(final Customer customer) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CustomerContract.CustomerEntry.CUSTOMER_NAME, customer.getCustomerName());
        contentValues.put(CustomerContract.CustomerEntry.CUSTOMER_EMAIL, customer.getCustomerEmail());
        contentValues.put(CustomerContract.CustomerEntry.CUSTOMER_PASSWORD, customer.getCustomerPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert(CustomerContract.CustomerEntry.TABLE_NAME, null, contentValues) > 0;

        db.close();
        return createSuccessful;
    }

    @Override
    public Customer getCustomerByEmail(final String customerEmail) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CustomerContract.CustomerEntry.TABLE_NAME, CustomerContract.getCOLUMNS(),
                CustomerContract.CustomerEntry.CUSTOMER_EMAIL + "=?",
                new String[]{customerEmail}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Customer customer = new Customer(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), Utils.convertStringToDate(cursor.getString(4)));

        cursor.close();
        db.close();
        return customer;
    }

    @Override
    public int deleteCustomerByEmail(String customerEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Define 'where' part of query.
        String selection = CustomerContract.CustomerEntry.CUSTOMER_EMAIL + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {customerEmail};
        // Issue SQL statement.
        int deleteSucessfull = db.delete(CustomerContract.CustomerEntry.TABLE_NAME, selection, selectionArgs);

        db.close();
        return deleteSucessfull;
    }
}
