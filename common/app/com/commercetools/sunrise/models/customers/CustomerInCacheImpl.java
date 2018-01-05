package com.commercetools.sunrise.models.customers;

import com.commercetools.sunrise.core.injection.RequestScoped;
import com.commercetools.sunrise.core.sessions.AbstractResourceInCache;
import io.sphere.sdk.customers.Customer;
import play.cache.CacheApi;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

@RequestScoped
final class CustomerInCacheImpl extends AbstractResourceInCache<Customer> implements CustomerInCache {

    private final CustomerFetcher customerFinder;

    @Inject
    CustomerInCacheImpl(final CustomerInSession customerInSession, final CacheApi cacheApi, final CustomerFetcher customerFinder) {
        super(customerInSession, cacheApi);
        this.customerFinder = customerFinder;
    }

    @Override
    protected CompletionStage<Optional<Customer>> fetchResource() {
        return customerFinder.get();
    }

    @Override
    protected String generateCacheKey(final String customerId) {
        return "customer_" + customerId;
    }
}
