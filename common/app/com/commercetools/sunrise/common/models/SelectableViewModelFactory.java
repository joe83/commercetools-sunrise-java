package com.commercetools.sunrise.common.models;

import javax.annotation.Nullable;

public abstract class SelectableViewModelFactory<T, D, S> {

    protected abstract T getViewModelInstance();

    public T create(final D option, @Nullable final S selectedValue) {
        return initializedViewModel(option, selectedValue);
    }

    protected abstract void initialize(final T viewModel, final D option, @Nullable final S selectedValue);

    protected final T initializedViewModel(final D option, @Nullable final S selectedValue) {
        final T viewModel = getViewModelInstance();
        initialize(viewModel, option, selectedValue);
        return viewModel;
    }
}