package com.dbserver.lunchtime.model;

import java.io.Serializable;
import java.util.Date;

/**
 * POJO class to represent a vote object
 *
 * @author Thúlio Araújo (thuliolins@gmail.com)
 * @since 2/13/2017
 */
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;
    private Restaurant restaurant;
    private Customer customer;
    private Date voteDay;

    public Vote(Restaurant restaurant, Customer customer, Date voteDay) {
        this.restaurant = restaurant;
        this.customer = customer;
        this.voteDay = voteDay;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getVoteDay() {
        return voteDay;
    }

    public void setVoteDay(Date voteDay) {
        this.voteDay = voteDay;
    }
}
