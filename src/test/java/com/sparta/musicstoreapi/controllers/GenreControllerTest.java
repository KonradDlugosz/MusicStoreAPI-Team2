package com.sparta.musicstoreapi.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class GenreControllerTest {
    private static final String allGenreUrl = "http://localhost:8080/chinook/allgenres";

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
}