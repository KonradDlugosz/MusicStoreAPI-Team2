package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Album;
import com.sparta.musicstoreapi.repositories.AlbumRepository;
import jdk.security.jarsigner.JarSignerException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    ObjectMapper mapper;

    @GetMapping(value = "/chinook/album")
    public ResponseEntity<String> getAlbumById(@RequestParam Integer id) {
        Optional<Album> result = albumRepository.findById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json; charset=utf-8");
        if (result.isPresent()) {
            try {
                ResponseEntity<String> resp = new ResponseEntity<String>(mapper.writeValueAsString(result.get()), headers, HttpStatus.OK);
                return resp;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } return new ResponseEntity<String>("{\"message\": \"That album doesnt exist\"}", headers, HttpStatus.OK);
    }
}
