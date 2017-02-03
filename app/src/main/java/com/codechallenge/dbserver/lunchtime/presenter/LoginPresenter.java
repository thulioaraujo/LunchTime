package com.codechallenge.dbserver.lunchtime.presenter;

import android.support.annotation.Nullable;

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

public class LoginPresenter {

    private JSONObject loginResult;
    private boolean signupResult;
    private static LoginPresenter getInstance = null;

    private LoginPresenter(){
        this.loginResult = null;
        this.signupResult = false;
    }

    public static LoginPresenter getInstance() {
        if (getInstance == null) {
            getInstance = new LoginPresenter();
        }
        return getInstance;
    }

    /**
     * Method gets triggered when Login button is clicked
     *
     * @param userName, userPassword
     */
    public JSONObject loginUser(String userName, String userPassword){
        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        // Put Http parameter username with value of Email Edit View control
        params.put("username", userName);
        // Put Http parameter password with value of Password Edit Value control
        params.put("password", userPassword);
        // Invoke RESTful Web Service with Http parameters
        checkLoginOnWS(params);
        return this.loginResult;
    }

    /**
     * Method that performs RESTful webservice invocations
     *
0
     * @param params
     */
    private void checkLoginOnWS(RequestParams params){
        SyncHttpClient client = new SyncHttpClient();
        this.loginResult = null;

        client.get("https://stark-temple-49959.herokuapp.com/user_controller/dologin", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                try {
                    // When the JSON response has status boolean value assigned with true
                    if(responseBody.getBoolean("logged")){
                        loginResult = responseBody;
                    }
                    // Else display error message
                    else{
                        loginResult = null;
                    }
                } catch (JSONException e) {
                    loginResult = null;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {// When Http response code is '404'
                if(statusCode == 404){
                    loginResult = errorResponse;
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    loginResult = errorResponse;
                }
            }
        });
    }

    /**
     * Method gets triggered when Register button is clicked
     *
     * @param user
     */
    public boolean signupUser(User user){
        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        // Put Http parameter name with value of Name Edit View control
        params.put("name", user.getUserName());
        // Put Http parameter username with value of Email Edit View control
        params.put("username", user.getUserEmail());
        // Put Http parameter password with value of Password Edit View control
        params.put("password", user.getUserPassword());
        // Invoke RESTful Web Service with Http parameters
        regiserUserOnWS(params);
        return this.signupResult;
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     */
    private void regiserUserOnWS(RequestParams params){
        // Make RESTful webservice call using AsyncHttpClient object
        SyncHttpClient client = new SyncHttpClient();
        this.signupResult = false;
        client.get("https://stark-temple-49959.herokuapp.com/user_controller/doregister",params ,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // When the JSON response has status boolean value assigned with true
                    if(response.getBoolean("status")){
                        //Toast.makeText(ctx, "You are successfully logged in!", Toast.LENGTH_LONG).show();
                        signupResult =  true;
                    }
                    // Else display error message
                    else{
                        //Toast.makeText(ctx, responseBody.getString("error_msg"), Toast.LENGTH_LONG).show();
                        signupResult = false;
                    }
                } catch (JSONException e) {
                    //Toast.makeText(ctx, "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    signupResult = false;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // When Http response code is '404'
                if(statusCode == 404){
                    signupResult = false;
                    //Toast.makeText(ctx, "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    signupResult = false;
                    //Toast.makeText(ctx, "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean validadeEmptyFields(@Nullable String userEmail, @Nullable String userPassword) {
        if (userEmail != null && userPassword != null) {
            if (!userEmail.isEmpty() && !userPassword.isEmpty()) {
                return true;
            }
        }

        return false;
    }
}
