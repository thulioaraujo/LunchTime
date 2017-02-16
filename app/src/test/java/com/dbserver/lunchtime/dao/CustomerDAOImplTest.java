package com.dbserver.lunchtime.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dbserver.lunchtime.BuildConfig;
import com.dbserver.lunchtime.data.LunchTimeDbHelper;
import com.dbserver.lunchtime.model.Customer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class tests CustomerDAOImpl functions
 *
 * @author Thúlio Araújo (thuliolins@gmail.com)
 * @since 2/16/2017
 */
@RunWith(MockitoJUnitRunner.class)
@Config(constants = BuildConfig.class, sdk = 16, manifest = "/src/main/AndroidManifest.xml")
public class CustomerDAOImplTest {

    private CustomerDAOImpl customerDAO;
    private CustomerDAOImpl customerDAOMock;
    private SQLiteDatabase dbMock;
    private Customer customer;

    @Before
    public void setup() {
        Context context = RuntimeEnvironment.application;
        customerDAO = new CustomerDAOImpl(context);
        customerDAOMock = new CustomerDAOImpl(context, setupHelperMock());
    }

    /**
     * Setup a DBSchema mock
     *
     * @return Returns the mocked obj
     */
    private LunchTimeDbHelper setupHelperMock() {
        // create the mocks
        LunchTimeDbHelper helperM = mock(LunchTimeDbHelper.class);
        dbMock = mock(SQLiteDatabase.class);

        // Define method's results for the mock obj
        when(helperM.getReadableDatabase()).thenReturn(dbMock);
        when(helperM.getWritableDatabase()).thenReturn(dbMock);
        return helperM;
    }

    @Test
    public void shouldCreateCustomer() {
        Customer customer = new Customer("Thulio", "thuliolins@gmail.com", "123456");
        boolean customerFromDb = customerDAOMock.createCustomer(customer);
        assertTrue(customerFromDb);
    }
}