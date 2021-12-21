package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
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
    void findCustomerById() throws Exception {
    }

    @Test
    void addCustomer() {
    }


    @Test
    @DisplayName("Check customer id 1 is Luis")
    public void checkCustomerIdOneIsLuis(){
        ObjectMapper objectMapper = new ObjectMapper();
        Customer customer;
        try {
            customer = objectMapper.readValue(new URL("http://localhost:8080/customer/1"), Customer.class);
            assertEquals("Luis", customer.getFirstName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}