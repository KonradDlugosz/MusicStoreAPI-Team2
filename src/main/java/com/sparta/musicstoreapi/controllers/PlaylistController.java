package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Playlist;
import com.sparta.musicstoreapi.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class PlaylistController {
    @Autowired
    private PlaylistRepository playlistRepository;
    //CREATE - Level 2 permissions
    @PostMapping(value = "/chinnok/playlist/add")
    public Playlist insertPlaylist(@RequestParam Playlist newPlaylist){
        Optional<Playlist> playlistExists = playlistRepository.findById(newPlaylist.getId());
        if(!playlistExists.isEmpty()){ return null;}
        playlistRepository.save(newPlaylist);
        return newPlaylist;
    }
    //UPDATE - Level 2 permissions
    @PutMapping(value = "/chinnok/playlist/update")
    public Playlist updatePlaylist(@RequestParam Playlist newPlaylist){
        Optional<Playlist> oldState = playlistRepository.findById(newPlaylist.getId());
        if(oldState.isEmpty()) return null;
        playlistRepository.save(newPlaylist);
        return newPlaylist;
    }
    @GetMapping(value = "/chinnok/playlists")
    //READ - Get all, get by ID
    public List<Playlist> getAllPlaylists(){ return playlistRepository.findAll(); }
    @GetMapping(value = "/chinnok/playlist")
    public Playlist getPlaylist(@RequestParam Integer id){
        Optional<Playlist> result = playlistRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        } else {
            return null;
        }
    }
    //DELETE - Level 2 permissions
    @DeleteMapping(value = "/chinnok/playlist/delete")
    public void deletePlaylist(@RequestParam Integer id){ playlistRepository.deleteById(id); }

}
