package com.commercetools.sunrise.common.search.facetedsearch;

import com.commercetools.sunrise.common.models.ViewModel;

import java.util.List;

public class FacetSelectorListBean extends ViewModel {

    private List<FacetSelectorBean> list;

    public FacetSelectorListBean() {
    }

    public List<FacetSelectorBean> getList() {
        return list;
    }

    public void setList(final List<FacetSelectorBean> list) {
        this.list = list;
    }
}