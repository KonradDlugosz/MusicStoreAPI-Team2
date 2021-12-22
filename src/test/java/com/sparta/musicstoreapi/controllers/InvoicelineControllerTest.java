package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Invoiceline;

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
    private static final String POST_INVOICELINE =  "http://localhost:8080/chinook/invoiceline/add";
    private static final String PUT_INVOICELINE = "http://localhost:8080/chinook/invoiceline/update";

    private static HttpResponse<String> getAllInvoicelinesResponse = null;
    private static HttpResponse<String> getOneInvoicelineResponse = null;
    private static Invoiceline getOneInvoicelineResponseJSON = null;
    private static HttpResponse<String> postInvoicelineResponse = null;
    private static HttpResponse<String> putInvoicelineResponse = null;

    @BeforeAll
    public static void getConnections(){
        getAllInvoicelinesResponse = getRequest(GET_ALL_INVOICELINES);
        getOneInvoicelineResponse = getRequest(GET_INVOICELINE_BY_ID);
 //       getOneInvoicelineResponseJSON = getObjectMapper(getRequest(GET_INVOICELINE_BY_ID).body());
        postInvoicelineResponse = postRequest(POST_INVOICELINE);
        putInvoicelineResponse = putRequest(PUT_INVOICELINE);
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

    public static HttpResponse<String> postRequest(String url){
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

    @Test
    @DisplayName("1 Given all invoicelines request, return 200 status")
    public void getAllInvoicesStatusCode(){
        Assertions.assertEquals(200, getAllInvoicelinesResponse.statusCode());
    }

    @Test
    @DisplayName("2 Given Invoiceline ID, return 200 status")
    public void getOneInvoiceStatusCode(){
        Assertions.assertEquals(200, getOneInvoicelineResponse.statusCode());
    }

    @Test
    @DisplayName("3 Given new invoiceline, return 200 status")
    public void postInvoiceStatusCheck(){
        Assertions.assertEquals(200, postInvoicelineResponse.statusCode());
    }

    @Test
    @DisplayName("4 Given invoiceline update, return 200 status")
    public void putInvoiceStatusCheck(){
        Assertions.assertEquals(200, putInvoicelineResponse.statusCode());
    }
}
