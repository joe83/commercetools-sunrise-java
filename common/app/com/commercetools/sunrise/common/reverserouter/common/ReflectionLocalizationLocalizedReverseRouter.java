package com.commercetools.sunrise.common.reverserouter.common;

import com.commercetools.sunrise.common.injection.RequestScoped;
import com.commercetools.sunrise.common.reverserouter.AbstractLocalizedReverseRouter;
import play.mvc.Call;

import javax.inject.Inject;
import java.util.Locale;

@RequestScoped
final class ReflectionLocalizationLocalizedReverseRouter extends AbstractLocalizedReverseRouter implements LocalizationReverseRouter {

    private final LocalizationSimpleReverseRouter delegate;

    @Inject
    private ReflectionLocalizationLocalizedReverseRouter(final Locale locale, final LocalizationSimpleReverseRouter reverseRouter) {
        super(locale);
        this.delegate = reverseRouter;
    }

    @Override
    public Call processChangeLanguageForm() {
        return delegate.processChangeLanguageForm();
    }

    @Override
    public Call processChangeCountryForm(final String languageTag) {
        return delegate.processChangeCountryForm(languageTag);
    }
}