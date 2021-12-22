package com.sparta.musicstoreapi.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class InvoicelineControllerTest {

    private static final String GET_ALL_INVOICELINES = "http://localhost:8080/chinook/invoicelines";
    private static final String GET_INVOICELINE_BY_ID = "http://localhost:8080/chinook/invoiceline?id=11";
    private static final String GET_INVOICELINE_BY_INVOICE_ID = "http://localhost:8080/chinook/invoiceline/1";
    private static final String POST_INVOICELINE_TRACK =  "http://localhost:8080/chinook/invoiceline/track/add?customerId=1&trackId=10";
    private static final String PUT_INVOICELINE = "http://localhost:8080/chinook/invoiceline/update";
    private static final String POST_INVOICELINE_ALBUM = "http://localhost:8080/chinook/invoiceline/album/add?customerId=11&albumId=1";
    private static final String POST_INVOICELINE_PLAYLIST = "http://localhost:8080/chinook/invoiceline/playlist/add?playlistId=12&customerId=8";
    private static final String DELETE_INVOICELINE = "http://localhost:8080/chinook/invoiceline/delete/2292";


    private static HttpResponse<String> getAllInvoicelinesResponse = null;
    private static HttpResponse<String> getOneInvoicelineResponse = null;

    private static HttpResponse<String> getInvoicelineByInvoiceIdResponse = null;
//    private static Invoiceline getOneInvoicelineResponseJSON = null;
    private static HttpResponse<String> postInvoicelineResponse = null;
    private static HttpResponse<String> putInvoicelineResponse = null;
    private static HttpResponse<String> postInvoicelineAlbum = null;
    private static HttpResponse<String> postInvoicelinePlaylist = null;
    private static HttpResponse<String> deleteInvoiceline = null;

    @BeforeAll
    public static void getConnections(){
        getAllInvoicelinesResponse = getRequest(GET_ALL_INVOICELINES);
        getOneInvoicelineResponse = getRequest(GET_INVOICELINE_BY_ID);
        getInvoicelineByInvoiceIdResponse = getRequest(GET_INVOICELINE_BY_INVOICE_ID);
//        getOneInvoicelineResponseJSON = getObjectMapper(getRequest(GET_INVOICELINE_BY_ID).body());
        postInvoicelineResponse = postTrackRequest(POST_INVOICELINE_TRACK);
        putInvoicelineResponse = putRequest(PUT_INVOICELINE);
        postInvoicelineAlbum = postAlbumRequest(POST_INVOICELINE_ALBUM);
        postInvoicelinePlaylist = postPlaylistRequest(POST_INVOICELINE_PLAYLIST);
        deleteInvoiceline = deleteRequest(DELETE_INVOICELINE);
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

    private static HttpResponse<String> deleteRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        return getResponse(request);
    }


//    public static Invoiceline getObjectMapper(String json) {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.findAndRegisterModules();
//        Invoiceline invoiceline = null;
//        try {
//            invoiceline = mapper.readValue(json, Invoiceline.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return invoiceline;
//    }

    public static HttpResponse<String> postTrackRequest(String url){
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                            "invoiceId": 3,
                            "trackId": 30,
                            "unitPrice": 0.99,
                            "quantity": 1
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
                            "id":20,
                            "invoiceId": 3,
                            "trackId": 30,
                            "unitPrice": 0.99,
                            "quantity": 1
                        }
                        """))
                .build();
        return getResponse(request);
    }

    public static HttpResponse<String> postAlbumRequest(String url){
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
        return getResponse(request);
    }

    public static HttpResponse<String> postPlaylistRequest(String url){
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
        return getResponse(request);
    }


    @Test
    @DisplayName("1 Given all invoicelines request, return 200 status")
    public void getAllInvoicesStatusCode(){
        Assertions.assertEquals(200, getAllInvoicelinesResponse.statusCode());
    }

    @Test
    @DisplayName("2 Given Invoiceline ID, return 200 status")
    public void getOneInvoicelineStatusCode(){
        Assertions.assertEquals(200, getOneInvoicelineResponse.statusCode());
    }

    @Test
    @DisplayName("3 Given invoice ID, return 200 status")
    public void getOneInvoiceStatusCode(){
        Assertions.assertEquals(200, getInvoicelineByInvoiceIdResponse.statusCode());
    }

    @Test
    @DisplayName("4 Given new invoiceline for track, return 200 status")
    public void postInvoiceStatusCheck(){
        Assertions.assertEquals(200, postInvoicelineResponse.statusCode());
    }

    @Test
    @DisplayName("5 Given new invoiceline for album, return 200 status")
    public void postInvoiceAlbumStatusCheck(){
        Assertions.assertEquals(200, postInvoicelineAlbum.statusCode());
    }

    @Test
    @DisplayName("6 Given new invoiceline for playlist, return 200 status")
    public void postInvoicePlaylistStatusCheck(){
        Assertions.assertEquals(200, postInvoicelinePlaylist.statusCode());
    }


    @Test
    @DisplayName("7 Given invoiceline update, return 200 status")
    public void putInvoiceStatusCheck(){
        Assertions.assertEquals(200, putInvoicelineResponse.statusCode());
    }

    @Test
    @DisplayName("8 Given delete invoiceline, return 200 status")
    public void deleteInvoiceLineStatusCheck(){
        Assertions.assertEquals(200, deleteInvoiceline.statusCode());
    }
}
