package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Artist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ArtistControllerTest {
    private static final String GET_ALL_ARTIST = "http://localhost:8080/chinook/artist/findAll";
    private static final String GET_ARTIST_BY_ID = "http://localhost:8080/chinook/artist/1";
    private static final String POST_ARTIST = "http://localhost:8080/chinook/artist/add";
    private static final String PUT_ARTIST = "http://localhost:8080/chinook/artist/update";

    private static HttpResponse<String> getAllArtistsResponse = null;
    private static HttpResponse<String> getOneArtistResponse = null;
    private static Artist getOneArtistResponseJSON = null;
    private static HttpResponse<String> postArtistResponse = null;
    private static HttpResponse<String> putArtistResponse = null;
    
    @BeforeAll
    public static void getConnections(){
        getAllArtistsResponse = getRequest(GET_ALL_ARTIST);
        getOneArtistResponse = getRequest(GET_ARTIST_BY_ID);
        getOneArtistResponseJSON = getObjectMapper(getRequest(GET_ARTIST_BY_ID).body());
        postArtistResponse = postRequest(POST_ARTIST);
        putArtistResponse = putRequest(PUT_ARTIST);
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

    public static Artist getObjectMapper(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        Artist artist = null;
        try {
            artist = mapper.readValue(json, Artist.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return artist;
    }

    public static HttpResponse<String> postRequest(String url){
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                               "name": "RossBob"
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
                            "id":1,
                            "name": "UMI"
                        }
                        """))
                .build();
        return getResponse(request);
    }
    @Test
    @DisplayName("1: Given all Artist request, return 200 status")
    public void getAllArtistsStatusCode(){
        Assertions.assertEquals(200, getAllArtistsResponse.statusCode());
    }

    @Test
    @DisplayName("2: Given Artist ID, return 200 status")
    public void getOneArtistStatusCode(){
        Assertions.assertEquals(200, getOneArtistResponse.statusCode());
    }

    @Test
    @DisplayName("3: Given new Artist, return 200 status")
    public void postArtistStatusCheck(){
        Assertions.assertEquals(200, postArtistResponse.statusCode());
    }

    @Test
    @DisplayName("4: Given Artist update, return 200 status")
    public void putArtistStatusCheck(){
        Assertions.assertEquals(200, putArtistResponse.statusCode());
    }

}
