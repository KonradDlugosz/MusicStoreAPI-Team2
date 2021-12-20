package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Invoice;
import com.sparta.musicstoreapi.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping(value = "/chinook/invoices")
    public List<Invoice> findAllInvoices(){
        return invoiceRepository.findAll();
    }

    @GetMapping(value = "/chinook/invoice")
    public Invoice findInvoicesById(@RequestParam Integer id){
        Optional<Invoice> result =  invoiceRepository.findById(id);
        if(result.isEmpty()) return null;
        return result.get();
    }

    @PostMapping(value = "/chinook/invoice/add")
    public Invoice addInvoices(@RequestBody Invoice newInvoice){
        return invoiceRepository.save(newInvoice);
    }

    @PutMapping(value = "/chinook/invocie/update")

    public Invoice updateInvoice(@RequestBody Invoice newState) {
        Optional<Invoice> oldState = invoiceRepository.findById(newState.getId());
        if (oldState.isEmpty()) {
            return null;
        }
        invoiceRepository.save(newState);
        return newState;
    }

}
