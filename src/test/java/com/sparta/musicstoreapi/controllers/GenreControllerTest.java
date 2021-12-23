package com.sparta.musicstoreapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


class GenreControllerTest {

    private static final String GET_ALL_GENRES = "http://localhost:8080/chinook/genres";
    private static final String GET_GENRE_BY_ID = "http://localhost:8080/chinook/genres/1";
    private static final String POST_GENRE = "http://localhost:8080/chinook/genres/add/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";

    private static HttpResponse<String> getAllGenresResponse = null;
    private static HttpResponse<String> getOneGenreResponse = null;
    private static Genre getOneGenreResponseJSON = null;
    private static HttpResponse<String> postGenreResponse = null;

    @BeforeAll
    public static void getConnections(){
        getAllGenresResponse = getRequest(GET_ALL_GENRES);
        getOneGenreResponse = getRequest(GET_GENRE_BY_ID);
        getOneGenreResponseJSON = getObjectMapper(getRequest(GET_GENRE_BY_ID).body());
        postGenreResponse = postRequest(POST_GENRE);
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

    public static Genre getObjectMapper(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        Genre genre = null;
        try {
            genre = mapper.readValue(json, Genre.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return genre;
    }

    public static HttpResponse<String> postRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                            "name": "Trip Hop"
                        }
                        """))
                .build();
        return getResponse(request);
    }

    @Test
    @DisplayName("Given all genres, return 200 status")
    public void getAllGenresStatusCodeTest(){
        Assertions.assertEquals(200, getAllGenresResponse.statusCode());
    }

    @Test
    @DisplayName("Given genre id, return 200 status")
    public void getOneGenreStatusCodeTest(){
        Assertions.assertEquals(200, getOneGenreResponse.statusCode());
    }

    @Test
    @DisplayName("Given genre id, return the id")
    public void getOneGenreIdTest(){
        Assertions.assertEquals(1, getOneGenreResponseJSON.getId());
    }

    @Test
    @DisplayName("Given a new genre, return status code")
    public void postGenreStatusCodeTest(){
        Assertions.assertEquals(200, postGenreResponse.statusCode());
    }


}

