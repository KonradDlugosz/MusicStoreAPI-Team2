package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Customer;
import com.sparta.musicstoreapi.entities.Playlist;
import com.sparta.musicstoreapi.entities.Token;
import com.sparta.musicstoreapi.repositories.PlaylistRepository;
import com.sparta.musicstoreapi.repositories.TokenRepository;
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
public class PlaylistController {
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TokenRepository tokenRepository;

    //CREATE - Level 2 permissions
    @PostMapping(value = "/playlist/add/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Playlist insertPlaylist(@RequestBody Playlist newPlaylist, @PathVariable String token){
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 2) {
                return playlistRepository.save(newPlaylist);
            }
        }
        return null;
    }
    //UPDATE - Level 2 permissions

    @PutMapping(value = "/playlist/update/{id}/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<String> updatePlaylistById(@PathVariable Integer id, @PathVariable String token){
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 2) {
                Optional<Playlist> result = playlistRepository.findById(id);
                HttpHeaders headers = new HttpHeaders();
                headers.add("content-type", "application/json; charset=utf-8");
                if (result.isPresent()) {
                    try {
                        ResponseEntity<String> responseEntity =
                                new ResponseEntity<String>(objectMapper.writeValueAsString(result.get()), headers, HttpStatus.OK);
                        return responseEntity;
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
                return new ResponseEntity<String>("{\"message\": \"no match found\"}", headers, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping(value = "/playlists", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    //READ - Get all, get by ID
    public List<Playlist> getAllPlaylists(){ return playlistRepository.findAll(); }

    @GetMapping(value = "/playlist/{id}")
    public Playlist getPlaylist(@PathVariable Integer id){
        Optional<Playlist> result = playlistRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        } else {
            return null;
        }
    }
  
    //DELETE - Level 2 permissions
    @DeleteMapping(value = "/playlist/delete/{id}/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public void deletePlaylist(@PathVariable Integer id, @PathVariable String token){
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 2) {
                playlistRepository.deleteById(id);
            }
        }
    }
}