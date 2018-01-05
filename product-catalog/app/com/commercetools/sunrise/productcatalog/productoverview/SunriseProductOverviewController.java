package com.commercetools.sunrise.productcatalog.productoverview;

import com.commercetools.sunrise.core.controllers.SunriseContentController;
import com.commercetools.sunrise.core.controllers.WithQueryFlow;
import com.commercetools.sunrise.core.hooks.EnableHooks;
import com.commercetools.sunrise.core.reverserouters.SunriseRoute;
import com.commercetools.sunrise.core.reverserouters.productcatalog.product.ProductReverseRouter;
import com.commercetools.sunrise.core.renderers.ContentRenderer;
import com.commercetools.sunrise.core.viewmodels.content.PageContent;
import com.commercetools.sunrise.models.products.ProductListFetcher;
import com.commercetools.sunrise.productcatalog.productoverview.viewmodels.ProductOverviewPageContentFactory;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.PagedSearchResult;
import play.libs.concurrent.HttpExecution;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

/**
 * Provides facilities display products by category.
 */
public abstract class SunriseProductOverviewController extends SunriseContentController implements WithQueryFlow<ProductsWithCategory>, WithRequiredCategory {

    private final CategoryFinder categoryFinder;
    private final ProductListFetcher productListFinder;
    private final ProductOverviewPageContentFactory productOverviewPageContentFactory;

    protected SunriseProductOverviewController(final ContentRenderer contentRenderer,
                                               final CategoryFinder categoryFinder, final ProductListFetcher productListFinder,
                                               final ProductOverviewPageContentFactory productOverviewPageContentFactory) {
        super(contentRenderer);
        this.categoryFinder = categoryFinder;
        this.productListFinder = productListFinder;
        this.productOverviewPageContentFactory = productOverviewPageContentFactory;
    }

    @Override
    public final CategoryFinder getCategoryFinder() {
        return categoryFinder;
    }

    @EnableHooks
    @SunriseRoute(ProductReverseRouter.PRODUCT_OVERVIEW_PAGE)
    public CompletionStage<Result> show(final String categoryIdentifier) {
        return requireCategory(categoryIdentifier, category ->
                findProducts(category, products ->
                        showPage(ProductsWithCategory.of(products, category))));
    }

    protected final CompletionStage<Result> findProducts(final Category category, final Function<PagedSearchResult<ProductProjection>, CompletionStage<Result>> nextAction) {
        return productListFinder.apply(category)
                .thenComposeAsync(nextAction, HttpExecution.defaultContext());
    }

    @Override
    public PageContent createPageContent(final ProductsWithCategory productsWithCategory) {
        return productOverviewPageContentFactory.create(productsWithCategory);
    }
}