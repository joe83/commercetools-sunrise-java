package com.commercetools.sunrise.common.controllers;

import com.commercetools.sunrise.common.pages.PageContent;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

/**
 * Approach to handle query data (Template Method Pattern).
 * @param <I> type of the input object, the one queried
 */
public interface WithQueryFlow<I> extends WithTemplate {

    default CompletionStage<Result> showPage(final I input) {
        return okResultWithPageContent(createPageContent(input));
    }

    PageContent createPageContent(final I input);
}