package com.commercetools.sunrise.myaccount.addressbook.addressbookdetail;


import com.commercetools.sunrise.core.viewmodels.content.PageContent;
import com.commercetools.sunrise.core.controllers.SunriseContentController;
import com.commercetools.sunrise.core.controllers.WithQueryFlow;
import com.commercetools.sunrise.core.hooks.EnableHooks;
import com.commercetools.sunrise.core.reverserouters.SunriseRoute;
import com.commercetools.sunrise.core.reverserouters.myaccount.addressbook.AddressBookReverseRouter;
import com.commercetools.sunrise.core.renderers.ContentRenderer;
import com.commercetools.sunrise.models.customers.CustomerFetcher;
import com.commercetools.sunrise.myaccount.MyAccountController;
import com.commercetools.sunrise.myaccount.WithRequiredCustomer;
import com.commercetools.sunrise.myaccount.addressbook.addressbookdetail.viewmodels.AddressBookPageContentFactory;
import io.sphere.sdk.customers.Customer;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

public abstract class SunriseAddressBookDetailController extends SunriseContentController
        implements MyAccountController, WithQueryFlow<Customer>, WithRequiredCustomer {

    private final CustomerFetcher customerFinder;
    private final AddressBookPageContentFactory addressBookPageContentFactory;

    protected SunriseAddressBookDetailController(final ContentRenderer contentRenderer, final CustomerFetcher customerFinder,
                                                 final AddressBookPageContentFactory addressBookPageContentFactory) {
        super(contentRenderer);
        this.customerFinder = customerFinder;
        this.addressBookPageContentFactory = addressBookPageContentFactory;
    }

    @Override
    public final CustomerFetcher getCustomerFinder() {
        return customerFinder;
    }

    @EnableHooks
    @SunriseRoute(AddressBookReverseRouter.ADDRESS_BOOK_DETAIL_PAGE)
    public CompletionStage<Result> show() {
        return requireCustomer(this::showPage);
    }

    @Override
    public PageContent createPageContent(final Customer customer) {
        return addressBookPageContentFactory.create(customer);
    }
}