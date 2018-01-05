package controllers.myaccount;

import com.commercetools.sunrise.core.controllers.cache.NoCache;
import com.commercetools.sunrise.core.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.core.renderers.ContentRenderer;
import com.commercetools.sunrise.core.reverserouters.myaccount.mydetails.MyPersonalDetailsReverseRouter;
import com.commercetools.sunrise.myaccount.authentication.signup.SignUpControllerAction;
import com.commercetools.sunrise.models.customers.SignUpFormData;
import com.commercetools.sunrise.myaccount.authentication.signup.SunriseSignUpController;
import com.commercetools.sunrise.myaccount.authentication.signup.viewmodels.SignUpPageContentFactory;
import io.sphere.sdk.customers.CustomerSignInResult;
import play.data.FormFactory;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@LogMetrics
@NoCache
public final class SignUpController extends SunriseSignUpController {

    private final MyPersonalDetailsReverseRouter myPersonalDetailsReverseRouter;

    @Inject
    public SignUpController(final ContentRenderer contentRenderer,
                            final FormFactory formFactory,
                            final SignUpFormData formData,
                            final SignUpControllerAction controllerAction,
                            final SignUpPageContentFactory pageContentFactory,
                            final MyPersonalDetailsReverseRouter myPersonalDetailsReverseRouter) {
        super(contentRenderer, formFactory, formData, controllerAction, pageContentFactory);
        this.myPersonalDetailsReverseRouter = myPersonalDetailsReverseRouter;
    }

    @Override
    public String getTemplateName() {
        return "my-account-login";
    }

    @Override
    public String getCmsPageKey() {
        return "default";
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final CustomerSignInResult result, final SignUpFormData formData) {
        return redirectToCall(myPersonalDetailsReverseRouter.myPersonalDetailsPageCall());
    }
}
