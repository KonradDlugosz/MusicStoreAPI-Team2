package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Artist;
import com.sparta.musicstoreapi.repositories.ArtistRepository;
import com.sparta.musicstoreapi.utils.ArtistDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/chinook")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping(value = "/artist/findAll", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<Artist> findAllArtists() {
        return artistRepository.findAll();
    }

    @GetMapping(value = "/artist/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Optional<Artist> findArtistById(@PathVariable Integer id) {
        Optional<Artist> result = artistRepository.findById(id);
        if (result.equals(null)) {
            System.err.println("Result not found or is null");
            return null;
        }
        return result;
    }

    @PostMapping(value = "/artist/add", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Artist addArtist(@RequestBody Artist newArtist) {
        return artistRepository.save(newArtist);
    }

    @PutMapping(value = "/artist/update", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
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
