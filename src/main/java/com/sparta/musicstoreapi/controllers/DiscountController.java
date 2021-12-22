package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.*;
import com.sparta.musicstoreapi.repositories.AlbumdiscountRepository;
import com.sparta.musicstoreapi.repositories.PlaylistdiscountRepository;
import com.sparta.musicstoreapi.repositories.TrackdiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/chinook")
public class DiscountController {

    @Autowired
    private AlbumdiscountRepository albumdiscountRepository;
    @Autowired
    private PlaylistdiscountRepository playlistdiscountRepository;
    @Autowired
    private TrackdiscountRepository trackdiscountRepository;

    /**
     *  ALBUM Discounts requests
     */
    @GetMapping(value = "/album-discount", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<Albumdiscount> getAllAlbumDiscounts(){
        return albumdiscountRepository.findAll();
    }

    @GetMapping(value = "/album-discount/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAlbumDiscountByID(@PathVariable Integer id){
        Optional<Albumdiscount> albumdiscount = albumdiscountRepository.findById(id);
        if(albumdiscount.isPresent())
            return ResponseEntity.ok(albumdiscount);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Discount not found!");
    }


    @PostMapping(value = "album-discount/add", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Albumdiscount addAlbumDiscount(@RequestBody Albumdiscount albumdiscount){
       return albumdiscountRepository.save(albumdiscount);
    }

    @PutMapping(value = "album-discount/update", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> updateAlbumDiscount(@RequestBody Albumdiscount albumdiscount){
        Optional<Albumdiscount> oldState = albumdiscountRepository.findById(albumdiscount.getId());
        if(oldState.isPresent()) {
            albumdiscountRepository.save(albumdiscount);
            return ResponseEntity.ok(albumdiscount);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't update, discount doesn't exists");
        }
    }

    @DeleteMapping(value = "album-discount/delete/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Map<String,Boolean> deleteAlbumDiscount(@PathVariable Integer id){
        Optional<Albumdiscount> albumdiscount = albumdiscountRepository.findById(id);
        Map<String,Boolean> response = new HashMap<>();
        if(albumdiscount.isPresent()){
            albumdiscountRepository.delete(albumdiscount.get());
            response.put("Deleted", Boolean.TRUE);
        } else {
            response.put("Deleted", Boolean.FALSE);
        }
        return response;
    }

    /**
     * TRACKS discount requests
     */

    @GetMapping(value = "/tracks-discount", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<Trackdiscount> getAllTracksDiscounts(){
        return trackdiscountRepository.findAll();
    }

    @GetMapping(value = "/tracks-discount/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getTrackDiscountByID(@PathVariable Integer id){
        Optional<Trackdiscount> trackdiscount = trackdiscountRepository.findById(id);
        if(trackdiscount.isPresent())
            return ResponseEntity.ok(trackdiscount);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Discount not found!");
    }


    @PostMapping(value = "tracks-discount/add", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Trackdiscount addTrackDiscount(@RequestBody Trackdiscount trackdiscount){
        return trackdiscountRepository.save(trackdiscount);
    }

    @PutMapping(value = "tracks-discount/update", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> updateTrackDiscount(@RequestBody Trackdiscount trackdiscount){
        Optional<Trackdiscount> oldState = trackdiscountRepository.findById(trackdiscount.getId());
        if(oldState.isPresent()) {
            trackdiscountRepository.save(trackdiscount);
            return ResponseEntity.ok(trackdiscount);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't update, discount doesn't exists");
        }
    }

    @DeleteMapping(value = "tracks-discount/delete/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Map<String,Boolean> deleteTrackDiscount(@PathVariable Integer id){
        Optional<Trackdiscount> trackdiscount = trackdiscountRepository.findById(id);
        Map<String,Boolean> response = new HashMap<>();
        if(trackdiscount.isPresent()){
            trackdiscountRepository.delete(trackdiscount.get());
            response.put("Deleted", Boolean.TRUE);
        } else {
            response.put("Deleted", Boolean.FALSE);
        }
        return response;
    }


    /**
     * PLAYLISTS discount requests
     */

    @GetMapping(value = "/playlists-discount", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<Playlistdiscount> getAllPlaylistsDiscounts(){
        return playlistdiscountRepository.findAll();
    }

    @GetMapping(value = "/playlists-discount/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getPlaylistsDiscountByID(@PathVariable Integer id){
        Optional<Playlistdiscount> playlistdiscount = playlistdiscountRepository.findById(id);
        if(playlistdiscount.isPresent())
            return ResponseEntity.ok(playlistdiscount);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Discount not found!");
    }


    @PostMapping(value = "playlists-discount/add", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Playlistdiscount addPlaylistsDiscount(@RequestBody Playlistdiscount playlistdiscount){
        return playlistdiscountRepository.save(playlistdiscount);
    }

    @PutMapping(value = "playlists-discount/update", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> updatePlaylistsDiscount(@RequestBody Playlistdiscount playlistdiscount){
        Optional<Playlistdiscount> oldState = playlistdiscountRepository.findById(playlistdiscount.getId());
        if(oldState.isPresent()) {
            playlistdiscountRepository.save(playlistdiscount);
            return ResponseEntity.ok(playlistdiscount);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't update, discount doesn't exists");
        }
    }

    @DeleteMapping(value = "playlists-discount/delete/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Map<String,Boolean> deletePlaylistsDiscount(@PathVariable Integer id){
        Optional<Playlistdiscount> playlistdiscount = playlistdiscountRepository.findById(id);
        Map<String,Boolean> response = new HashMap<>();
        if(playlistdiscount.isPresent()){
            playlistdiscountRepository.delete(playlistdiscount.get());
            response.put("Deleted", Boolean.TRUE);
        } else {
            response.put("Deleted", Boolean.FALSE);
        }
        return response;
    }

}
