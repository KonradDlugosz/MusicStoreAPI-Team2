package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Invoice;
import com.sparta.musicstoreapi.entities.Invoiceline;
import com.sparta.musicstoreapi.repositories.InvoiceRepository;
import com.sparta.musicstoreapi.repositories.InvoicelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class InvoicelineController {

    @Autowired
    private InvoicelineRepository invoicelineRepository;

    @GetMapping(value = "/chinook/invoicelines")
    public List<Invoiceline> findAllInvoicelines(){
        return invoicelineRepository.findAll();
    }

    @GetMapping(value = "/chinook/invoiceline")
    public Invoiceline findInvoicelineById(@RequestParam Integer id){
        Optional<Invoiceline> result =  invoicelineRepository.findById(id);
        if(result.isEmpty()) return null;
        return result.get();
    }

    @PostMapping(value = "/chinook/invoiceline/add")
    public Invoiceline addInvoiceline(@RequestBody Invoiceline newInvoiceline){
        return invoicelineRepository.save(newInvoiceline);
    }

    @PutMapping(value = "/chinook/invoiceline/update")
    public Invoiceline updateInvoiceline(@RequestBody Invoiceline newState) {
        Optional<Invoiceline> oldState = invoicelineRepository.findById(newState.getId());
        if (oldState.isEmpty()) {
            return null;
        }
        invoicelineRepository.save(newState);
        return newState;
    }


}
