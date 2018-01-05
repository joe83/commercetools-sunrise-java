package controllers.myaccount;

import com.commercetools.sunrise.core.controllers.cache.NoCache;
import com.commercetools.sunrise.core.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.core.renderers.ContentRenderer;
import com.commercetools.sunrise.core.reverserouters.myaccount.authentication.AuthenticationReverseRouter;
import com.commercetools.sunrise.core.reverserouters.myaccount.myorders.MyOrdersReverseRouter;
import com.commercetools.sunrise.models.customers.CustomerFetcher;
import com.commercetools.sunrise.models.orders.MyOrderFetcher;
import com.commercetools.sunrise.myaccount.myorders.myorderdetail.SunriseMyOrderDetailController;
import com.commercetools.sunrise.myaccount.myorders.myorderdetail.viewmodels.MyOrderDetailPageContentFactory;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@LogMetrics
@NoCache
public final class MyOrderDetailController extends SunriseMyOrderDetailController {

    private final MyOrdersReverseRouter myOrdersReverseRouter;
    private final AuthenticationReverseRouter authenticationReverseRouter;

    @Inject
    public MyOrderDetailController(final ContentRenderer contentRenderer,
                                   final CustomerFetcher customerFinder,
                                   final MyOrderFetcher myOrderFinder,
                                   final MyOrderDetailPageContentFactory pageContentFactory,
                                   final MyOrdersReverseRouter myOrdersReverseRouter,
                                   final AuthenticationReverseRouter authenticationReverseRouter) {
        super(contentRenderer, customerFinder, myOrderFinder, pageContentFactory);
        this.myOrdersReverseRouter = myOrdersReverseRouter;
        this.authenticationReverseRouter = authenticationReverseRouter;
    }

    @Override
    public String getTemplateName() {
        return "my-account-my-orders-order";
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
    public CompletionStage<Result> handleNotFoundMyOrder() {
        return redirectToCall(myOrdersReverseRouter.myOrderListPageCall());
    }
}
