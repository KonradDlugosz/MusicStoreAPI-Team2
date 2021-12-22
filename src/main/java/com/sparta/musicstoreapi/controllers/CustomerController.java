package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Customer;
import com.sparta.musicstoreapi.entities.Track;
import com.sparta.musicstoreapi.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/chinook")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * Get all customers
     * @return all customers
     */
    @GetMapping(value = "/allcustomer", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<Customer> findAllCustomer(){
        return customerRepository.findAll();
    }

    /**
     * Return customer by id
     * @param id customer id
     * @return null if non existent
     */
    @GetMapping(value = "/customer/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
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
    @PostMapping(value = "/customer/add", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Customer addCustomer(@RequestBody Customer newCustomer){
        return customerRepository.save(newCustomer);
    }

    /**
     * Update customer by id. If id doesnt exist return no match found
     * @return no match found if no id
     */
    @PutMapping(value = "/customer/update", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> updateCustomer(@Valid @RequestBody Customer customer){
        Optional<Customer> customerToUpdate = customerRepository.findById(customer.getId());
        if(customerToUpdate.isPresent()){
            customerRepository.save(customer);
            return new ResponseEntity<String>("Customer updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Customer doesn't exists.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/customer/email", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Customer searchCustomerByEmail(@RequestParam String email){
        return customerRepository.findCustomerByEmail(email);
    }
}
