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
    private static final String GET_ALL_DISCONTINUED = "http://localhost:8080/chinook/tracks/discontinued";
    private static final String GET_DISCONTINUED_BY_TRACK_ID = "http://localhost:8080/chinook/tracks/1";
    private static final String POST_DISCONTINUED_BY_TRACK_ID = "http://localhost:8080/chinook/track/discontinue/260";
    private static final String PUT_DISCONTINUED_BY_TRACK_ID = "http://localhost:8080/chinook/track/discontinue/update/";
    private static final String DELETE_DISCONTINUED_BY_TRACK_ID = "http://localhost:8080/chinook/track/discontinued/delete/1";

    private static HttpResponse<String> getAllDiscontinuedResponse = null;
    private static HttpResponse<String> getDiscontinuedByTrackIdResponse = null;
    private static HttpResponse<String> postDiscontinuedByTrackIdResponse = null;
    private static HttpResponse<String> putDiscontinuedByTrackIdResponse = null;
    private static HttpResponse<String> deleteDiscontinuedByTrackIdResponse = null;

    @BeforeAll
    public static void getConnections(){
        getAllDiscontinuedResponse = getRequest(GET_ALL_DISCONTINUED);
        getDiscontinuedByTrackIdResponse = getRequest(GET_DISCONTINUED_BY_TRACK_ID);
        postDiscontinuedByTrackIdResponse = discontinuedPostRequest(POST_DISCONTINUED_BY_TRACK_ID);
        putDiscontinuedByTrackIdResponse = discontinuedPutRequest(PUT_DISCONTINUED_BY_TRACK_ID);
        deleteDiscontinuedByTrackIdResponse = deleteRequest(DELETE_DISCONTINUED_BY_TRACK_ID);
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

    private static HttpResponse<String> discontinuedPutRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString("""
                            {
                                "isDiscontinued": false
                            }
                        """))
                .header("Content-Type", "application/json")
                .build();
        return getResponse(request);
    }

    private static HttpResponse<String> deleteRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        return getResponse(request);
    }


    @Test
    @DisplayName("1.1 Given GET ALL, return all discontinued")
    public void getAllDiscontinuedTest(){
        Assertions.assertEquals(200, getAllDiscontinuedResponse.statusCode() );
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

//    @Test
//    @DisplayName("1.4 Given PUT by Track ID, update discontinued boolean")
//    public void putDiscontinuedByIdTest(){
//        Assertions.assertEquals(200, putDiscontinuedByTrackIdResponse.statusCode() );
//    }
//
//    @Test
//    @DisplayName("1.5 Given DELETE by Track ID, delete row")
//    public void deleteDiscontinuedByTrackIdTest(){
//        Assertions.assertEquals(200, deleteDiscontinuedByTrackIdResponse.statusCode() );
//    }

}
