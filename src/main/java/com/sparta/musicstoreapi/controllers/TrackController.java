package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Album;
import com.sparta.musicstoreapi.entities.Artist;
import com.sparta.musicstoreapi.entities.Track;
import com.sparta.musicstoreapi.repositories.AlbumRepository;
import com.sparta.musicstoreapi.repositories.ArtistRepository;
import com.sparta.musicstoreapi.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/chinook")
public class TrackController {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @GetMapping(value = "/tracks", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<Track> getAllTracks(){
        return trackRepository.findAll();
    }

    @GetMapping(value = "/tracks/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getTrackById(@PathVariable Integer id){
        Optional<Track> track = trackRepository.findById(id);
        if(track.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Track not found!");
        else
            return ResponseEntity.ok(track.get());
    }

    @GetMapping(value = "/tracks-by-name", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<Track> getTrackBySearch(@RequestParam String name){
        return trackRepository.findByNameContains(name);
    }

    @GetMapping(value = "/tracks-by-album/{albumId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<Track> getTrackByAlbumID(@PathVariable Integer albumId){
        List<Track> trackByAlbumId = trackRepository
                .findAll()
                .stream()
                .filter(track -> track.getAlbumId() == albumId)
                .toList();
        return trackByAlbumId;
    }

    @GetMapping(value = "/tracks-by-artist/{artistId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<Track> getTrackByArtist(@PathVariable Integer artistId){

        List<Track> allTracksForArtist = new ArrayList<>();
        Optional<Artist> artist = artistRepository.findById(artistId);
        if(artist.isPresent()){
            List<Album> albumListForArtist = albumRepository
                    .findAll()
                    .stream()
                    .filter(album -> album.getArtistId().getId() == artistId)
                    .toList();

            for(Album album : albumListForArtist){
                List<Track> tracksForArtist = trackRepository
                        .findAll()
                        .stream()
                        .filter(track -> track.getAlbumId() == album.getId())
                        .toList();
                allTracksForArtist.addAll(tracksForArtist);
            }
        }
        return allTracksForArtist;
    }


    @PostMapping(value = "tracks/add", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Track addNewTrac(@Valid @RequestBody Track track){
        return trackRepository.save(track);
    }

    @PutMapping(value = "tracks/update", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> updateTrack(@Valid @RequestBody Track track){
        Optional<Track> trackToUpdate = trackRepository.findById(track.getId());
        if(trackToUpdate.isPresent()){
            trackRepository.save(track);
            return new ResponseEntity<String>("Track updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Track doesn't exists.", HttpStatus.NOT_FOUND);
        }
    }
}
