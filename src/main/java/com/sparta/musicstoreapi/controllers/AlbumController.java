package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Album;
import com.sparta.musicstoreapi.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/chinook")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    ObjectMapper mapper;

    @GetMapping(value = "/album/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> getAlbumById(@PathVariable Integer id) {
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

    @GetMapping(value = "/albums", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    @PostMapping(value = "/album/insert" , produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public String insertAlbum(@RequestBody Album album) {
        if (albumRepository.existsById(album.getId())) {
            return "Album already exists.";
        } else {
            albumRepository.save(album);
            return "Saved";
        }
    }

    @PutMapping(value = "/album/update" , produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Album updateAlbum(@RequestBody Album newState) {
        Optional<Album> oldState = albumRepository.findById(newState.getId());
        if (oldState.isEmpty()) {
            return null;
        }
        albumRepository.save(newState);
        return newState;
    }
}
