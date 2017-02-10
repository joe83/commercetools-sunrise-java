package com.commercetools.sunrise.common.reverserouter;

import com.google.inject.ImplementedBy;
import play.mvc.Call;

@ImplementedBy(ReflectionMyPersonalDetailsLocalizedReverseRouter.class)
public interface MyPersonalDetailsLocalizedReverseRouter extends MyPersonalDetailsReverseRouter, LocalizedReverseRouter {

    default Call myPersonalDetailsPageCall() {
        return myPersonalDetailsPageCall(languageTag());
    }

    default Call myPersonalDetailsProcessFormCall() {
        return myPersonalDetailsProcessFormCall(languageTag());
    }
}