package com.dbserver.lunchtime.model;

import java.io.Serializable;
import java.util.Date;

/**
 * POJO class to represent a customer object
 *
 * @author Thúlio Araújo (thuliolins@gmail.com)
 * @since 2/13/2017
 */
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer customerId;
    private String customerName;
    private String customerEmail;
    private String customerPassword;
    private Date customerRegisterDt;
    private byte[] customerAvatar;

    public Customer(String customerName, String customerEmail, String customerPassword) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
    }

    public Customer(Integer customerId, String customerName, String customerEmail, String customerPassword, Date customerRegisterDt) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
        this.customerRegisterDt = customerRegisterDt;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public Date getCustomerRegisterDt() {
        return customerRegisterDt;
    }

    public void setCustomerRegisterDt(Date customerRegisterDt) {
        this.customerRegisterDt = customerRegisterDt;
    }

    public byte[] getCustomerAvatar() {
        return customerAvatar;
    }

    public void setCustomerAvatar(byte[] customerAvatar) {
        this.customerAvatar = customerAvatar;
    }
}
