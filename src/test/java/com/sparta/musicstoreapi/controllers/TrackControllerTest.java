package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Track;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TrackControllerTest {

    private static final String GET_ALL_TRACKS = "http://localhost:8080/chinook/tracks";
    private static final String GET_TRACK_BY_ID = "http://localhost:8080/chinook/tracks/10";
    private static final String GET_TRACK_BY_NAME = "http://localhost:8080/chinook/tracks-by-name?name=Balls";
    private static final String GET_TRACK_BY_ALBUM = "http://localhost:8080/chinook/tracks-by-album/3";
    private static final String GET_TRACK_BY_ARTIST = "http://localhost:8080/chinook/tracks-by-artist/1";
    private static final String POST_TRACK =  "http://localhost:8080/chinook/tracks/add";
    private static final String PUT_TRACK = "http://localhost:8080/chinook/tracks/update";

    private static HttpResponse<String> getAllTracksResponse = null;
    private static HttpResponse<String> getOneTrackResponse = null;
    private static Track getOneTrackResponseJSON = null;
    private static HttpResponse<String> searchByNameResponse = null;
    private static HttpResponse<String> getTracksByAlbumId = null;
    private static HttpResponse<String> getTracksByArtist = null;
    private static HttpResponse<String> postTrackResponse = null;
    private static HttpResponse<String> putTrackResponse = null;

    @BeforeAll
    public static void getConnections(){
        getAllTracksResponse = getRequest(GET_ALL_TRACKS);
        getOneTrackResponse = getRequest(GET_TRACK_BY_ID);
        getOneTrackResponseJSON = getObjectMapper(getRequest(GET_TRACK_BY_ID).body());
        searchByNameResponse = getRequest(GET_TRACK_BY_NAME);
        getTracksByAlbumId = getRequest(GET_TRACK_BY_ALBUM);
        getTracksByArtist = getRequest(GET_TRACK_BY_ARTIST);
        postTrackResponse = postRequest(POST_TRACK);
        putTrackResponse = putRequest(PUT_TRACK);
    }

    /**
     * Base methods to get HttpResponse and deserialize JSON to Java Objects POJO
     * @param request -> Http request with headers, method like GET/POST/DELETE etc.
     * @return response for track request
     */

    public static HttpResponse<String> getResponse(HttpRequest request) {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static HttpResponse<String> getRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .build();
        return getResponse(request);
    }

    public static HttpResponse<String> postRequest(String url){
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                            "name": "Breaking The Rules",
                            "albumId": 3,
                            "mediaTypeId": 1,
                            "genreId": 1,
                            "composer": "Angus Young, Malcolm Young, Brian Johnson",
                            "milliseconds": 263288,
                            "bytes": 8596840,
                            "unitPrice": 0.99
                        }
                        """))
                .build();
        return getResponse(request);
    }

    public static HttpResponse<String> putRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString("""
                        {
                            "id":10,
                            "name": "Breaking The Rules",
                            "albumId": 3,
                            "mediaTypeId": 1,
                            "genreId": 1,
                            "composer": "Angus Young, Malcolm Young, Brian Johnson",
                            "milliseconds": 263288,
                            "bytes": 8596840,
                            "unitPrice": 0.99
                        }
                                                
                        """))
                .build();
        return getResponse(request);
    }

    public static Track getObjectMapper(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Track track = null;
        try {
            track = mapper.readValue(json, Track.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return track;
    }

    /**
     * Tests for GET all tracks
     */

    @Test
    @DisplayName("1.1 Given all tracks request, return 200 status")
    public void getAllTracksStatusCode(){
        Assertions.assertEquals(200, getAllTracksResponse.statusCode());
    }

    /**
     * Tests for GET Track by ID
     */

    @Test
    @DisplayName("2.1 Given track ID, return 200 status")
    public void getOneTrackStatusCode(){
        Assertions.assertEquals(200, getOneTrackResponse.statusCode());
    }

    @Test
    @DisplayName("2.2 Given track ID, return Track name")
    public void getOneTrackName(){
        Assertions.assertEquals("Breaking The Rules", getOneTrackResponseJSON.getName());
    }

    @Test
    @DisplayName("2.3 Given track ID, return Track album id")
    public void getOneTrackAlbumID(){
        Assertions.assertEquals(3, getOneTrackResponseJSON.getAlbumId());
    }
    @Test
    @DisplayName("2.4 Given track ID, return Track composer")
    public void getOneTrackComposer(){
        Assertions.assertEquals("Angus Young, Malcolm Young, Brian Johnson", getOneTrackResponseJSON.getComposer());
    }

    /**
     * Tests for search tracks by name
     */
    @Test
    @DisplayName("3.1 Given track search, return 200 status")
    public void searchByNameStatusCode(){
        Assertions.assertEquals(200, searchByNameResponse.statusCode());
    }

    /**
     * Tests for GET tracks by Album ID
     */
    @Test
    @DisplayName("4.1 Given album id, return 200 status")
    public void getTracksByAlbumIdStatusCode(){
        Assertions.assertEquals(200, getTracksByAlbumId.statusCode());
    }

    /**
     * Test for GET tracks by artist
     */
    @Test
    @DisplayName("5.1 Given artist ID, return 200 status")
    public void getTracksByArtistStatusCode(){
        Assertions.assertEquals(200, getTracksByArtist.statusCode());
    }

    /**
     * Tests for POST track
     */
    @Test
    @DisplayName("6.1 Given new track, return 200 status")
    public void postTrackStatusCheck(){
        Assertions.assertEquals(200, postTrackResponse.statusCode());
    }

    /**
     * Tests for PUT track
     */

    @Test
    @DisplayName("7.1 Given track update, return 200 status")
    public void putTrackStatusCheck(){
        Assertions.assertEquals(200, putTrackResponse.statusCode());
    }
}
