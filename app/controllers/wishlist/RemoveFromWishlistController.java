package controllers.wishlist;

import com.commercetools.sunrise.framework.components.controllers.PageHeaderControllerComponentSupplier;
import com.commercetools.sunrise.framework.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.framework.controllers.cache.NoCache;
import com.commercetools.sunrise.framework.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.framework.hooks.EnableHooks;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.wishlist.WishlistReverseRouter;
import com.commercetools.sunrise.framework.template.TemplateControllerComponentsSupplier;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.shoppinglist.ShoppingListFinder;
import com.commercetools.sunrise.shoppinglist.ShoppingListInSessionControllerComponent;
import com.commercetools.sunrise.shoppinglist.content.viewmodels.ShoppingListPageContentFactory;
import com.commercetools.sunrise.shoppinglist.remove.RemoveFromShoppingListControllerAction;
import com.commercetools.sunrise.shoppinglist.remove.RemoveFromShoppingListFormData;
import com.commercetools.sunrise.shoppinglist.remove.SunriseRemoveFromShoppingListController;
import io.sphere.sdk.shoppinglists.ShoppingList;
import play.data.FormFactory;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@LogMetrics
@NoCache
@RegisteredComponents({
        TemplateControllerComponentsSupplier.class,
        PageHeaderControllerComponentSupplier.class,
        ShoppingListInSessionControllerComponent.class
})
public final class RemoveFromWishlistController extends SunriseRemoveFromShoppingListController implements WishlistTypeIdentifier {

    private final WishlistReverseRouter reverseRouter;

    @Inject
    public RemoveFromWishlistController(final ContentRenderer contentRenderer,
                                        final FormFactory formFactory,
                                        final ShoppingListPageContentFactory shoppingListPageContentFactory,
                                        final RemoveFromShoppingListFormData formData,
                                        final ShoppingListFinder shoppingListFinder,
                                        final RemoveFromShoppingListControllerAction controllerAction,
                                        final WishlistReverseRouter reverseRouter) {
        super(contentRenderer, formFactory, shoppingListPageContentFactory, formData, shoppingListFinder, controllerAction);
        this.reverseRouter = reverseRouter;
    }


    @EnableHooks
    @SunriseRoute(WishlistReverseRouter.REMOVE_FROM_WISHLIST_PROCESS)
    public CompletionStage<Result> process(final String languageTag) {
        return requireShoppingList(this::processForm, getShoppingListType());
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
    public CompletionStage<Result> handleSuccessfulAction(final ShoppingList wishlist, final RemoveFromShoppingListFormData removeFromShoppingListFormData) {
        return redirectToCall(reverseRouter.wishlistPageCall());
    }

    @Override
    public CompletionStage<Result> handleNotFoundShoppingList() {
        return redirectToCall(reverseRouter.wishlistPageCall());
    }
}
