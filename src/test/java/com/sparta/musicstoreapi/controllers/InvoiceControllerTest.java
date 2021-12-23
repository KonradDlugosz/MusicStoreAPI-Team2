package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Invoice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class InvoiceControllerTest {

    private static final String GET_ALL_INVOICES = "http://localhost:8080/chinook/invoices/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";
    private static final String GET_INVOICE_BY_ID = "http://localhost:8080/chinook/invoice/11/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";
    private static final String POST_INVOICE =  "http://localhost:8080/chinook/invoice/add/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";
    private static final String PUT_INVOICE = "http://localhost:8080/chinook/invoice/update/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";

    private static HttpResponse<String> getAllInvoicesResponse = null;
    private static HttpResponse<String> getOneInvoiceResponse = null;
    private static Invoice getOneInvoiceResponseJSON = null;
    private static HttpResponse<String> postInvoiceResponse = null;
    private static HttpResponse<String> putInvoiceResponse = null;

    @BeforeAll
    public static void getConnections(){
        getAllInvoicesResponse = getRequest(GET_ALL_INVOICES);
        getOneInvoiceResponse = getRequest(GET_INVOICE_BY_ID);
        getOneInvoiceResponseJSON = getObjectMapper(getRequest(GET_INVOICE_BY_ID).body());
        postInvoiceResponse = postRequest(POST_INVOICE);
        putInvoiceResponse = putRequest(PUT_INVOICE);
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

    public static Invoice getObjectMapper(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        Invoice invoice = null;
        try {
            invoice = mapper.readValue(json, Invoice.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return invoice;
    }

    public static HttpResponse<String> postRequest(String url){
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                               "customerId": 2,
                               "invoiceDate": "2009-02-03T00:00:00Z",
                               "billingAddress": "3 Chatham Street",
                               "billingCity": "Dublin",
                               "billingState": "Dublin",
                               "billingCountry": "Ireland",
                               "billingPostalCode": null,
                               "total": 5.94
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
                            "id": 11,
                            "customerId": 3,
                            "invoiceDate": "2009-02-03T00:00:00Z",
                            "billingAddress": "4 Chatham Street",
                            "billingCity": "Dublin",
                            "billingState": "Dublin",
                            "billingCountry": "Ireland",
                            "billingPostalCode": null,
                            "total": 5.94
                        }
                        """))
                .build();
        return getResponse(request);
    }

    @Test
    @DisplayName("1 Given all invoices request, return 200 status")
    public void getAllInvoicesStatusCode(){
        Assertions.assertEquals(200, getAllInvoicesResponse.statusCode());
    }

    @Test
    @DisplayName("2 Given Invoice ID, return 200 status")
    public void getOneInvoiceStatusCode(){
        Assertions.assertEquals(200, getOneInvoiceResponse.statusCode());
    }

    @Test
    @DisplayName("3 Given new invoice, return 200 status")
    public void postInvoiceStatusCheck(){
        Assertions.assertEquals(200, postInvoiceResponse.statusCode());
    }

    @Test
    @DisplayName("4 Given invoice update, return 200 status")
    public void putInvoiceStatusCheck(){
        Assertions.assertEquals(200, putInvoiceResponse.statusCode());
    }

}
