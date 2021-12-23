package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Invoice;
import com.sparta.musicstoreapi.entities.Token;
import com.sparta.musicstoreapi.repositories.InvoiceRepository;
import com.sparta.musicstoreapi.repositories.TokenRepository;
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
    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping(value = "/invoices/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<Invoice> findAllInvoices(@PathVariable String token){
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 1) {
                return invoiceRepository.findAll();
            }
        }
        return null;
    }

    @GetMapping(value = "/invoice/{id}/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Invoice findInvoicesById(@PathVariable Integer id, @PathVariable String token){
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 1) {
                Optional<Invoice> result = invoiceRepository.findById(id);
                if (result.isEmpty()) return null;
                return result.get();
            }
        }
        return null;
    }

    @PostMapping(value = "/invoice/add/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Invoice addInvoices(@RequestBody Invoice newInvoice, @PathVariable String token){
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 1) {
                return invoiceRepository.save(newInvoice);
            }
        }
        return null;
    }

    @PutMapping(value = "/invoice/update/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })

    public Invoice updateInvoice(@RequestBody Invoice newState, @PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 1) {
                Optional<Invoice> oldState = invoiceRepository.findById(newState.getId());
                if (oldState.isEmpty()) {
                    return null;
                }
                invoiceRepository.save(newState);
                return newState;
            }
        }
        return null;
    }

}
