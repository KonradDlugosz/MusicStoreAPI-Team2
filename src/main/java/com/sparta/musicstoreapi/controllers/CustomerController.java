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

import javax.persistence.PreUpdate;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

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

    /**
     * Update customer by id. If id doesnt exist return no match found
      * @param id customer id
     * @param newDetails new customer details as json format
     * @return no match found if no id
     */
    @PutMapping(value = "/chinook/customer/update/{id}")
    public ResponseEntity<String> updateCustomerById(@PathVariable Integer id, @RequestBody Customer newDetails){
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
}
