package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Discontinued;
import com.sparta.musicstoreapi.repositories.DiscontinuedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscontinuedController {
    @Autowired
    private DiscontinuedRepository discontinuedRepository;
    @Autowired
    ObjectMapper mapper;

    @PostMapping(value = "/chinook/track/discontinue")
    public Discontinued discontinueTrack(@RequestBody Discontinued discontinued) {
        return discontinuedRepository.save(discontinued);
    }


}
