package com.dbserver.lunchtime.dao;

import com.dbserver.lunchtime.model.Customer;

/**
 * Represents the Customer CRUD
 *
 * @author Thúlio Araújo (thuliolins@gmail.com)
 * @since 2/13/2017
 */
public interface CustomerDAO {

    /**
     * Local customer login request
     *
     * @param customerEmail
     * @param customerPassword
     * @return true otherwise false
     */
    public Customer customerLogin(final String customerEmail, final String customerPassword);

    /**
     * Creates a customer and store it on the local database
     *
     * @param customer
     */
    public boolean createCustomer(final Customer customer);

    /**
     * Get the customer from local database by its Login
     *
     * @param customerEmail
     * @return Customer
     */
    public Customer getCustomerByEmail(final String customerEmail);

    /**
     * Delete customer by email
     *
     * @param customerEmail
     * @return Customer
     */
    public int deleteCustomerByEmail(final String customerEmail);
}
