package com.commercetools.sunrise.shoppingcart.checkout.address;

import com.commercetools.sunrise.core.renderers.ContentRenderer;
import com.commercetools.sunrise.core.viewmodels.content.PageContent;
import com.commercetools.sunrise.models.carts.CartFetcher;
import com.commercetools.sunrise.shoppingcart.WithRequiredCart;
import com.commercetools.sunrise.shoppingcart.checkout.address.viewmodels.CheckoutAddressPageContentFactory;
import com.commercetools.sunrise.core.controllers.SunriseContentFormController;
import com.commercetools.sunrise.core.controllers.WithContentFormFlow;
import com.commercetools.sunrise.core.hooks.EnableHooks;
import com.commercetools.sunrise.core.reverserouters.SunriseRoute;
import com.commercetools.sunrise.core.reverserouters.shoppingcart.checkout.CheckoutReverseRouter;
import io.sphere.sdk.carts.Cart;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

public abstract class SunriseCheckoutAddressController extends SunriseContentFormController
        implements WithContentFormFlow<Cart, Cart, CheckoutAddressFormData>, WithRequiredCart {

    private final CheckoutAddressFormData formData;
    private final CartFetcher cartFetcher;
    private final CheckoutAddressControllerAction controllerAction;
    private final CheckoutAddressPageContentFactory pageContentFactory;

    protected SunriseCheckoutAddressController(final ContentRenderer contentRenderer,
                                               final FormFactory formFactory, final CheckoutAddressFormData formData,
                                               final CartFetcher cartFetcher,
                                               final CheckoutAddressControllerAction controllerAction,
                                               final CheckoutAddressPageContentFactory pageContentFactory) {
        super(contentRenderer, formFactory);
        this.formData = formData;
        this.cartFetcher = cartFetcher;
        this.controllerAction = controllerAction;
        this.pageContentFactory = pageContentFactory;
    }

    @Override
    public final Class<? extends CheckoutAddressFormData> getFormDataClass() {
        return formData.getClass();
    }

    @Override
    public final CartFetcher getCartFetcher() {
        return cartFetcher;
    }

    @EnableHooks
    @SunriseRoute(CheckoutReverseRouter.CHECKOUT_ADDRESS_PAGE)
    public CompletionStage<Result> show() {
        return requireNonEmptyCart(cart -> showFormPage(cart, formData));
    }

    @EnableHooks
    @SunriseRoute(CheckoutReverseRouter.CHECKOUT_ADDRESS_PROCESS)
    public CompletionStage<Result> process() {
        return requireNonEmptyCart(this::processForm);
    }

    @Override
    public CompletionStage<Cart> executeAction(final Cart cart, final CheckoutAddressFormData formData) {
        return controllerAction.apply(cart, formData);
    }

    @Override
    public abstract CompletionStage<Result> handleSuccessfulAction(final Cart updatedCart, final CheckoutAddressFormData formData);

    @Override
    public PageContent createPageContent(final Cart cart, final Form<? extends CheckoutAddressFormData> form) {
        return pageContentFactory.create(cart, form);
    }

    @Override
    public Form<? extends CheckoutAddressFormData> createForm() {
        if (isBillingAddressDifferent()) {
            return getFormFactory().form(getFormDataClass(), BillingAddressDifferentToShippingAddressGroup.class);
        }
        return WithContentFormFlow.super.createForm();
    }

    protected boolean isBillingAddressDifferent() {
        final String flagFieldName = "billingAddressDifferentToBillingAddress";
        final String fieldValue = getFormFactory().form().bindFromRequest().get(flagFieldName);
        return "true".equals(fieldValue);
    }

    @Override
    public void preFillFormData(final Cart cart, final CheckoutAddressFormData formData) {
        formData.applyCart(cart);
    }
}