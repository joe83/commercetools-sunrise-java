package controllers.myaccount;

import com.commercetools.sunrise.core.controllers.cache.NoCache;
import com.commercetools.sunrise.core.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.core.renderers.ContentRenderer;
import com.commercetools.sunrise.core.reverserouters.myaccount.addressbook.AddressBookReverseRouter;
import com.commercetools.sunrise.core.reverserouters.myaccount.authentication.AuthenticationReverseRouter;
import com.commercetools.sunrise.models.customers.CustomerFetcher;
import com.commercetools.sunrise.models.addresses.AddressFinder;
import com.commercetools.sunrise.models.addresses.AddressFormData;
import com.commercetools.sunrise.myaccount.addressbook.changeaddress.ChangeAddressControllerAction;
import com.commercetools.sunrise.myaccount.addressbook.changeaddress.SunriseChangeAddressController;
import com.commercetools.sunrise.myaccount.addressbook.changeaddress.viewmodels.ChangeAddressPageContentFactory;
import io.sphere.sdk.customers.Customer;
import play.data.FormFactory;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@LogMetrics
@NoCache
public final class ChangeAddressController extends SunriseChangeAddressController {

    private final AuthenticationReverseRouter authenticationReverseRouter;
    private final AddressBookReverseRouter addressBookReverseRouter;

    @Inject
    public ChangeAddressController(final ContentRenderer contentRenderer,
                                   final FormFactory formFactory,
                                   final AddressFormData formData,
                                   final CustomerFetcher customerFinder,
                                   final AddressFinder addressFinder,
                                   final ChangeAddressControllerAction controllerAction,
                                   final ChangeAddressPageContentFactory pageContentFactory,
                                   final AuthenticationReverseRouter authenticationReverseRouter,
                                   final AddressBookReverseRouter addressBookReverseRouter) {
        super(contentRenderer, formFactory, formData, customerFinder, addressFinder, controllerAction, pageContentFactory);
        this.authenticationReverseRouter = authenticationReverseRouter;
        this.addressBookReverseRouter = addressBookReverseRouter;
    }

    @Override
    public String getTemplateName() {
        return "my-account-edit-address";
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
    public CompletionStage<Result> handleSuccessfulAction(final Customer updatedCustomer, final AddressFormData formData) {
        return redirectToCall(addressBookReverseRouter.addressBookDetailPageCall());
    }
}
