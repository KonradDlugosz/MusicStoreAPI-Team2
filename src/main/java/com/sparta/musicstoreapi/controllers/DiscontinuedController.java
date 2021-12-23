package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Discontinued;
import com.sparta.musicstoreapi.entities.Token;
import com.sparta.musicstoreapi.entities.Track;
import com.sparta.musicstoreapi.repositories.DiscontinuedRepository;
import com.sparta.musicstoreapi.repositories.TokenRepository;
import com.sparta.musicstoreapi.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/chinook")
public class DiscontinuedController {
    @Autowired
    private DiscontinuedRepository discontinuedRepository;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    ObjectMapper mapper;

    @PostMapping(value = "/track/discontinue/{trackId}/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, })
    public ResponseEntity<?> discontinueTrack(@PathVariable Integer trackId, @RequestBody Discontinued discontinued, @PathVariable String token ) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 2) {
                Optional<Track> trackResult = trackRepository.findById(trackId);
                if (trackResult.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Track not found!");
                } else {
                    Track result = trackRepository.getById(trackId);
                    discontinued.setTrackId(result);
                    discontinuedRepository.save(discontinued);
                    Discontinued discontinuedResult = discontinuedRepository.getById(discontinued.getId());
                    return ResponseEntity.ok(discontinuedResult);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/track/discontinued/{trackId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, })
    public Discontinued getIsDiscontinued(@PathVariable Integer trackId) {
        Track result = trackRepository.getById(trackId);
        Integer id = discontinuedRepository.findByTrackId(result).getId();
        Optional<Discontinued> discResult = discontinuedRepository.findById(id);
        return discResult.get();
    }

    @GetMapping(value = "/tracks/discontinued/everything", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, })
    public List<Discontinued> getAllTableEntries() {
        return discontinuedRepository.findAll();
    }

    @GetMapping(value = "/tracks/discontinued/true", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, })
    public List<Discontinued> getAllDiscontinuedTrue() {
        return discontinuedRepository.findAllByIsDiscontinuedTrue();
    }

    @GetMapping(value = "/tracks/discontinued/false", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, })
    public List<Discontinued> getAllDiscontinuedFalse() {
        return discontinuedRepository.findAllByIsDiscontinuedFalse();
    }

    @DeleteMapping(value = "/track/discontinued/delete/{trackId}/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, })
    public String deleteRow(@PathVariable Integer trackId, @PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 1) {
                Track result = trackRepository.getById(trackId);
                Integer toBeDeleted = discontinuedRepository.findByTrackId(result).getId();
                discontinuedRepository.deleteById(toBeDeleted);
                return "Row with : " + trackId + " deleted.";
            }
        }
        return null;
    }

    @PutMapping(value = "/track/discontinue/update/{trackId}/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> updateDiscontinued(@PathVariable Integer trackId, @RequestBody Discontinued newState, @PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 1) {
                Optional<Track> trackResult = trackRepository.findById(trackId);
                Track result = trackRepository.getById(trackId);
                Integer id = discontinuedRepository.findByTrackId(result).getId();
                newState.setId(id);
                newState.setTrackId(result);

                if (trackResult.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Track not found!");
                } else {
                    Optional<Discontinued> oldState = discontinuedRepository.findById(newState.getId());
                    if (oldState.isEmpty()) {
                        return null;
                    }
                    discontinuedRepository.save(newState);
                    return ResponseEntity.ok(newState);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
