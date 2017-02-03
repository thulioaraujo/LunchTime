package com.codechallenge.dbserver.lunchtime.presenter;

import com.codechallenge.dbserver.lunchtime.models.User;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by thulioaraujo on 1/13/2017.
 */
public class LoginPresenterTest {

    LoginPresenter loginPresenter;
    User user;

    @Before
    public void setup() {
        loginPresenter = LoginPresenter.getInstance();
        this.user = new User("Thulio", "thuliolins@gmail.com", "123456");
    }

    @Test
    public void checkIfEmailAndPasswordIsNotEmptyAndNotNull() {
        Assert.assertFalse(loginPresenter.validadeEmptyFields(user.getUserEmail(), user.getUserPassword()));
        Assert.assertNotNull(loginPresenter.validadeEmptyFields(null, null));
    }
}