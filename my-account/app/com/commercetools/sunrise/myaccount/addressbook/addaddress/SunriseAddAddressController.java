package com.commercetools.sunrise.myaccount.addressbook.addaddress;

import com.commercetools.sunrise.core.viewmodels.content.PageContent;
import com.commercetools.sunrise.core.controllers.SunriseContentFormController;
import com.commercetools.sunrise.core.controllers.WithContentFormFlow;
import com.commercetools.sunrise.core.hooks.EnableHooks;
import com.commercetools.sunrise.core.reverserouters.SunriseRoute;
import com.commercetools.sunrise.core.reverserouters.myaccount.addressbook.AddressBookReverseRouter;
import com.commercetools.sunrise.core.renderers.ContentRenderer;
import com.commercetools.sunrise.models.customers.CustomerFetcher;
import com.commercetools.sunrise.myaccount.MyAccountController;
import com.commercetools.sunrise.myaccount.WithRequiredCustomer;
import com.commercetools.sunrise.models.addresses.AddressFormData;
import com.commercetools.sunrise.myaccount.addressbook.addaddress.viewmodels.AddAddressPageContentFactory;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Address;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

public abstract class SunriseAddAddressController extends SunriseContentFormController
        implements MyAccountController, WithContentFormFlow<Customer, Customer, AddressFormData>, WithRequiredCustomer {

    private final AddressFormData formData;
    private final CustomerFetcher customerFinder;
    private final AddAddressControllerAction controllerAction;
    private final AddAddressPageContentFactory pageContentFactory;
    private final CountryCode country;

    protected SunriseAddAddressController(final ContentRenderer contentRenderer, final FormFactory formFactory,
                                          final AddressFormData formData, final CustomerFetcher customerFinder,
                                          final AddAddressControllerAction controllerAction,
                                          final AddAddressPageContentFactory pageContentFactory,
                                          final CountryCode country) {
        super(contentRenderer, formFactory);
        this.formData = formData;
        this.customerFinder = customerFinder;
        this.controllerAction = controllerAction;
        this.pageContentFactory = pageContentFactory;
        this.country = country;
    }

    @Override
    public final Class<? extends AddressFormData> getFormDataClass() {
        return formData.getClass();
    }

    @Override
    public final CustomerFetcher getCustomerFinder() {
        return customerFinder;
    }

    @EnableHooks
    @SunriseRoute(AddressBookReverseRouter.ADD_ADDRESS_PAGE)
    public CompletionStage<Result> show() {
        return requireCustomer(customer -> showFormPage(customer, formData));
    }

    @EnableHooks
    @SunriseRoute(AddressBookReverseRouter.ADD_ADDRESS_PROCESS)
    public CompletionStage<Result> process() {
        return requireCustomer(this::processForm);
    }

    @Override
    public CompletionStage<Customer> executeAction(final Customer customer, final AddressFormData formData) {
        return controllerAction.apply(customer, formData);
    }

    @Override
    public abstract CompletionStage<Result> handleSuccessfulAction(final Customer updatedCustomer, final AddressFormData formData);

    @Override
    public PageContent createPageContent(final Customer customer, final Form<? extends AddressFormData> form) {
        return pageContentFactory.create(customer, form);
    }

    @Override
    public void preFillFormData(final Customer customer, final AddressFormData formData) {
        final Address address = Address.of(country)
                .withTitle(customer.getTitle())
                .withFirstName(customer.getFirstName())
                .withLastName(customer.getLastName())
                .withEmail(customer.getEmail());
        formData.applyAddress(address);
    }
}
