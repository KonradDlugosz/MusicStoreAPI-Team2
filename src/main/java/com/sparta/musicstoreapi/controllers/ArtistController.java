package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Artist;
import com.sparta.musicstoreapi.entities.Token;
import com.sparta.musicstoreapi.repositories.ArtistRepository;
import com.sparta.musicstoreapi.utils.ArtistDAO;
import com.sparta.musicstoreapi.repositories.TokenRepository;
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

    @Autowired
    private ArtistDAO artistDAO;

    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping(value = "/artists", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<Artist> findAllArtists() {
        return artistRepository.findAll();
    }

    @GetMapping(value = "/artist/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, })
    public Optional<Artist> findArtistById(@PathVariable Integer id) {
        Optional<Artist> result = artistRepository.findById(id);
        if (result.equals(null)) {
            System.err.println("Result not found or is null");
            return null;
        }
        return result;
    }

    @PostMapping(value = "/artist/add/{token}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE,})
    public Artist addArtist(@RequestBody Artist newArtist, @PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 2) {
                return artistRepository.save(newArtist);
            }
        }
        return null;
    }

    @PutMapping(value = "/artist/update/{token}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE, })
    public Artist updateCustomerById(@RequestBody Artist newState, @PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 2) {
                Optional<Artist> oldState = artistRepository.findById(newState.getId());
                if (oldState.isEmpty()) {
                    System.err.println("Result not found or is null");
                    return null;
                }
                artistRepository.save(newState);
                return newState;
            }
        }
        return null;
    }


    @GetMapping(value = "/search/{s}")
    public List<Artist> search(@PathVariable String s){
        return artistDAO.searchQuery(s);
    }

}
