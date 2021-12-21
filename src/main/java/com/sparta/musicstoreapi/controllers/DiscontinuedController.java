package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Discontinued;
import com.sparta.musicstoreapi.entities.Track;
import com.sparta.musicstoreapi.repositories.DiscontinuedRepository;
import com.sparta.musicstoreapi.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    ObjectMapper mapper;

    @PostMapping(value = "/track/discontinue/{trackId}")
    public ResponseEntity<?> discontinueTrack(@PathVariable Integer trackId, @RequestBody Discontinued discontinued) {
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

    @GetMapping(value = "/track/discontinued/{id}")
    public Discontinued getIsDiscontinued(@PathVariable Integer id) {
        Optional<Discontinued> result = discontinuedRepository.findById(id);
        return result.get();
    }

    @GetMapping(value = "/tracks/discontinued")
    public List<Discontinued> getAllDiscontinued() {
        return discontinuedRepository.findAll();
    }

    @DeleteMapping(value = "/track/discontinued/delete/{trackId}")
    public String deleteRow(@PathVariable Integer trackId) {
        Track result = trackRepository.getById(trackId);
        Integer toBeDeleted = discontinuedRepository.findByTrackId(result).getId();
        discontinuedRepository.deleteById(toBeDeleted);
        return "Row with : " + trackId + " deleted.";
    }

    @PutMapping(value = "/track/discontinue/update/{trackId}")
    public ResponseEntity<?> updateDiscontinued(@PathVariable Integer trackId, @RequestBody Discontinued newState) {
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
