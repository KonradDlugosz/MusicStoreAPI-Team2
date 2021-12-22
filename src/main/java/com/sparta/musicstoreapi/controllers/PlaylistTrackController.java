package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Playlisttrack;
import com.sparta.musicstoreapi.entities.PlaylisttrackId;
import com.sparta.musicstoreapi.entities.Track;
import com.sparta.musicstoreapi.repositories.PlaylistRepository;
import com.sparta.musicstoreapi.repositories.PlaylisttrackRepository;
import com.sparta.musicstoreapi.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PlaylistTrackController {
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private PlaylisttrackRepository playlisttrackRepository;
    @Autowired
    private TrackRepository trackRepository;
    //CREATE

    @PostMapping(value = "/chinook/playlisttrack/add/{playlistID}/{trackID}")
    public Playlisttrack insertPlaylist(@PathVariable Integer playlistID, @PathVariable Integer trackID){
        if(!playlistRepository.existsById(playlistID)){ return null; }
        Optional<Track> trackExists = trackRepository.findById(trackID);
        if(trackExists.isPresent()){
            PlaylisttrackId newPlaylisttrackId = new PlaylisttrackId();
            newPlaylisttrackId.setPlaylistId(playlistID);
            newPlaylisttrackId.setTrackId(trackExists.get().getId());
            if(playlisttrackRepository.existsById(newPlaylisttrackId)){ return null; }
            Playlisttrack newPlaylisttrack = new Playlisttrack();
            newPlaylisttrack.setId(newPlaylisttrackId);
            return newPlaylisttrack;

        }
        return null;
    }
    //UPDATE

    @PutMapping(value = "/chinook/playlisttrack/update")
    public Playlisttrack updatePlaylistTrack(@RequestBody Playlisttrack newPlaylistTrack){
        Optional<Playlisttrack> oldState = playlisttrackRepository.findById(newPlaylistTrack.getId());
        if(oldState.isEmpty()) return null;
        playlisttrackRepository.save(newPlaylistTrack);
        return newPlaylistTrack;
    }

    @GetMapping(value = "/chinook/playlisttracks")
    //READ - Get all, get by ID
    public List<Playlisttrack> getAllPlaylists(){ return playlisttrackRepository.findAll(); }
    @GetMapping(value = "/chinook/playlisttrack/{playlistID}")
    public List<Playlisttrack> getPlaylistByPlaylistID(@PathVariable Integer playlistID){
        return playlisttrackRepository.findAll().stream()
                .filter( playlisttrack -> playlisttrack.getId().getPlaylistId() == playlistID)
                .toList();
    }

    @GetMapping(value = "/playlisttrack", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Playlisttrack getPlaylistTrackByID(@RequestParam Integer id){
        return null;
    }
    //DELETE
    @DeleteMapping(value = "/playlisttrack/delete", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public void deletePlaylist(@RequestParam Integer id){}

    @GetMapping(value = "/chinook/playlisttrack/{playlistID}/{trackID}")
    public Playlisttrack getPlaylistByPlaylistIDAndTrackID(@PathVariable Integer playlistID,@PathVariable Integer trackID){
        Optional<Playlisttrack> playlistTrackExists = playlisttrackRepository.findAll().stream()
                .filter(playlisttrack -> playlisttrack.getId().getPlaylistId() == playlistID && playlisttrack.getId().getTrackId() == trackID)
                .findFirst();
        if(playlistTrackExists.isPresent()){
            return playlistTrackExists.get();
        }
        return null;
    }

}

