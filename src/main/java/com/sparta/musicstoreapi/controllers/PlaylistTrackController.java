package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Playlist;
import com.sparta.musicstoreapi.entities.Playlisttrack;
import com.sparta.musicstoreapi.repositories.PlaylisttrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PlaylistTrackController {
    @Autowired
    private PlaylisttrackRepository playlisttrackRepository;
    //CREATE
    @PostMapping(value = "/chinook/playlisttrack/add")
    public Playlisttrack insertPlaylist(@RequestParam Playlisttrack newPlaylistTrack){
        Optional<Playlisttrack> PlaylistTrackExists = playlisttrackRepository.findById(newPlaylistTrack.getId());
        if(PlaylistTrackExists.isEmpty()){
            playlisttrackRepository.save(newPlaylistTrack);
            return newPlaylistTrack;
        }
        return null;
    }
    //UPDATE
    @PutMapping(value = "/chinook/playlisttrack/update")
    public Playlisttrack updatePlaylistTrack(@RequestParam Playlisttrack newPlaylistTrack){
        Optional<Playlisttrack> oldState = playlisttrackRepository.findById(newPlaylistTrack.getId());
        if(oldState.isEmpty()) return null;
        playlisttrackRepository.save(newPlaylistTrack);
        return newPlaylistTrack;
    }
    @GetMapping(value = "/chinook/playlisttracks")
    //READ - Get all, get by ID
    public List<Playlisttrack> getAllPlaylists(){ return playlisttrackRepository.findAll(); }
    @GetMapping(value = "/chinook/playlisttrack")
    public Playlisttrack getPlaylistTrackByID(@RequestParam Integer id){
        return null;
    }
    //DELETE
    @DeleteMapping(value = "/chinook/playlisttrack/delete")
    public void deletePlaylist(@RequestParam Integer id){}
}
