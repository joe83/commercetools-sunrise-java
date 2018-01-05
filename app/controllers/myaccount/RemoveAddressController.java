package controllers.myaccount;

import com.commercetools.sunrise.core.controllers.cache.NoCache;
import com.commercetools.sunrise.core.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.core.renderers.ContentRenderer;
import com.commercetools.sunrise.core.reverserouters.myaccount.addressbook.AddressBookReverseRouter;
import com.commercetools.sunrise.core.reverserouters.myaccount.authentication.AuthenticationReverseRouter;
import com.commercetools.sunrise.models.customers.CustomerFetcher;
import com.commercetools.sunrise.models.addresses.AddressFinder;
import com.commercetools.sunrise.myaccount.addressbook.addressbookdetail.viewmodels.AddressBookPageContentFactory;
import com.commercetools.sunrise.myaccount.addressbook.removeaddress.RemoveAddressControllerAction;
import com.commercetools.sunrise.myaccount.addressbook.removeaddress.RemoveAddressFormData;
import com.commercetools.sunrise.myaccount.addressbook.removeaddress.SunriseRemoveAddressController;
import io.sphere.sdk.customers.Customer;
import play.data.FormFactory;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@LogMetrics
@NoCache
public final class RemoveAddressController extends SunriseRemoveAddressController {

    private final AuthenticationReverseRouter authenticationReverseRouter;
    private final AddressBookReverseRouter addressBookReverseRouter;

    @Inject
    public RemoveAddressController(final ContentRenderer contentRenderer,
                                   final FormFactory formFactory,
                                   final RemoveAddressFormData formData,
                                   final CustomerFetcher customerFinder,
                                   final AddressFinder addressFinder,
                                   final RemoveAddressControllerAction controllerAction,
                                   final AddressBookPageContentFactory pageContentFactory,
                                   final AuthenticationReverseRouter authenticationReverseRouter,
                                   final AddressBookReverseRouter addressBookReverseRouter) {
        super(contentRenderer, formFactory, formData, customerFinder, addressFinder, controllerAction, pageContentFactory);
        this.authenticationReverseRouter = authenticationReverseRouter;
        this.addressBookReverseRouter = addressBookReverseRouter;
    }

    @Override
    public String getTemplateName() {
        return "my-account-address-book";
    }

    @Override
    public String getCmsPageKey() {
        return "default";
    }

    @Override
    public CompletionStage<Result> handleNotFoundCustomer() {
        return redirectToCall(authenticationReverseRouter.logInPageCall());
    }

    @Override
    public CompletionStage<Result> handleNotFoundAddress() {
        return redirectToCall(addressBookReverseRouter.addressBookDetailPageCall());
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final Customer updatedCustomer, final RemoveAddressFormData formData) {
        return redirectToCall(addressBookReverseRouter.addressBookDetailPageCall());
    }
}
