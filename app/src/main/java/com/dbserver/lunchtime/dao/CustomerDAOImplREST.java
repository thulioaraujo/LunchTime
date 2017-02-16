package com.dbserver.lunchtime.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dbserver.lunchtime.data.CustomerContract;
import com.dbserver.lunchtime.data.LunchTimeDbHelper;
import com.dbserver.lunchtime.model.Customer;
import com.dbserver.lunchtime.util.ClientConnection;
import com.dbserver.lunchtime.util.Logger;
import com.dbserver.lunchtime.util.Utils;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class implements the Customer CRUD Interface
 *
 * CustomerDAOImplREST communicates with LunchTimeWS
 *
 * @author Thúlio Araújo (thuliolins@gmail.com)
 * @since 2/13/2017
 */
public class CustomerDAOImplREST implements CustomerDAO {

    private final Context mContext;
    private JSONObject jsonObject;

    // JSON Main Handler
    private JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                jsonObject = response;
            } catch (Exception e) {
                Logger.error("Error Responce: " + e.getMessage());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);

            Logger.error("Error Responce: " + errorResponse);
        }
    };

    public CustomerDAOImplREST(final Context context) {
        this.mContext = context;
    }

    @Override
    public Customer customerLogin(String customerEmail, String customerPassword) {
        this.jsonObject = null;
        Customer customer = null;

        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        // Put Http parameter username with value of Email Edit View control
        params.put("customerEmail", customerEmail);
        // Put Http parameter password with value of Password Edit Value control
        params.put("customerPassword", customerPassword);

        // Invoke RESTful Web Service
        String baseUrl = "customer/logincustomer";
        ClientConnection.getAccess(baseUrl, params, handler);

        if (jsonObject != null) {
            Gson gson = new Gson();
            customer = gson.fromJson(jsonObject.toString(), Customer.class);
        } else {
            return null;
        }

        return customer;
    }

    @Override
    public boolean createCustomer(final Customer customer) {
        boolean result = false;
        this.jsonObject = null;
        Gson gson = new Gson();
        Customer customerFromWS = null;
        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        // Put Http parameter username with value of Email Edit View control
        params.put("customer", gson.toJson(customer));

        // Invoke RESTful Web Service
        String baseUrl = "customer/addcustomer";
        ClientConnection.postAccess(baseUrl, params, handler);

        if (jsonObject != null) {
            customerFromWS = gson.fromJson(jsonObject.toString(), Customer.class);

            if (customerFromWS != null && !customerFromWS.getCustomerName().equals("")) {
                result = true;
            }
        }

        return result;
    }

    @Override
    public Customer getCustomerByEmail(final String customerEmail) {
        return null;
    }

    @Override
    public int deleteCustomerByEmail(String customerEmail) {
        return 0;
    }
}
