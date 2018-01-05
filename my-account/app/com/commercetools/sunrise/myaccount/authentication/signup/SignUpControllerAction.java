package com.commercetools.sunrise.myaccount.authentication.signup;

import com.commercetools.sunrise.core.controllers.ControllerAction;
import com.commercetools.sunrise.models.customers.SignUpFormData;
import com.google.inject.ImplementedBy;
import io.sphere.sdk.customers.CustomerSignInResult;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@ImplementedBy(DefaultSignUpControllerAction.class)
@FunctionalInterface
public interface SignUpControllerAction extends ControllerAction, Function<SignUpFormData, CompletionStage<CustomerSignInResult>> {

    @Override
    CompletionStage<CustomerSignInResult> apply(final SignUpFormData formData);
}
