package controllers.shoppingcart;

import com.commercetools.sunrise.core.controllers.cache.NoCache;
import com.commercetools.sunrise.core.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.core.renderers.ContentRenderer;
import com.commercetools.sunrise.core.reverserouters.shoppingcart.cart.CartReverseRouter;
import com.commercetools.sunrise.models.carts.CartCreator;
import com.commercetools.sunrise.models.carts.CartFetcher;
import com.commercetools.sunrise.shoppingcart.add.AddToCartControllerAction;
import com.commercetools.sunrise.shoppingcart.add.AddToCartFormData;
import com.commercetools.sunrise.shoppingcart.add.SunriseAddToCartController;
import com.commercetools.sunrise.shoppingcart.content.viewmodels.CartPageContentFactory;
import io.sphere.sdk.carts.Cart;
import play.data.FormFactory;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@LogMetrics
@NoCache
public final class AddToCartController extends SunriseAddToCartController {

    private final CartReverseRouter cartReverseRouter;

    @Inject
    public AddToCartController(final ContentRenderer contentRenderer,
                               final FormFactory formFactory,
                               final AddToCartFormData formData,
                               final CartFetcher cartFetcher,
                               final CartCreator cartCreator,
                               final AddToCartControllerAction controllerAction,
                               final CartPageContentFactory pageContentFactory,
                               final CartReverseRouter cartReverseRouter) {
        super(contentRenderer, formFactory, formData, cartFetcher, cartCreator, controllerAction, pageContentFactory);
        this.cartReverseRouter = cartReverseRouter;
    }

    @Override
    public String getTemplateName() {
        return "cart";
    }

    @Override
    public String getCmsPageKey() {
        return "default";
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final Cart updatedCart, final AddToCartFormData formData) {
        return redirectToCall(cartReverseRouter.cartDetailPageCall());
    }
}
