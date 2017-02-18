package com.commercetools.sunrise.common.sessions.customer;

import com.commercetools.sunrise.common.injection.RequestScoped;
import com.commercetools.sunrise.common.sessions.DataFromResourceStoringOperations;
import com.commercetools.sunrise.common.sessions.ObjectStoringSessionStrategy;
import io.sphere.sdk.customers.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Configuration;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Keeps some parts from the customer in session, such as customer ID, email and some general info.
 */
@RequestScoped
public class DefaultCustomerInSession extends DataFromResourceStoringOperations<Customer> implements CustomerInSession {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerInSession.class);
    private static final String DEFAULT_CUSTOMER_ID_SESSION_KEY = "sunrise-customer-id";
    private static final String DEFAULT_CUSTOMER_EMAIL_SESSION_KEY = "sunrise-customer-email";
    private static final String DEFAULT_USER_INFO_SESSION_KEY = "sunrise-user-info";

    private final String customerIdSessionKey;
    private final String customerEmailSessionKey;
    private final String userInfoSessionKey;
    private final ObjectStoringSessionStrategy session;
    private final UserInfoBeanFactory userInfoBeanFactory;

    @Inject
    public DefaultCustomerInSession(final Configuration configuration, final ObjectStoringSessionStrategy session, final UserInfoBeanFactory userInfoBeanFactory) {
        this.customerIdSessionKey = configuration.getString("session.customer.customerId", DEFAULT_CUSTOMER_ID_SESSION_KEY);
        this.customerEmailSessionKey = configuration.getString("session.customer.customerEmail", DEFAULT_CUSTOMER_EMAIL_SESSION_KEY);
        this.userInfoSessionKey = configuration.getString("session.customer.userInfo", DEFAULT_USER_INFO_SESSION_KEY);
        this.session = session;
        this.userInfoBeanFactory = userInfoBeanFactory;
    }

    @Override
    protected final Logger getLogger() {
        return LOGGER;
    }

    protected final ObjectStoringSessionStrategy getSession() {
        return session;
    }

    @Override
    public Optional<String> findCustomerId() {
        return session.findValueByKey(customerIdSessionKey);
    }

    @Override
    public Optional<String> findCustomerEmail() {
        return session.findValueByKey(customerEmailSessionKey);
    }

    @Override
    public Optional<UserInfoBean> findUserInfo() {
        return session.findObjectByKey(userInfoSessionKey, UserInfoBean.class);
    }

    @Override
    public void store(@Nullable final Customer customer) {
        super.store(customer);
    }

    @Override
    public void remove() {
        super.remove();
    }

    @Override
    protected void storeAssociatedData(final Customer customer) {
        session.overwriteObjectByKey(userInfoSessionKey, userInfoBeanFactory.create(customer));
        session.overwriteValueByKey(customerIdSessionKey, customer.getId());
        session.overwriteValueByKey(customerEmailSessionKey, customer.getEmail());
    }

    @Override
    protected void removeAssociatedData() {
        session.removeObjectByKey(userInfoSessionKey);
        session.removeValueByKey(customerIdSessionKey);
        session.removeValueByKey(customerEmailSessionKey);
    }
}