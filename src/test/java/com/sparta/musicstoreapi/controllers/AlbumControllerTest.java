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

public class AlbumControllerTest {
    private static final String GET_ALL_ALBUMS = "http://localhost:8080/chinook/albums";
    private static final String GET_ALBUM_BY_ID = "http://localhost:8080/chinook/album/1";
    private static final String POST_ALBUM = "http://localhost:8080/chinook/album/insert";
    private static final String PUT_ALBUM = "http://localhost:8080/chinook/album/update";

    private static HttpResponse<String> getAllAlbumResponse = null;
    private static HttpResponse<String> getAlbumByIdResponse = null;
    private static HttpResponse<String> postAlbumResponse = null;
    private static HttpResponse<String> putAlbumResponse = null;

    @BeforeAll
    public static void getConnections(){
        getAllAlbumResponse = getRequest(GET_ALL_ALBUMS);
        getAlbumByIdResponse = getRequest(GET_ALBUM_BY_ID);
        postAlbumResponse = albumPostRequest(POST_ALBUM);
        putAlbumResponse = albumPutRequest(PUT_ALBUM);
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

    private static HttpResponse<String> albumPostRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString("""
                            {
                                "id": 349,
                                "title": "TEST ALBUM",
                                "artistId": {
                                        "name": "AC/DC"
                                }
                            }
                        """))
                .header("Content-Type", "application/json")
                .build();
        return getResponse(request);
    }

    private static HttpResponse<String> albumPutRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString("""
                            {
                                "id": 349,
                                "title": "TEST ALBUM",
                                "artistId": {
                                        "name": "Aerosmith"
                                }
                            }
                        """))
                .header("Content-Type", "application/json")
                .build();
        return getResponse(request);
    }

    @Test
    @DisplayName("1.1 Given GET ALL, return all albums")
    public void getAllAlbumTests(){
        Assertions.assertEquals(200, getAllAlbumResponse.statusCode() );
    }

    @Test
    @DisplayName("1.2 Given GET by ID, return album")
    public void getAlbumById(){
        Assertions.assertEquals(200, getAlbumByIdResponse.statusCode() );
    }

    @Test
    @DisplayName("1.3 Given POST request, return added album")
    public void postAlbum(){
        Assertions.assertEquals(200, postAlbumResponse.statusCode() );
    }

    @Test
    @DisplayName("1.3 Given PUT request, return update album")
    public void putAlbum(){
        Assertions.assertEquals(200, putAlbumResponse.statusCode() );
    }
}
