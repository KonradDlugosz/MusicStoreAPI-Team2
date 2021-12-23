package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Mediatype;
import com.sparta.musicstoreapi.entities.Token;
import com.sparta.musicstoreapi.repositories.MediatypeRepository;
import com.sparta.musicstoreapi.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/chinook")
public class MediatypeController {

    @Autowired
    private MediatypeRepository mediatypeRepository;
    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping(value = "/mediatypes", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<Mediatype> getAllMediatypes() {
        return mediatypeRepository.findAll();
    }

    @GetMapping(value = "/mediatypes/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> getMediatypesById(@PathVariable Integer id) {
        Optional<Mediatype> mediatype = mediatypeRepository.findById(id);
        if (mediatype.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mediatype not found");
        else
            return ResponseEntity.ok(mediatype.get());
    }


    @PostMapping(value = "/mediatype/add/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Mediatype> addNewMediatype(@RequestBody Mediatype newMediatype, @PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 2) {
                mediatypeRepository.save(newMediatype);
                return ResponseEntity.ok(newMediatype);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(value = "/mediatype/update/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Mediatype> updateMediatype(@Valid @RequestBody Mediatype mediatype, @PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 2) {
                Optional<Mediatype> results = mediatypeRepository.findById(mediatype.getId());
                if (results.isPresent()) {
                    results.get().setName(mediatype.getName());
                    final Mediatype updatedMediatype = mediatypeRepository.save(mediatype);
                    return ResponseEntity.ok(updatedMediatype);

                } else
                    return null;
            }
        }
        return null;
    }
}
