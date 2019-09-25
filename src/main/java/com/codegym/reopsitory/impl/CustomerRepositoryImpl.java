package com.codegym.reopsitory.impl;

import com.codegym.model.Customer;
import com.codegym.reopsitory.CustomerRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerRepositoryImpl implements CustomerRepository {

    private  static Map<Integer, Customer> customers;

    static {
        customers = new HashMap<>();
        customers.put(1, new Customer(1, "John","Hanoi","Male","flower1.jpg"));
        customers.put(2, new Customer(2, "Bill", "Danang", "Male","flower2.jpg"));
        customers.put(3, new Customer(3, "Alex",  "Saigon","Female","flower3.jpg"));
        customers.put(4, new Customer(4, "Adam", "Beijin","Male","flower4.jpg"));
        customers.put(5, new Customer(5, "Sophia", "Miami","Female","flower5.jpg"));
        customers.put(6, new Customer(6, "Rose", "Newyork","Female","flower6.jpg"));
    }

    @Override
    public List<Customer> findByAll() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Customer findById(int id) {
        return customers.get(id);
    }

    @Override
    public List<Customer> findByName(String name) {
        List<Customer> customerList= findByAll();
        List<Customer> customerSearchResponse = new ArrayList<>();
        String searchNameToLowerCase= name.toLowerCase().trim();
        for(Customer customer:customerList){
            String customerNameToLowerCase=customer.getName().toLowerCase().trim();
            if(customerNameToLowerCase.contains(searchNameToLowerCase)){
                customerSearchResponse.add(customer);
            }
        }
        return customerSearchResponse;
    }

    @Override
    public void save(Customer customer) {
    customers.put(customer.getId(),customer);
    }

    @Override
    public void update(int id, Customer customer) {
    customers.put(id,customer);
    }

    @Override
    public void remove(int id) {
    customers.remove(id);
    }


}
