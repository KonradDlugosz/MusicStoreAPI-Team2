package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Playlist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PlaylistTesting {

    private static final String GET_ALL_PLAYLISTS = "http://localhost:8080/chinook/playlists";
    private static final String GET_PLAYLIST_BY_ID = "http://localhost:8080/chinook/playlist?id=11";
    private static final String POST_PLAYLIST =  "http://localhost:8080/chinook/playlist/add";
    private static final String PUT_PLAYLIST = "http://localhost:8080/chinook/playlist/update";

    private static HttpResponse<String> getAllPlaylistsResponse = null;
    private static HttpResponse<String> getOnePlaylistResponse = null;
    private static Playlist getOnePlaylistResponseJSON = null;
    private static HttpResponse<String> postPlaylistResponse = null;
    private static HttpResponse<String> putPlaylistResponse = null;

    @BeforeAll
    public static void getConnections(){
        getAllPlaylistsResponse = getRequest(GET_ALL_PLAYLISTS);
        getOnePlaylistResponse = getRequest(GET_PLAYLIST_BY_ID);
        getOnePlaylistResponseJSON = getObjectMapper(getRequest(GET_PLAYLIST_BY_ID).body());
        postPlaylistResponse = postRequest(POST_PLAYLIST);
        putPlaylistResponse = putRequest(PUT_PLAYLIST);
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
    public static Playlist getObjectMapper(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        Playlist playlist = null;
        try {
            playlist = mapper.readValue(json, Playlist.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return playlist;
    }
    public static HttpResponse<String> postRequest(String url){
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                            "name": "Music"
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
                            "id": 19,
                            "name": "MTV"
                        }
                        """))
                .build();
        return getResponse(request);
    }

    @Test
    @DisplayName("1 Given all playlist request, return 200 status")
    public void getAllInvoicesStatusCode(){ Assertions.assertEquals(200, getAllPlaylistsResponse.statusCode()); }

    @Test
    @DisplayName("2 Given playlist ID, return 200 status")
    public void getOneInvoiceStatusCode(){
        Assertions.assertEquals(200, getOnePlaylistResponse.statusCode());
    }

    @Test
    @DisplayName("3 Given new invoice, return 200 status")
    public void postInvoiceStatusCheck(){ Assertions.assertEquals(200, postPlaylistResponse.statusCode()); }

    @Test
    @DisplayName("4 Given invoice update, return 200 status")
    public void putInvoiceStatusCheck(){
        Assertions.assertEquals(200, putPlaylistResponse.statusCode());
    }
}
