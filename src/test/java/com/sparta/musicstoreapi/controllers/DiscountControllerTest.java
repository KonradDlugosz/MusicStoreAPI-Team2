package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Albumdiscount;
import com.sparta.musicstoreapi.entities.Track;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DiscountControllerTest {

    private static final String GET_ALL_ALBUM_DISCOUNTS = "http://localhost:8080/chinook/album-discount";
    private static final String GET_ALBUM_DISCOUNT_BY_ID = "http://localhost:8080/chinook/album-discount/2";
    private static final String POST_ALBUM_DISCOUNT = "http://localhost:8080/chinook/album-discount/add";
    private static final String PUT_ALBUM_DISCOUNT = "http://localhost:8080/chinook/album-discount/update";
    private static final String DELETE_ALBUM_DISCOUNT = "http://localhost:8080/chinook/album-discount/delete/1";

    private static final String GET_ALL_TRACK_DISCOUNTS = "http://localhost:8080/chinook/tracks-discount";
    private static final String GET_TRACK_DISCOUNT_BY_ID = "http://localhost:8080/chinook/tracks-discount/2";
    private static final String POST_TRACK_DISCOUNT = "http://localhost:8080/chinook/tracks-discount/add";
    private static final String PUT_TRACK_DISCOUNT = "http://localhost:8080/chinook/tracks-discount/update";
    private static final String DELETE_TRACK_DISCOUNT = "http://localhost:8080/chinook/tracks-discount/delete/1";

    private static final String GET_ALL_PLAYLISTS_DISCOUNTS = "http://localhost:8080/chinook/playlists-discount";
    private static final String GET_PLAYLIST_DISCOUNT_BY_ID = "http://localhost:8080/chinook/playlists-discount/2";
    private static final String POST_PLAYLIST_DISCOUNT = "http://localhost:8080/chinook/playlists-discount/add";
    private static final String PUT_PLAYLIST_DISCOUNT = "http://localhost:8080/chinook/playlists-discount/update";
    private static final String DELETE_PLAYLIST_DISCOUNT = "http://localhost:8080/chinook/playlists-discount/delete/1";

    private static HttpResponse<String> getAllAlbumDiscountResponse = null;
    private static HttpResponse<String> getAlbumDiscountByIdResponse = null;
    private static HttpResponse<String> postAlbumDiscountResponse = null;
    private static HttpResponse<String> putAlbumDiscountResponse = null;
    private static HttpResponse<String> deleteAlbumDiscountResponse = null;
    private static HttpResponse<String> getAllDiscountResponse = null;
    private static HttpResponse<String> getTracksDiscountByIdResponse = null;
    private static HttpResponse<String> postTrackDiscountResponse = null;
    private static HttpResponse<String> putTrackDiscountResponse = null;
    private static HttpResponse<String> deleteTrackDiscountResponse = null;
    private static HttpResponse<String> getAllPlaylistsDiscountResponse = null;
    private static HttpResponse<String> getPlaylistsDiscountByIdResponse = null;
    private static HttpResponse<String> postPlaylistsDiscountResponse = null;
    private static HttpResponse<String> putPlaylistsDiscountResponse = null;
    private static HttpResponse<String> deletePlaylistsDiscountResponse = null;


    @BeforeAll
    public static void getConnections(){
        // ALBUM Discount
        getAllAlbumDiscountResponse = getRequest(GET_ALL_ALBUM_DISCOUNTS);
        getAlbumDiscountByIdResponse = getRequest(GET_ALBUM_DISCOUNT_BY_ID);
        postAlbumDiscountResponse = albumPostRequest(POST_ALBUM_DISCOUNT);
        putAlbumDiscountResponse = albumPutRequest(PUT_ALBUM_DISCOUNT);
        deleteAlbumDiscountResponse = deleteRequest(DELETE_ALBUM_DISCOUNT);
        // TRACK Discount
        getAllDiscountResponse = getRequest(GET_ALL_TRACK_DISCOUNTS);
        getTracksDiscountByIdResponse = getRequest(GET_TRACK_DISCOUNT_BY_ID);
        postTrackDiscountResponse = trackPostRequest(POST_TRACK_DISCOUNT);
        putTrackDiscountResponse = trackPutRequest(PUT_TRACK_DISCOUNT);
        deleteTrackDiscountResponse = deleteRequest(DELETE_TRACK_DISCOUNT);
        // PLAYLIST Discount
        getAllPlaylistsDiscountResponse = getRequest(GET_ALL_PLAYLISTS_DISCOUNTS);
        getPlaylistsDiscountByIdResponse = getRequest(GET_PLAYLIST_DISCOUNT_BY_ID);
        postPlaylistsDiscountResponse = playlistPostRequest(POST_PLAYLIST_DISCOUNT);
        putPlaylistsDiscountResponse = playlistPutRequest(PUT_PLAYLIST_DISCOUNT);
        deletePlaylistsDiscountResponse = deleteRequest(DELETE_PLAYLIST_DISCOUNT);
    }

    /**
     * Base methods to get HttpResponse and deserialize JSON to Java Objects POJO
     * @param request -> Http request with headers, method like GET/POST/DELETE etc.
     * @return response for discount request
     */

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

    private static HttpResponse<String> deleteRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        return getResponse(request);
    }

    private static HttpResponse<String> albumPostRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString("""
                            {
                                "albumId": 2,
                                "expiryDate": "2022-11-11",
                                "amount": 0.30
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
                                "id":2,
                                "albumId": 2,
                                "expiryDate": "2022-09-11",
                                "amount": 0.30
                            }
                        """))
                .header("Content-Type", "application/json")
                .build();
        return getResponse(request);
    }

    private static HttpResponse<String> trackPostRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString("""
                            {
                                "trackId": 2,
                                "expiryDate": "2022-11-11",
                                "amount": 0.30
                            }
                        """))
                .header("Content-Type", "application/json")
                .build();
        return getResponse(request);
    }

    private static HttpResponse<String> trackPutRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString("""
                            {
                                "id": 2,
                                "trackId": 2,
                                "expiryDate": "2022-11-11",
                                "amount": 0.25
                            }
                        """))
                .header("Content-Type", "application/json")
                .build();
        return getResponse(request);
    }

    private static HttpResponse<String> playlistPostRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString("""
                            {
                                "playListId": 1,
                                "expiryDate": "2022-11-11",
                                "amount": 0.30
                            }
                        """))
                .header("Content-Type", "application/json")
                .build();
        return getResponse(request);
    }

    private static HttpResponse<String> playlistPutRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString("""
                            {
                                "id": 2,
                                "playListId": 2,
                                "expiryDate": "2022-11-11",
                                "amount": 0.20
                            }
                        """))
                .header("Content-Type", "application/json")
                .build();
        return getResponse(request);
    }


    public static Albumdiscount getObjectMapper(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Albumdiscount albumdiscount = null;
        try {
            albumdiscount = mapper.readValue(json, Albumdiscount.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return albumdiscount;
    }


    /**
     * ALBUM DISCOUNT TESTS
     */

    @Test
    @DisplayName("1.1 Given GET ALL, return all discounts")
    public void getAllAlbumDiscounts(){
        Assertions.assertEquals(200,getAllAlbumDiscountResponse.statusCode() );
    }

    @Test
    @DisplayName("1.2 Given GET by ID, return album discount")
    public void getAlbumDiscountById(){
        Assertions.assertEquals(200,getAlbumDiscountByIdResponse.statusCode() );
    }

    @Test
    @DisplayName("1.3 Given POST request, return added discount")
    public void postAlbumDiscount(){
        Assertions.assertEquals(200,postAlbumDiscountResponse.statusCode() );
    }

    @Test
    @DisplayName("1.4 Given PUT request, return updated discount")
    public void putAlbumDiscount(){
        Assertions.assertEquals(200,putAlbumDiscountResponse.statusCode() );
    }

    @Test
    @DisplayName("1.5 Given DELETE request, return deleted result")
    public void deleteAlbumDiscount(){
        Assertions.assertEquals(200,deleteAlbumDiscountResponse.statusCode() );
    }

    /**
     * TRACK DISCOUNT TESTS
     */

    @Test
    @DisplayName("2.1 Given GET ALL, return all discounts")
    public void getAllTrackDiscounts(){
        Assertions.assertEquals(200,getAllDiscountResponse.statusCode() );
    }

    @Test
    @DisplayName("2.2 Given GET by ID, return track discount")
    public void getTrackDiscountById(){
        Assertions.assertEquals(200,getTracksDiscountByIdResponse.statusCode() );
    }

    @Test
    @DisplayName("2.3 Given POST request, return added discount")
    public void postTrackDiscount(){
        Assertions.assertEquals(200,postTrackDiscountResponse.statusCode() );
    }

    @Test
    @DisplayName("2.4 Given PUT request, return updated discount")
    public void putTrackDiscount(){
        Assertions.assertEquals(200,putTrackDiscountResponse.statusCode() );
    }

    @Test
    @DisplayName("2.5 Given DELETE request, return deleted result")
    public void deleteTrackDiscount(){
        Assertions.assertEquals(200,deleteTrackDiscountResponse.statusCode() );
    }

    /**
     * PLAYLIST DISCOUNT TESTS
     */

    @Test
    @DisplayName("3.1 Given GET ALL, return all discounts")
    public void getAllPlaylistDiscounts(){
        Assertions.assertEquals(200,getAllPlaylistsDiscountResponse.statusCode() );
    }

    @Test
    @DisplayName("3.2 Given GET by ID, return playlist discount")
    public void getPlaylistDiscountById(){
        Assertions.assertEquals(200,getPlaylistsDiscountByIdResponse.statusCode() );
    }

    @Test
    @DisplayName("3.3 Given POST request, return added discount")
    public void postPlaylistDiscount(){
        Assertions.assertEquals(200,postPlaylistsDiscountResponse.statusCode() );
    }

    @Test
    @DisplayName("3.4 Given PUT request, return updated discount")
    public void putPlaylistDiscount(){
        Assertions.assertEquals(200,putPlaylistsDiscountResponse.statusCode() );
    }

    @Test
    @DisplayName("3.5 Given DELETE request, return deleted result")
    public void deletePlaylistDiscount(){
        Assertions.assertEquals(200,deletePlaylistsDiscountResponse.statusCode() );
    }




}
