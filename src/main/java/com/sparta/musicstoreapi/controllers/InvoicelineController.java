package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Albumdiscount;
import com.sparta.musicstoreapi.entities.Invoiceline;
import com.sparta.musicstoreapi.entities.Track;
import com.sparta.musicstoreapi.entities.Trackdiscount;
import com.sparta.musicstoreapi.repositories.AlbumdiscountRepository;
import com.sparta.musicstoreapi.repositories.InvoicelineRepository;
import com.sparta.musicstoreapi.repositories.TrackRepository;
import com.sparta.musicstoreapi.repositories.TrackdiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class InvoicelineController {

    @Autowired
    private InvoicelineRepository invoicelineRepository;
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
    public List<Invoiceline> addAlbumToInvoiceline(@PathVariable Integer albumId){
        List<Track> trackByAlbumId = trackRepository
                .findAll()
                .stream()
                .filter(track -> track.getAlbumId() == albumId)
                .toList();

        for(Track track: trackByAlbumId){
            Invoiceline invoiceline = new Invoiceline();
            //invoiceline.setInvoiceId();
            invoicelineRepository.save(invoiceline);
        }
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
