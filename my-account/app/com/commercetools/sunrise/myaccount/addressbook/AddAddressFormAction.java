package com.commercetools.sunrise.myaccount.addressbook;

import com.commercetools.sunrise.core.FormAction;
import com.google.inject.ImplementedBy;

@ImplementedBy(DefaultAddAddressFormAction.class)
public interface AddAddressFormAction extends FormAction<AddAddressFormData> {

}