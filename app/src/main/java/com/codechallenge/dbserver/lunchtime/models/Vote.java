package com.codechallenge.dbserver.lunchtime.models;

/**
 * Created by thulioaraujo on 1/12/2017.
 */

public class Vote {
    private String userUserName;
    private String restaurantName;

    public Vote(){}

    public Vote(String userUserName, String restaurantName) {
        this.userUserName = userUserName;
        this.restaurantName = restaurantName;
    }

    public String getUserUserName() {
        return userUserName;
    }

    public void setUserUserName(String userUserName) {
        this.userUserName = userUserName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
