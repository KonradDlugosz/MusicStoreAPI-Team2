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

import javax.swing.text.html.Option;
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
        return discontinuedRepository.findAllByIsDiscontinuedTrue();
    }

    @DeleteMapping(value = "/track/discontinued/delete/{id}")
    public String deleteRow(@PathVariable Integer id) {
        discontinuedRepository.deleteById(id);
        return "ID: " + id + " deleted.";
    }

//    @PutMapping(value = "/track/discontinue/update/{trackId}")
//    public Discontinued updateDiscontinued(@PathVariable Integer trackId, @RequestBody Discontinued newState) {
//        Track result = trackRepository.getById(trackId);
//        newState.setTrackId(result);
//        Optional<Discontinued> oldState = discontinuedRepository.findById(newState.getId());
//        if (oldState.isEmpty()) {
//            return null;
//        }
//        discontinuedRepository.save(newState);
//        return newState;
//    }



}
