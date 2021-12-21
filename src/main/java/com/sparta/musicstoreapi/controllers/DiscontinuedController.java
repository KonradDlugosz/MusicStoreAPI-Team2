package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Discontinued;
import com.sparta.musicstoreapi.entities.Track;
import com.sparta.musicstoreapi.repositories.DiscontinuedRepository;
import com.sparta.musicstoreapi.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class DiscontinuedController {
    @Autowired
    private DiscontinuedRepository discontinuedRepository;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    ObjectMapper mapper;

    @PostMapping(value = "/chinook/discontinue/{id}")
    public String discontinueTrack(@PathVariable Integer id) {
        Optional<Track> result = trackRepository.findById(id);
        Discontinued discontinued = new Discontinued();
        if (result.isPresent()) {
            discontinued.setTrackId(result.get());
            discontinued.setIsDiscontinued(true);
            discontinuedRepository.save(discontinued);
            return "Discontinued = true";
        } else {
            return null;
        }
    }

    @GetMapping(value = "/chinook/track/discontinued/{id}")
    public Discontinued getIsDiscontinued(@PathVariable Integer id) {
        Optional<Discontinued> result = discontinuedRepository.findById(id);
        return result.get();
    }


}
