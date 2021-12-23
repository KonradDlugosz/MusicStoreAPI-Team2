package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Playlist;
import com.sparta.musicstoreapi.entities.Playlisttrack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PlaylisttrackTesting {

    private static final String GET_ALL_PLAYLISTTRACKS = "http://localhost:8080/chinook/playlisttracks";
    private static final String GET_PLAYLISTTRACK_BY_PALYLISTID_TRACKID = "http://localhost:8080/chinook/playlisttrack/11/1";
    private static final String POST_PLAYLISTTRACK =  "http://localhost:8080/chinook/playlisttrack/add/1/1/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";
    private static final String PUT_PLAYLISTTRACK = "http://localhost:8080/chinook/playlisttrack/update/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";

    private static HttpResponse<String> getAllPlaylistTracksResponse = null;
    private static HttpResponse<String> getOnePlaylistTrackResponse = null;
    private static HttpResponse<String> postPlaylistTrackResponse = null;
    private static HttpResponse<String> putPlaylistTrackResponse = null;

    @BeforeAll
    public static void getConnections(){
        getAllPlaylistTracksResponse = getRequest(GET_ALL_PLAYLISTTRACKS);
        getOnePlaylistTrackResponse = getRequest(GET_PLAYLISTTRACK_BY_PALYLISTID_TRACKID);
        postPlaylistTrackResponse = postRequest(POST_PLAYLISTTRACK);
        putPlaylistTrackResponse = putRequest(PUT_PLAYLISTTRACK);
    }
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
                        """))
                .build();
        return getResponse(request);
    }
    public static HttpResponse<String> putRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString("""
                        {
                            "id": {
                                "playlistId": 1,
                                "track": {
                                    "id": 1,
                                    "name": "For Those About To Rock (We Salute You)",
                                    "albumId": 1,
                                    "mediaTypeId": 1,
                                    "genreId": 1,
                                    "composer": "Angus Young, Malcolm Young, Brian Johnson",
                                    "milliseconds": 343719,
                                    "bytes": 11170334,
                                    "unitPrice": 0.99
                                }
                            }
                        }
                        """))
                .build();
        return getResponse(request);
    }

    @Test
    @DisplayName("1 Given all playlist track request, return 200 status")
    public void getAllPlaylistTracksStatusCode(){ Assertions.assertEquals(200, getAllPlaylistTracksResponse.statusCode()); }

    @Test
    @DisplayName("2 Given playlist ID and track ID, return 200 status")
    public void getOnePlaylistTrackStatusCode(){
        Assertions.assertEquals(200, getOnePlaylistTrackResponse.statusCode());
    }

    @Test
    @DisplayName("3 Given new playlist track, return 200 status")
    public void postPlaylistTrackStatusCheck(){ Assertions.assertEquals(200, postPlaylistTrackResponse.statusCode()); }

    @Test
    @DisplayName("4 Given playlist update, return 200 status")
    public void putPlaylistTrackStatusCheck(){
        Assertions.assertEquals(200, putPlaylistTrackResponse.statusCode());
    }
}