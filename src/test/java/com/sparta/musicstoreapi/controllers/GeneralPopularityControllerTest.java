package com.sparta.musicstoreapi.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeneralPopularityControllerTest {
    private static final String GET_TOP_FIVE_TRACKS = "http://localhost:8080/chinook/tracks/top5/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";
    private static final String GET_TOP_FIVE_ALBUMS = "http://localhost:8080/chinook/albums/top5/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";
    private static final String GET_TOP_FIVE_GENRES = "http://localhost:8080/chinook/genres/top5/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";
    private static final String GET_TOP_FIVE_ARTISTS = "http://localhost:8080/chinook/artists/top5/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";

    private static HttpResponse<String> getTopFiveTracksResponse = null;
    private static HttpResponse<String> getTopFiveAlbumsResponse = null;
    private static HttpResponse<String> getTopFiveGenresResponse = null;
    private static HttpResponse<String> getTopFiveArtistResponse = null;

    @BeforeAll
    public static void getConnections() {
        getTopFiveTracksResponse = getRequest(GET_TOP_FIVE_TRACKS);
        getTopFiveAlbumsResponse = getRequest(GET_TOP_FIVE_ALBUMS);
        getTopFiveGenresResponse = getRequest(GET_TOP_FIVE_GENRES);
        getTopFiveArtistResponse = getRequest(GET_TOP_FIVE_ARTISTS);
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

    @Test
    @DisplayName("1.1 Given TOP 5 Tracks, return 200 status")
    public void getTopFiveTracksTest(){
        Assertions.assertEquals(200, getTopFiveTracksResponse.statusCode());
    }

    @Test
    @DisplayName("1.2 Given TOP 5 Albums, return 200 status")
    public void getTopFiveAlbumsTest(){
        Assertions.assertEquals(200, getTopFiveAlbumsResponse.statusCode());
    }

    @Test
    @DisplayName("1.3 Given TOP 5 Genres, return 200 status")
    public void getTopFiveGenresTest(){
        Assertions.assertEquals(200, getTopFiveGenresResponse.statusCode());
    }

    @Test
    @DisplayName("1.4 Given TOP 5 Artists, return 200 status")
    public void setGetTopFiveArtists(){
        Assertions.assertEquals(200, getTopFiveArtistResponse.statusCode());
    }

}
