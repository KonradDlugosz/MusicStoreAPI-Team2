package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Customer;
import com.sparta.musicstoreapi.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PreUpdate;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(value = "/chinook/allcustomer")
    public List<Customer> findAllCustomer(){
        return customerRepository.findAll();
    }

    @GetMapping(value = "/chinook/customer/{id}")
    public Customer findCustomerById(@PathVariable Integer id){
        Optional<Customer> result =  customerRepository.findById(id);
        if(result.isEmpty()) return null;
        return result.get();
    }

    @PostMapping(value = "/chinook/customer/add")
    public Customer addCustomer(@RequestBody Customer newCustomer){
        return customerRepository.save(newCustomer);
    }
}
