package com.codechallenge.dbserver.lunchtime.controller;

import com.codechallenge.dbserver.lunchtime.models.Restaurant;
import com.codechallenge.dbserver.lunchtime.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by thulioaraujo on 1/10/2017.
 */
public class RestaurantController {

    private boolean registerResult;
    private boolean isRestaurantExists;
    private static RestaurantController getInstance = null;

    private RestaurantController(){
        this.registerResult = false;
        this.isRestaurantExists = false;
    }

    public static RestaurantController getInstance() {
        if (getInstance == null) {
            getInstance = new RestaurantController();
        }
        return getInstance;
    }

    /**
     * Method gets triggered when a new restaurant is listed nearby the user
     *
     * @param restaurantName
     */
    public boolean checkIfRestaurantExists(String restaurantName){
        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        // Put Http parameter name with value of Restaurant name
        params.put("name", restaurantName);
        checkIfRestaurantExistsOnWS(params);
        return this.isRestaurantExists;
    }

    /**
     * Method gets triggered when a new restaurant is listed nearby the user
     *
     * @param restaurant
     */
    public boolean registerRestaurant(Restaurant restaurant){
        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        // Put Http parameter name with value of Restaurant name
        params.put("name", restaurant.getRestaurantName());
        // Put Http parameter adress with value of Restaurant adress Edit Value control
        params.put("adress", restaurant.getRestaurantAddress());
        // Put Http´parameter latitude with value of Restaurant latitude Edit Value control
        params.put("latitude", restaurant.getRestaurantLatitude());
        // Put Http´parameter longitude with value of Restaurant longitude Edit Value control
        params.put("longitude", restaurant.getRestaurantLongitude());
        // Invoke RESTful Web Service with Http parameters
        registerRestaurantOnWS(params);
        return this.registerResult;
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     */
    private void registerRestaurantOnWS(RequestParams params){
        // Make RESTful webservice call using AsyncHttpClient object
        SyncHttpClient client = new SyncHttpClient();
        this.registerResult = false;
        client.get("https://stark-temple-49959.herokuapp.com/restaurant_controller/doregister",params ,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // When the JSON response has status boolean value assigned with true
                    if(response.getBoolean("status")){
                        //Toast.makeText(ctx, "You are successfully logged in!", Toast.LENGTH_LONG).show();
                        registerResult =  true;
                    }
                    // Else display error message
                    else{
                        //Toast.makeText(ctx, responseBody.getString("error_msg"), Toast.LENGTH_LONG).show();
                        registerResult = false;
                    }
                } catch (JSONException e) {
                    //Toast.makeText(ctx, "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    registerResult = false;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // When Http response code is '404'
                if(statusCode == 404){
                    registerResult = false;
                    //Toast.makeText(ctx, "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    registerResult = false;
                    //Toast.makeText(ctx, "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     */
    private void checkIfRestaurantExistsOnWS(RequestParams params){
        // Make RESTful webservice call using AsyncHttpClient object
        SyncHttpClient client = new SyncHttpClient();
        this.isRestaurantExists = false;
        client.get("https://stark-temple-49959.herokuapp.com/restaurant_controller/checkifexists",params ,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // When the JSON response has status boolean value assigned with true
                    if(response.getBoolean("status")){
                        //Toast.makeText(ctx, "You are successfully logged in!", Toast.LENGTH_LONG).show();
                        isRestaurantExists =  true;
                    }
                    // Else display error message
                    else{
                        //Toast.makeText(ctx, responseBody.getString("error_msg"), Toast.LENGTH_LONG).show();
                        isRestaurantExists = false;
                    }
                } catch (JSONException e) {
                    //Toast.makeText(ctx, "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    isRestaurantExists = false;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // When Http response code is '404'
                if(statusCode == 404){
                    isRestaurantExists = false;
                    //Toast.makeText(ctx, "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    isRestaurantExists = false;
                    //Toast.makeText(ctx, "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
