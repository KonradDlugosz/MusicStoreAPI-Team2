package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Invoiceline;
import com.sparta.musicstoreapi.repositories.InvoicelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/chinook")
public class InvoicelineController {

    @Autowired
    private InvoicelineRepository invoicelineRepository;

    @GetMapping(value = "/invoicelines", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<Invoiceline> findAllInvoicelines(){
        return invoicelineRepository.findAll();
    }

    @GetMapping(value = "/invoiceline/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Invoiceline findInvoicelineById(@PathVariable Integer id){
        Optional<Invoiceline> result =  invoicelineRepository.findById(id);
        if(result.isEmpty()) return null;
        return result.get();
    }

    @PostMapping(value = "/invoiceline/add", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Invoiceline addInvoiceline(@RequestBody Invoiceline newInvoiceline){
        return invoicelineRepository.save(newInvoiceline);
    }

    @PutMapping(value = "/invoiceline/update", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Invoiceline updateInvoiceline(@RequestBody Invoiceline newState) {
        Optional<Invoiceline> oldState = invoicelineRepository.findById(newState.getId());
        if (oldState.isEmpty()) {
            return null;
        }
        invoicelineRepository.save(newState);
        return newState;
    }
    
}
