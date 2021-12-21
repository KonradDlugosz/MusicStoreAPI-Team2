package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Customer;
import com.sparta.musicstoreapi.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * Get all customers
     * @return all customers
     */
    @GetMapping(value = "/chinook/allcustomer")
    public List<Customer> findAllCustomer(){
        return customerRepository.findAll();
    }

    /**
     * Return customer by id
     * @param id customer id
     * @return null if non existent
     */
    @GetMapping(value = "/chinook/customer/{id}")
    public Customer findCustomerById(@PathVariable Integer id){
        Optional<Customer> result =  customerRepository.findById(id);
        if(result.isEmpty()) return null;
        return result.get();
    }

    /**
     * Add new customer, json format
     * @param newCustomer json format
     * @return Customer object
     */
    @PostMapping(value = "/chinook/customer/add")
    public Customer addCustomer(@RequestBody Customer newCustomer){
        return customerRepository.save(newCustomer);
    }

    /**
     * Update customer by id. If id doesnt exist return no match found
      * @param id customer id
     * @return no match found if no id
     */
    @PutMapping(value = "/chinook/customer/update/{id}")
    public ResponseEntity<String> updateCustomerById(@PathVariable Integer id){
        Optional<Customer> result = customerRepository.findById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json; charset=utf-8");
        if(result.isPresent()){
            try{
                ResponseEntity<String> responseEntity =
                        new ResponseEntity<String>(objectMapper.writeValueAsString(result.get()), headers, HttpStatus.OK);
                return responseEntity;
            }catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
        return new ResponseEntity<String>("{\"message\": \"no match found\"}", headers, HttpStatus.OK);
    }

    @GetMapping(value = "/chinook/customer/email")
    public Customer searchCustomerByEmail(@RequestParam String email){
        return customerRepository.findCustomerByEmail(email);
    }
}
