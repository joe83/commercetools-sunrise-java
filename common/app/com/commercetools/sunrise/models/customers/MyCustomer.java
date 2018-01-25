package com.commercetools.sunrise.models.customers;

import com.commercetools.sunrise.core.sessions.UserResourceInCache;
import com.google.inject.ImplementedBy;
import io.sphere.sdk.customers.Customer;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

@ImplementedBy(MyCustomerImpl.class)
public interface MyCustomer extends UserResourceInCache<Customer> {

    @Override
    CompletionStage<Optional<Customer>> get();
}