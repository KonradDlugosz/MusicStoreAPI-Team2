package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Artist;
import com.sparta.musicstoreapi.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping(value = "/chinook/artist/findAll")
    public List<Artist> findAllArtists() {
        return artistRepository.findAll();
    }

    @GetMapping(value = "/chinook/artist")
    public Artist findArtistById(@RequestParam Integer id) {
        Artist result = artistRepository.getById(id);
        if (result.equals(null)) {
            System.err.println("Result not found or is null");
            return null;
        }
        return result;
    }

    @PostMapping(value = "/chinook/artist/add")
    public Artist addArtist(@RequestBody Artist newArtist) {
        return artistRepository.save(newArtist);
    }

    @PutMapping(value = "/chinook/artist/update")
    public Artist updateCustomerById(@RequestBody Artist newState) {
        Optional<Artist> oldState = artistRepository.findById(newState.getId());
        if (oldState.isEmpty()) {
            System.err.println("Result not found or is null");
            return null;
        }
        artistRepository.save(newState);
        return newState;
    }


}
