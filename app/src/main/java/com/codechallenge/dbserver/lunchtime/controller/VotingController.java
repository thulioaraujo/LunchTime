package com.codechallenge.dbserver.lunchtime.controller;

import com.codechallenge.dbserver.lunchtime.models.Vote;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by thulioaraujo on 1/10/2017.
 */

public class VotingController {

    private JSONObject mMostVotedRestaurant;
    private JSONObject voteResult;
    private int mRestaurantVotes;

    private static VotingController getInstance = null;

    private VotingController(){
        this.mRestaurantVotes = 0;
        this.voteResult = null;
        this.mMostVotedRestaurant = null;
    }

    public static VotingController getInstance() {
        if (getInstance == null) {
            getInstance = new VotingController();
        }
        return getInstance;
    }

    /**
     * Method to register the daily user vote
     *
     * @param
     */
    public JSONObject getmMostVotedRestaurant(){
        getMostVotedRestaurantOnWs();
        return mMostVotedRestaurant;
    }

    /**
     * Method to register the daily user vote
     *
     * @param vote
     */
    public JSONObject registerVote(Vote vote){
        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        // Put Http parameter user_username with value of userUserName
        params.put("user_username", vote.getUserUserName());
        // Put Http parameter restaurant_name with value of restaurantName
        params.put("restaurant_name", vote.getRestaurantName());
        registerVoteOnWs(params);
        return voteResult;
    }

    /**
     * Method gets the daily votes of the restaurant
     *
     * @param restaurantName
     */
    public int getDailyRestaurantVotes(String restaurantName){
        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        // Put Http parameter name with value of Restaurant name
        params.put("restaurant_name", restaurantName);
        getDailyRestaurantVotesOnWs(params);
        return mRestaurantVotes;
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param
     */
    private void getMostVotedRestaurantOnWs() {
        // Make RESTful webservice call using AsyncHttpClient object
        SyncHttpClient client = new SyncHttpClient();
        mMostVotedRestaurant = null;
        client.get("https://stark-temple-49959.herokuapp.com/voting_controller/getmostvotedrestaurantperday", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                mMostVotedRestaurant = response;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // When Http response code is '404'
                if(statusCode == 404){
                    mMostVotedRestaurant = null;
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    mMostVotedRestaurant = null;
                }
            }
        });
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     */
    private int getDailyRestaurantVotesOnWs(RequestParams params) {
        // Make RESTful webservice call using AsyncHttpClient object
        SyncHttpClient client = new SyncHttpClient();
        mRestaurantVotes = 0;
        client.get("https://stark-temple-49959.herokuapp.com/voting_controller/getdailyrestaurantvotes",params ,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // When the JSON response has votes int value assigned
                    mRestaurantVotes = response.getInt("value");

                } catch (JSONException e) {
                    mRestaurantVotes = 0;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // When Http response code is '404'
                if(statusCode == 404){
                    mRestaurantVotes = 0;
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    mRestaurantVotes = 0;
                }
            }
        });
        return mRestaurantVotes;
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     */
    private void registerVoteOnWs(RequestParams params){
        // Make RESTful webservice call using SyncHttpClient object
        SyncHttpClient client = new SyncHttpClient();
        voteResult = null;
        client.get("https://stark-temple-49959.herokuapp.com/voting_controller/dovote",params ,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                voteResult = response;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // When Http response code is '404'
                if(statusCode == 404){
                    voteResult = null;
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    voteResult = null;
                }
            }
        });
    }
}
