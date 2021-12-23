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

public class DiscontinuedControllerTest {
    private static final String GET_ALL_DISCONTINUED_ENTRIES = "http://localhost:8080/chinook/tracks/discontinued/everything";
    private static final String GET_ALL_DISCONTINUED_WHERE_TRUE = "http://localhost:8080/chinook/tracks/discontinued/true";
    private static final String GET_ALL_DISCONTINUED_WHERE_FALSE = "http://localhost:8080/chinook/tracks/discontinued/false";
    private static final String GET_DISCONTINUED_BY_TRACK_ID = "http://localhost:8080/chinook/tracks/1";
    private static final String POST_DISCONTINUED_BY_TRACK_ID = "http://localhost:8080/chinook/track/discontinue/10/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";


    private static HttpResponse<String> getAllDiscontinuedEntriesResponse = null;
    private static HttpResponse<String> getAllDiscontinuedTrueResponse = null;
    private static HttpResponse<String> getAllDiscontinuedFalseResponse = null;
    private static HttpResponse<String> getDiscontinuedByTrackIdResponse = null;
    private static HttpResponse<String> postDiscontinuedByTrackIdResponse = null;


    @BeforeAll
    public static void getConnections(){
        getAllDiscontinuedEntriesResponse = getRequest(GET_ALL_DISCONTINUED_ENTRIES);
        getAllDiscontinuedTrueResponse = getRequest(GET_ALL_DISCONTINUED_WHERE_TRUE);
        getAllDiscontinuedFalseResponse = getRequest(GET_ALL_DISCONTINUED_WHERE_FALSE);
        getDiscontinuedByTrackIdResponse = getRequest(GET_DISCONTINUED_BY_TRACK_ID);
        postDiscontinuedByTrackIdResponse = discontinuedPostRequest(POST_DISCONTINUED_BY_TRACK_ID);
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

    private static HttpResponse<String> discontinuedPostRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString("""
                            {
                                "isDiscontinued": true
                            }
                        """))
                .header("Content-Type", "application/json")
                .build();
        return getResponse(request);
    }

    @Test
    @DisplayName("1.1 Given GET ALL, return all discontinued entries")
    public void getAllDiscontinuedTest(){
        Assertions.assertEquals(200, getAllDiscontinuedEntriesResponse.statusCode() );
    }

    @Test
    @DisplayName("1.2 Given GET by ID, return discontinued track")
    public void getDiscontinuedByIdTest(){
        Assertions.assertEquals(200, getDiscontinuedByTrackIdResponse.statusCode() );
    }

    @Test
    @DisplayName("1.3 Given POST by Track ID, add discontinued to track")
    public void postDiscontinuedByIdTest(){
        Assertions.assertEquals(200, postDiscontinuedByTrackIdResponse.statusCode() );
    }

    @Test
    @DisplayName("1.4 Given GET ALL, return tracks where isDiscontinued = true")
    public void putDiscontinuedByIdTest(){
        Assertions.assertEquals(200, getAllDiscontinuedTrueResponse.statusCode() );
    }

    @Test
    @DisplayName("1.5 Given GET ALL, return tracks where isDiscontinued = false")
    public void deleteDiscontinuedByTrackIdTest(){
        Assertions.assertEquals(200, getAllDiscontinuedFalseResponse.statusCode() );
    }

}
