package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Playlist;
import com.sparta.musicstoreapi.entities.Playlisttrack;
import com.sparta.musicstoreapi.repositories.PlaylisttrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/chinook")
public class PlaylistTrackController {
    @Autowired
    private PlaylisttrackRepository playlisttrackRepository;
    //CREATE
    @PostMapping(value = "/playlisttrack/add", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Playlisttrack insertPlaylist(@RequestParam Playlisttrack newPlaylistTrack){
        Optional<Playlisttrack> PlaylistTrackExists = playlisttrackRepository.findById(newPlaylistTrack.getId());
        if(PlaylistTrackExists.isEmpty()){
            playlisttrackRepository.save(newPlaylistTrack);
            return newPlaylistTrack;
        }
        return null;
    }
    //UPDATE
    @PutMapping(value = "/playlisttrack/update", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Playlisttrack updatePlaylistTrack(@RequestParam Playlisttrack newPlaylistTrack){
        Optional<Playlisttrack> oldState = playlisttrackRepository.findById(newPlaylistTrack.getId());
        if(oldState.isEmpty()) return null;
        playlisttrackRepository.save(newPlaylistTrack);
        return newPlaylistTrack;
    }
    @GetMapping(value = "/playlisttracks", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    //READ - Get all, get by ID
    public List<Playlisttrack> getAllPlaylists(){
        return playlisttrackRepository.findAll();
    }

    @GetMapping(value = "/playlisttrack", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Playlisttrack getPlaylistTrackByID(@RequestParam Integer id){
        return null;
    }
    //DELETE
    @DeleteMapping(value = "/playlisttrack/delete", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public void deletePlaylist(@RequestParam Integer id){}
}
