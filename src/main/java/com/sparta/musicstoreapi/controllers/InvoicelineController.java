package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.*;
import com.sparta.musicstoreapi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping(value = "/chinook")
public class InvoicelineController {

    @Autowired
    private InvoicelineRepository invoicelineRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PlaylisttrackRepository playlisttrackRepository;
    @Autowired
    private AlbumdiscountRepository albumdiscountRepository;
    @Autowired
    private TrackdiscountRepository trackdiscountRepository;
    @Autowired
    private PlaylistdiscountRepository playlistdiscountRepository;
    @Autowired
    private TokenRepository tokenRepository;


    @GetMapping(value = "/invoicelines/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<Invoiceline> findAllInvoicelines(@PathVariable String token){
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 1) {
                return invoicelineRepository.findAll();
            }
        }
        return null;
    }

    @GetMapping(value = "/invoiceline/{id}/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Invoiceline findInvoicelineById(@PathVariable Integer id, @PathVariable String token){
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 0) {
                Optional<Invoiceline> result = invoicelineRepository.findById(id);
                if (result.isEmpty()) return null;
                return result.get();
            }
        }
        return null;
    }
    
    @GetMapping(value = "/invoiceline/invoice/{id}/{token}")
    public List<Invoiceline> findAllInvoicelinesForId(@PathVariable Integer id, @PathVariable String token){
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 0) {
                return invoicelineRepository
                        .findAll()
                        .stream()
                        .filter(invoiceline -> invoiceline.getInvoiceId() == id)
                        .toList();
            }
        }
        return null;
    }

    @PostMapping(value = "/invoiceline/track/add/{customerId}/{trackId}/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Invoiceline addInvoiceline(@PathVariable Integer customerId, @PathVariable Integer trackId, @PathVariable String token){
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        List<Integer> trackLibrary = checkIfTrackInLibrary(customerId);
        if(trackLibrary.contains(trackId)){
            System.out.println("Already in library!");
        }
        else {
            if (tokenResult.isPresent()) {
                if (tokenResult.get().getPermissionLevel() >= 0) {
                    BigDecimal totalPrice;
                    Invoiceline invoiceline = new Invoiceline();
                    Invoice invoice = checkIfOpenInvoiceExists(customerId);
                    Trackdiscount trackdiscount = null;

                    //Create invoice line for track
                    invoiceline.setInvoiceId(invoiceRepository.findById(invoice.getId()).get());
                    invoiceline.setTrackId(trackRepository.findById(trackId).get());
                    invoiceline.setUnitPrice(trackRepository.findById(trackId).get().getUnitPrice());
                    invoiceline.setQuantity(1);

                    //Get current total
                    totalPrice = invoiceRepository.findById(invoice.getId()).get().getTotal();

                    List<Trackdiscount> tracksdiscountList = trackdiscountRepository.findAll();
                    for (Trackdiscount track : tracksdiscountList) {
                        if (track.getTrackId() == trackId) {
                            trackdiscount = track;
                        }
                    }

                    if (trackdiscount == null) {
                        totalPrice = totalPrice.add(invoiceline.getUnitPrice());
                        updateInvoice(totalPrice, invoice);
                        return invoicelineRepository.save(invoiceline);
                    } else if (trackdiscount.getTrackId() == trackId) {
                        BigDecimal discount = trackdiscount.getAmount();
                        BigDecimal price = trackRepository.findById(trackId).get().getUnitPrice();
                        BigDecimal discountAmount = price.multiply(discount);
                        BigDecimal priceAfterDiscount = price.subtract(discountAmount);
                        invoiceline.setUnitPrice(priceAfterDiscount.round(new MathContext(2)));
                    }

                    totalPrice = totalPrice.add(invoiceline.getUnitPrice());
                    updateInvoice(totalPrice, invoice);
                    return invoicelineRepository.save(invoiceline);
                }
            }
        }
        return null;
    }

    @PostMapping(value = "/invoiceline/album/add/{customerId}/{albumId}/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<Invoiceline> addAlbumToInvoiceline(@PathVariable Integer albumId, @PathVariable Integer customerId, @PathVariable String token){
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 0) {
                BigDecimal totalPrice;
                List<Invoiceline> tracksAddedFromAlbum = new ArrayList<>();
                Albumdiscount albumdiscountToApply = null;
                Invoice invoice = checkIfOpenInvoiceExists(customerId);

                //Check what tracks to add
                List<Track> trackByAlbumId = trackRepository
                        .findAll()
                        .stream()
                        .filter(track -> track.getAlbumId() == albumId)
                        .toList();

                //find all album discounts
                List<Albumdiscount> albumDiscountsList = albumdiscountRepository.findAll();
                for (Albumdiscount albumdiscount : albumDiscountsList) {
                    if (albumdiscount.getAlbumId() == albumId) {
                        albumdiscountToApply = albumdiscount;
                    }
                }

                //Get current total
                totalPrice = invoiceRepository.findById(invoice.getId()).get().getTotal();
                List<Integer> trackLibrary = checkIfTrackInLibrary(customerId);

                //Add each track from album
                for (Track track : trackByAlbumId) {
                    if(trackLibrary.contains(track.getId())){
                        System.out.println("Already in library!");
                    } else {
                        Invoiceline invoiceline = new Invoiceline();
                        invoiceline.setInvoiceId(invoiceRepository.findById(invoice.getId()).get());
                        invoiceline.setTrackId(trackRepository.findById(track.getId()).get());
                        //Check if album on discount
                        if (albumdiscountToApply == null) {
                            invoiceline.setUnitPrice(track.getUnitPrice());
                        } else if (albumdiscountToApply.getAlbumId() == albumId) {
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
                }

                //update invoice
                updateInvoice(totalPrice, invoice);
                return tracksAddedFromAlbum;
            }
        }
        return null;
    }

    @PostMapping(value = "/invoiceline/playlist/add/{customerId}/{playlistId}/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<Invoiceline> addPlaylistToInvoiceLine(@PathVariable Integer playlistId, @PathVariable Integer customerId, @PathVariable String token){
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 0) {
                BigDecimal totalPrice;
                List<Invoiceline> tracksAddedFromPlaylist = new ArrayList<>();
                Playlistdiscount playlistDiscountToApply = null;
                Invoice invoice = checkIfOpenInvoiceExists(customerId);
                //Check what tracks to add
                List<Playlisttrack> trackByPlaylistId = playlisttrackRepository
                        .findAll()
                        .stream()
                        .filter(track -> track.getId().getPlaylistId() == playlistId)
                        .toList();
                //find all playlists discounts
                List<Playlistdiscount> playlistDiscountsList = playlistdiscountRepository.findAll();
                for (Playlistdiscount playlistdiscount : playlistDiscountsList) {
                    if (playlistdiscount.getPlayListId() == playlistId) {
                        playlistDiscountToApply = playlistdiscount;
                    }
                }

                //Get current total
                totalPrice = invoiceRepository.findById(invoice.getId()).get().getTotal();
                List<Integer> trackLibrary = checkIfTrackInLibrary(customerId);
                //Add each track from playlist
                for (Playlisttrack playlistTrack : trackByPlaylistId) {
                    if(trackLibrary.contains(playlistTrack.getId().getTrackId())){
                        System.out.println("Already in library!");
                    } else {
                        Invoiceline invoiceline = new Invoiceline();
                        invoiceline.setInvoiceId(invoiceRepository.findById(invoice.getId()).get());
                        invoiceline.setTrackId(trackRepository.findById(playlistTrack.getId().getPlaylistId()).get());
                        //Check if playlist on discount
                        if (playlistDiscountToApply == null) {
                            invoiceline.setUnitPrice(trackRepository.findById(playlistTrack.getId().getTrackId()).get().getUnitPrice());
                        } else if (playlistDiscountToApply.getPlayListId() == playlistId) {
                            BigDecimal discount = playlistDiscountToApply.getAmount();
                            BigDecimal price = trackRepository.findById(playlistTrack.getId().getTrackId()).get().getUnitPrice();
                            BigDecimal discountAmount = price.multiply(discount);
                            BigDecimal priceAfterDiscount = price.subtract(discountAmount);
                            invoiceline.setUnitPrice(priceAfterDiscount.round(new MathContext(2)));
                        }
                        invoiceline.setQuantity(1);
                        totalPrice = totalPrice.add(invoiceline.getUnitPrice());
                        tracksAddedFromPlaylist.add(invoicelineRepository.save(invoiceline));
                    }
                }
                //update invoice
                updateInvoice(totalPrice, invoice);
                return tracksAddedFromPlaylist;
            }
        }
        return null;
    }

    private void updateInvoice(BigDecimal totalPrice, Invoice invoice) {
        Optional<Invoice> invoiceToUpdate = invoiceRepository.findById(invoice.getId());
        if(invoiceToUpdate.isPresent()){
            invoiceToUpdate.get().setTotal(totalPrice);
            invoiceRepository.save(invoiceToUpdate.get());
        }
    }

    private Invoice checkIfOpenInvoiceExists(Integer customerId){
        Customer customer = customerRepository.getById(customerId);
        Invoice invoice;
        //check if open invoice exists
        List<Invoice> invoiceList = invoiceRepository
                .findAll()
                .stream()
                .filter(invoiceForCustomer -> invoiceForCustomer.getCustomerId() == customerId)
                .filter(openInvoice -> openInvoice.getInvoiceDate().isAfter(Instant.now()))
                .toList();

        //create new invoice
        if(invoiceList.size() >= 1){
            invoice = invoiceList.get(0);
        } else {
            invoice = new Invoice(customer,
                    Instant.now().plus(7, ChronoUnit.DAYS),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getState(),
                    customer.getCountry(),
                    customer.getPostalCode(),
                    new BigDecimal(0));
            invoiceRepository.save(invoice);
        }
        return invoice;
    }

    private List<Integer> checkIfTrackInLibrary(Integer customerId){
        List<Invoice> customerInvoices = invoiceRepository.findAll().stream().filter(invoice ->  invoice.getCustomerId() == customerId).toList();

        List<Integer> trackLibrary = new ArrayList<>();

        for(Invoice invoice : customerInvoices){
            List<Invoiceline> customerInvoicesLines = invoicelineRepository.findAll().stream().filter(invoiceline -> invoiceline.getInvoiceId() == invoice.getId()).toList();
                    for(Invoiceline invoiceline: customerInvoicesLines){
                        trackLibrary.add(invoiceline.getTrackId());
                    }
        }
        return trackLibrary;
    }

    @PutMapping(value = "/invoiceline/update/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Invoiceline updateInvoiceline(@RequestBody Invoiceline newState, @PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 1) {
                Optional<Invoiceline> oldState = invoicelineRepository.findById(newState.getId());
                if (oldState.isEmpty()) {
                    return null;
                }
                invoicelineRepository.save(newState);
                return newState;
            }
        }
        return null;
    }

    @DeleteMapping(value = "/invoiceline/delete/{id}/{token}")
    public Map<String, Boolean> deleteInvoiceLine(@PathVariable Integer id, @PathVariable String token){
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 0) {
                Optional<Invoiceline> invoiceline = invoicelineRepository.findById(id);
                Map<String, Boolean> response = new HashMap<>();

                if (invoiceline.isPresent()) {
                    BigDecimal totalPrice = invoiceRepository.findById(invoiceline.get().getInvoiceId()).get().getTotal();
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
        return null;
    }
}
