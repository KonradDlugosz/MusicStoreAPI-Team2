package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Mediatype;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MediatypeControllerTest {

    private static final String GET_ALL_MEDIATYPES = "http://localhost:8080/chinook/allmediatypes";
    private static final String GET_MEDIATYPE_BY_ID = "http://localhost:8080/chinook/mediatypes/1";
    private static final String POST_MEDIATYPE = "http://localhost:8080/chinook/mediatype/add";
    private static final String PUT_MEDIATYPE = "http://localhost:8080/chinook/mediatype/update";

    private static HttpResponse<String> getAllMediatypesResponse = null;
    private static HttpResponse<String> getOneMediatypeResponse = null;
    private static Mediatype getOneMediatypeResponseJSON = null;
    private static HttpResponse<String> searchByNameResponse = null;
    private static HttpResponse<String> postMediatypeResponse = null;
    private static HttpResponse<String> putMediatypeResponse = null;

    @BeforeAll
    public static void getConnections() {
        getAllMediatypesResponse = getRequest(GET_ALL_MEDIATYPES);
        getOneMediatypeResponse = getRequest(GET_MEDIATYPE_BY_ID);
        getOneMediatypeResponseJSON = getObjectMapper(getRequest(GET_MEDIATYPE_BY_ID).body());
        postMediatypeResponse = postRequest(POST_MEDIATYPE);
        putMediatypeResponse = putRequest(PUT_MEDIATYPE);
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

    public static Mediatype getObjectMapper(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Mediatype mediatype = null;
        try {
            mediatype = mapper.readValue(json, Mediatype.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return mediatype;
    }

    public static HttpResponse<String> postRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                            "name": "New media type"
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
                            "id": 6,
                            "name": "New updated media type"
                        }
                                                
                        """))
                .build();
        return getResponse(request);
    }


    @Test
    @DisplayName("Given mediatype ID, return 200 status")
    public void getOneMediatypeStatusCodeTest(){
        Assertions.assertEquals(200, getOneMediatypeResponse.statusCode());
    }

    @Test
    @DisplayName("Given mediatype ID, return mediatype name")
    public void getMediatypeNameTest(){
        Assertions.assertEquals("MPEG audio file", getOneMediatypeResponseJSON.getName());
    }

    @Test
    @DisplayName("Given mediatype ID, return mediatype id")
    public void getMediatypeIdTest(){
        Assertions.assertEquals(1, getOneMediatypeResponseJSON.getId());
    }


    @Test
    @DisplayName("Given new mediatype, return 200 status")
    public void postMediatypeStatusCheckTest(){
        Assertions.assertEquals(200, postMediatypeResponse.statusCode());
    }

    @Test
    @DisplayName("Given mediatype update, return 200 status")
    public void putMediaTypeStatusCheckTest(){
        Assertions.assertEquals(200, putMediatypeResponse.statusCode());
    }


}
