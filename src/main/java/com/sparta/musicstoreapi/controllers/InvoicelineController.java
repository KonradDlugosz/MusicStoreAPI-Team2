package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.*;
import com.sparta.musicstoreapi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

@RestController
public class InvoicelineController {

    @Autowired
    private InvoicelineRepository invoicelineRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private AlbumdiscountRepository albumdiscountRepository;
    @Autowired
    private TrackdiscountRepository trackdiscountRepository;
    @Autowired
    private TrackRepository trackRepository;

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
    
    @GetMapping(value = "/chinook/invoiceline/{invoiceId}")
    public List<Invoiceline> findAllInvoicelinesForId(@PathVariable Integer invoiceId){
        return invoicelineRepository
                .findAll()
                .stream()
                .filter(invoiceline -> invoiceline.getInvoiceId() == invoiceId)
                .toList();
    }

    @PostMapping(value = "/chinook/invoiceline/track/add")
    public Invoiceline addInvoiceline(@RequestBody Invoiceline newInvoiceline){
        Invoiceline invoiceline = newInvoiceline;
        Trackdiscount trackdiscount = null;

        List<Trackdiscount> tracksdiscountList = trackdiscountRepository.findAll();
        for(Trackdiscount track : tracksdiscountList){
            if(track.getTrackId() == newInvoiceline.getTrackId()){
                trackdiscount = track;
            }
        }

        if(trackdiscount == null){
            return invoicelineRepository.save(invoiceline);
        } else if(trackdiscount.getTrackId() == newInvoiceline.getTrackId()){
            BigDecimal discount = trackdiscount.getAmount();
            BigDecimal price = newInvoiceline.getUnitPrice();
            BigDecimal discountAmount = price.multiply(discount);
            BigDecimal priceAfterDiscount = price.subtract(discountAmount);
            invoiceline.setUnitPrice(priceAfterDiscount.round(new MathContext(2)));
        }

        return invoicelineRepository.save(invoiceline);
    }

    @PostMapping(value = "/chinook/invoiceline/album/add/{albumId}")
    public List<Invoiceline> addAlbumToInvoiceline(@PathVariable Integer albumId, @RequestBody Invoiceline invoicelineBody){
        List<Invoiceline> tracksAddedFromAlbum = new ArrayList<>();
        Albumdiscount albumdiscountToApply = null;

        //Check what tracks to add
        List<Track> trackByAlbumId = trackRepository
                .findAll()
                .stream()
                .filter(track -> track.getAlbumId() == albumId)
                .toList();

        //Current invoice price
        BigDecimal totalPrice = invoiceRepository.findById(invoicelineBody.getInvoiceId()).get().getTotal();

        //find all album discounts
        List<Albumdiscount> albumDiscountsList = albumdiscountRepository.findAll();
        for(Albumdiscount albumdiscount : albumDiscountsList){
            if(albumdiscount.getAlbumId() == albumId){
                albumdiscountToApply = albumdiscount;
            }
        }

        //Add each track from album
        for(Track track: trackByAlbumId){
            Invoiceline invoiceline = new Invoiceline();
            invoiceline.setInvoiceId(invoiceRepository.findById(invoicelineBody.getInvoiceId()).get());
            invoiceline.setTrackId(trackRepository.findById(track.getId()).get());
            //Check if album on discount
            if(albumdiscountToApply == null){
                invoiceline.setUnitPrice(track.getUnitPrice());
            }
            else if(albumdiscountToApply.getAlbumId() == albumId){
                BigDecimal discount = albumdiscountToApply.getAmount();
                BigDecimal price = track.getUnitPrice();
                BigDecimal discountAmount = price.multiply(discount);
                BigDecimal priceAfterDiscount = price.subtract(discountAmount);
                invoiceline.setUnitPrice(priceAfterDiscount.round(new MathContext(2)));
            }
            invoiceline.setQuantity(1);
            totalPrice = totalPrice.add(track.getUnitPrice());
            tracksAddedFromAlbum.add(invoicelineRepository.save(invoiceline));
        }

        //update invoice
        Optional<Invoice> invoice = invoiceRepository.findById(invoicelineBody.getInvoiceId());
        if(invoice.isPresent()){
            invoice.get().setTotal(totalPrice);
            invoiceRepository.save(invoice.get());
        }
        return tracksAddedFromAlbum;
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

    @DeleteMapping(value = "/chinook/invoiceline/delete/{id}")
    public Map<String, Boolean> deleteInvoiceLine(@PathVariable Integer id){
        Optional<Invoiceline> invoiceline = invoicelineRepository.findById(id);
        Map<String,Boolean> response = new HashMap<>();
        BigDecimal totalPrice = invoiceRepository.findById(invoiceline.get().getInvoiceId()).get().getTotal();

        if(invoiceline.isPresent()){
            Invoice invoice = invoiceRepository.getById(invoiceline.get().getInvoiceId());
            invoice.setTotal(totalPrice.subtract(invoiceline.get().getUnitPrice()));
            invoicelineRepository.delete(invoiceline.get());
            invoiceRepository.save(invoice);
            response.put("Deleted", Boolean.TRUE);
        } else {
            response.put("Deleted", Boolean.FALSE);
        }
        return response;
    }
    
}
