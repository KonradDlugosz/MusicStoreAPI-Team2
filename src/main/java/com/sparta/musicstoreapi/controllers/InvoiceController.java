package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Invoice;
import com.sparta.musicstoreapi.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/chinook")
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping(value = "/invoices", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<Invoice> findAllInvoices(){
        return invoiceRepository.findAll();
    }

    @GetMapping(value = "/invoice/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Invoice findInvoicesById(@PathVariable Integer id){
        Optional<Invoice> result =  invoiceRepository.findById(id);
        if(result.isEmpty()) return null;
        return result.get();
    }

    @PostMapping(value = "/invoice/add", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Invoice addInvoices(@RequestBody Invoice newInvoice){
        return invoiceRepository.save(newInvoice);
    }

    @PutMapping(value = "/invoice/update", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })

    public Invoice updateInvoice(@RequestBody Invoice newState) {
        Optional<Invoice> oldState = invoiceRepository.findById(newState.getId());
        if (oldState.isEmpty()) {
            return null;
        }
        invoiceRepository.save(newState);
        return newState;
    }

}
