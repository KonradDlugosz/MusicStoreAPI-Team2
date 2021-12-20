package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Customer;
import org.hibernate.annotations.Source;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class CustomerControllerTest {
    private static final String allCustomerUrl = "http://localhost:8080/chinook/allcustomer";

    @ParameterizedTest
    @ValueSource(strings = {allCustomerUrl})
    @DisplayName("check status 200 for all customer url")
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

    @Test
    void findAllCustomer() {
    }

    @Test
    @DisplayName("Check id 1")
    void findCustomerById() throws IOException, JSONException {
    }

    @Test
    void addCustomer() {
    }

    @Test
    void updateCustomerById() {
    }
}