package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Mediatype;
import com.sparta.musicstoreapi.repositories.MediatypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class MediatypeController {

    @Autowired
    private MediatypeRepository mediatypeRepository;

    @GetMapping(value = "/chinook/allmediatypes")
    public List<Mediatype> getAllMediatypes() {
        return mediatypeRepository.findAll();
    }

    @GetMapping(value = "/chinook/mediatypes/{id}")
    public ResponseEntity<?> getMediatypesById(@PathVariable Integer id) {
        Optional<Mediatype> mediatype = mediatypeRepository.findById(id);
        if (mediatype.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mediatype not found");
        else
            return ResponseEntity.ok(mediatype.get());
    }


    @PostMapping(value = "/chinook/mediatype/add")
    public ResponseEntity<Mediatype> addNewMediatype(@RequestBody Mediatype newMediatype) {
        mediatypeRepository.save(newMediatype);
        return ResponseEntity.ok(newMediatype);
    }

    @PutMapping(value = "/chinook/mediatype/update")
    public ResponseEntity<Mediatype> updateMediatype(@Valid @RequestBody Mediatype mediatype) {
        Optional<Mediatype> results = mediatypeRepository.findById(mediatype.getId());
        if (results.isPresent()) {
            results.get().setName(mediatype.getName());
            final Mediatype updatedMediatype = mediatypeRepository.save(mediatype);
            return ResponseEntity.ok(updatedMediatype);

        } else
            return null;
    }
}
