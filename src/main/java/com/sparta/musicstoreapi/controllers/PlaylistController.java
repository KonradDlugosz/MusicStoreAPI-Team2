package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Customer;
import com.sparta.musicstoreapi.entities.Playlist;
import com.sparta.musicstoreapi.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PlaylistController {
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private ObjectMapper objectMapper;
    //CREATE - Level 2 permissions
    @PostMapping(value = "/chinook/playlist/add")
    public Playlist insertPlaylist(@RequestBody Playlist newPlaylist){ return playlistRepository.save(newPlaylist);}
    //UPDATE - Level 2 permissions

    @PutMapping(value = "/chinook/playlist/update/{id}")
    public ResponseEntity<String> updatePlaylistById(@PathVariable Integer id){
        Optional<Playlist> result = playlistRepository.findById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json; charset=utf-8");
        if(result.isPresent()){
            try{
                ResponseEntity<String> responseEntity =
                        new ResponseEntity<String>(objectMapper.writeValueAsString(result.get()), headers, HttpStatus.OK);
                return responseEntity;
            }catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
        return new ResponseEntity<String>("{\"message\": \"no match found\"}", headers, HttpStatus.OK);
    }
    @GetMapping(value = "/chinook/playlists")
    //READ - Get all, get by ID
    public List<Playlist> getAllPlaylists(){ return playlistRepository.findAll(); }
    @GetMapping(value = "/chinook/playlist")
    public Playlist getPlaylist(@RequestParam Integer id){
        Optional<Playlist> result = playlistRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        } else {
            return null;
        }
    }
    //DELETE - Level 2 permissions
    @DeleteMapping(value = "/chinook/playlist/delete")
    public void deletePlaylist(@RequestParam Integer id){ playlistRepository.deleteById(id); }

}
