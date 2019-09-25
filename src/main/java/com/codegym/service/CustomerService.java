package com.codegym.service;

import com.codegym.model.Customer;
import com.codegym.model.CustomerForm;
import org.springframework.validation.BindingResult;

public interface CustomerService extends GeneralService<Customer> {
    String uploadFile(CustomerForm customerForm, BindingResult result);
}
