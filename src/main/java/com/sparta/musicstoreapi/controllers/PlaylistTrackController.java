package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Playlisttrack;
import com.sparta.musicstoreapi.entities.PlaylisttrackId;
import com.sparta.musicstoreapi.entities.Token;
import com.sparta.musicstoreapi.entities.Track;
import com.sparta.musicstoreapi.repositories.PlaylistRepository;
import com.sparta.musicstoreapi.repositories.PlaylisttrackRepository;
import com.sparta.musicstoreapi.repositories.TokenRepository;
import com.sparta.musicstoreapi.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
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
    @Autowired
    private TokenRepository tokenRepository;
    //CREATE

    @PostMapping(value = "/chinook/playlisttrack/add/{playlistID}/{trackID}/{token}")
    public Playlisttrack insertPlaylist(@PathVariable Integer playlistID, @PathVariable Integer trackID, @PathVariable String token){
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 2) {
                if (!playlistRepository.existsById(playlistID)) {
                    return null;
                }
                Optional<Track> trackExists = trackRepository.findById(trackID);
                if (trackExists.isPresent()) {
                    PlaylisttrackId newPlaylisttrackId = new PlaylisttrackId();
                    newPlaylisttrackId.setPlaylistId(playlistID);
                    newPlaylisttrackId.setTrackId(trackExists.get().getId());
                    if (playlisttrackRepository.existsById(newPlaylisttrackId)) {
                        return null;
                    }
                    Playlisttrack newPlaylisttrack = new Playlisttrack();
                    newPlaylisttrack.setId(newPlaylisttrackId);
                    return newPlaylisttrack;

                }
                return null;
            }
        }
        return null;
    }
    //UPDATE

    @PutMapping(value = "/chinook/playlisttrack/update/{token}")
    public Playlisttrack updatePlaylistTrack(@RequestBody Playlisttrack newPlaylistTrack, @PathVariable String token){
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 2) {
                Optional<Playlisttrack> oldState = playlisttrackRepository.findById(newPlaylistTrack.getId());
                if (oldState.isEmpty()) return null;
                playlisttrackRepository.save(newPlaylistTrack);
                return newPlaylistTrack;
            }
        }
        return null;
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

    @GetMapping(value = "/chinook/playlisttrack", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Playlisttrack getPlaylistTrackByID(@RequestParam Integer id){
        return null;
    }


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

