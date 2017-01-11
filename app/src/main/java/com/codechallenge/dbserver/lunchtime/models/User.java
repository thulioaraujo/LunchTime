package com.codechallenge.dbserver.lunchtime.models;

/**
 * Created by thulioaraujo on 1/10/2017.
 */
public class User {

    private String userName;
    private String userLogin;
    private String userPassword;
    private byte[] userAvatar;

    public User(){}

    public User(String userName, String userLogin, String userPassword, byte[] userAvatar) {
        super();
        this.userName = userName;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userAvatar = userAvatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public byte[] getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(byte[] userAvatar) {
        this.userAvatar = userAvatar;
    }
}