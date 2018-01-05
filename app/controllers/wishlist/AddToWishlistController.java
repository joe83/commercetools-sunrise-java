package controllers.wishlist;

import com.commercetools.sunrise.core.controllers.cache.NoCache;
import com.commercetools.sunrise.core.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.core.renderers.ContentRenderer;
import com.commercetools.sunrise.core.reverserouters.wishlist.WishlistReverseRouter;
import com.commercetools.sunrise.models.shoppinglists.WishlistCreator;
import com.commercetools.sunrise.models.shoppinglists.WishlistFetcher;
import com.commercetools.sunrise.wishlist.add.AddToWishlistControllerAction;
import com.commercetools.sunrise.wishlist.add.AddToWishlistFormData;
import com.commercetools.sunrise.wishlist.add.SunriseAddToWishlistController;
import com.commercetools.sunrise.wishlist.content.viewmodels.WishlistPageContentFactory;
import io.sphere.sdk.shoppinglists.ShoppingList;
import play.data.FormFactory;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@LogMetrics
@NoCache
public final class AddToWishlistController extends SunriseAddToWishlistController {

    private final WishlistReverseRouter reverseRouter;

    @Inject
    public AddToWishlistController(final ContentRenderer contentRenderer, final FormFactory formFactory,
                                   final WishlistPageContentFactory wishlistPageContentFactory,
                                   final AddToWishlistFormData formData, final WishlistCreator wishlistCreator, final WishlistFetcher wishlistFinder,
                                   final AddToWishlistControllerAction controllerAction, final WishlistReverseRouter reverseRouter) {
        super(contentRenderer, formFactory, wishlistPageContentFactory, formData, wishlistCreator, wishlistFinder, controllerAction);
        this.reverseRouter = reverseRouter;
    }

    @Override
    public String getTemplateName() {
        return "my-account-wishlist";
    }

    @Override
    public String getCmsPageKey() {
        return "default";
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final ShoppingList wishlist, final AddToWishlistFormData addToWishlistFormData) {
        return redirectToCall(reverseRouter.wishlistPageCall());
    }
}
