package com.commercetools.sunrise.common.reverserouter.productcatalog;

import com.google.inject.ImplementedBy;
import play.mvc.Call;

@ImplementedBy(ReflectionHomeReverseRouter.class)
interface HomeSimpleReverseRouter {

    Call homePageCall(final String languageTag);

}