package com.codegym.service;

import com.codegym.model.Customer;

import java.util.List;

public interface GeneralService<E> {
    List<E> findByAll();

    E findById(int id);

    List<E>findByName(String name);

    void save(E e);

    void update(int id, Customer customer);

    void remove(int id);

}
