package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenreControllerTest {
    private static final String allGenreUrl = "http://localhost:8080/chinook/allgenres";
    private static final List<String> genreList = Arrays.asList("Rock", "Jazz","Metal", "Alternative & Punk", "Rock And Roll",
            "Blues", "Latin", "Reggae","Pop", "Soundtrack", "Bossa Nova","Easy Listening"
            ,"Heavy Metal", "R&B/Soul", "Electronica/Dance", "World", "Hip Hop/Rap",
            "Science Fiction", "TV Shows", "Sci Fi & Fantasy", "Drama", "Comedy",
            "Alternative", "Classical", "Opera");

    @ParameterizedTest
    @ValueSource(strings = {allGenreUrl})
    @DisplayName("check status 200 for all genres url")
    void check200Code(String s){
        HttpResponse<String> httpResponse = null;
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(s)).build();
        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(httpResponse.statusCode(), 200);
    }

    @Test
    @DisplayName("Check all genre match list")
    public void checkAllGenreMatch(){
        ObjectMapper objectMapper = new ObjectMapper();
        Genre[] genre;
        try {
            genre = objectMapper.readValue(new URL("http://localhost:8080/chinook/allgenres"), Genre[].class);
            for(Genre g : genre){
                assert(genreList.contains(g));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
