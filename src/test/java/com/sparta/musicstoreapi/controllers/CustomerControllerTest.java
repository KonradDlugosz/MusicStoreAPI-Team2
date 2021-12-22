package com.sparta.musicstoreapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;




class CustomerControllerTest {

    private static final String GET_ALL_CUSTOMERS = "http://localhost:8080/chinook/allcustomer";
    private static final String GET_CUSTOMERS_BY_ID = "http://localhost:8080/chinook/customer/1";
    private static final String GET_CUSTOMER_BY_EMAIL = "http://localhost:8080/chinook/customer/email?email=luisg@embraer.com.br";
    private static final String POST_CUSTOMER = "http://localhost:8080/chinook/customer/add";
    private static final String PUT_CUSTOMER = "http://localhost:8080/chinook/customer/update/59";


    private static HttpResponse<String> getAllCustomersResponse = null;
    private static HttpResponse<String> getOneCustomerResponse = null;
    private static Customer getOneCustomerResponseJSON = null;
    private static HttpResponse<String> searchByEmailResponse = null;
    private static HttpResponse<String> postRequest = null;

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

    public static Customer getObjectMapper(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        Customer customer = null;
        try {
            customer = mapper.readValue(json, Customer.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return customer;
    }


    public static HttpResponse<String> postRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                            "id" : 60,
                            "firstName": "Nikos",
                            "lastName": "Pap",
                            "company": "Sparta Global",
                            "address": "16 Pauline House",
                            "city": "London",
                            "state": "LN",
                            "country": "England",
                            "postalCode": "E1 5NU",
                            "phone": "+44  3956-4444",
                            "fax": "+44  3923-4444",
                            "email": "npap@hotmail.com",
                            "supportRepId": {
                                     "id": 3,
                                     "lastName": "Peacock",
                                     "firstName": "Jane",
                                     "title": "Sales Support Agent",
                                     "reportsTo": {
                                        "id": 2,
                                        "lastName": "Edwards",
                                        "firstName": "Nancy",
                                        "title": "Sales Manager",
                                        "reportsTo": {
                                        "id": 1,
                                        "lastName": "Adams",
                                        "firstName": "Andrew",
                                        "title": "General Manager",
                                        "reportsTo": null,
                                        "birthDate": "1962-02-18T00:00:00Z",
                                        "hireDate": "2002-08-13T23:00:00Z",
                                        "address": "11120 Jasper Ave NW",
                                        "city": "Edmonton",
                                        "state": "AB",
                                        "country": "Canada",
                                        "postalCode": "T5K 2N1",
                                        "phone": "+1 (780) 428-9482",
                                        "fax": "+1 (780) 428-3457",
                                        "email": "andrew@chinookcorp.com"
                                        },
                                        "birthDate": "1958-12-08T00:00:00Z",
                                        "hireDate": "2002-04-30T23:00:00Z",
                                        "address": "825 8 Ave SW",
                                        "city": "Calgary",
                                        "state": "AB",
                                        "country": "Canada",
                                        "postalCode": "T2P 2T3",
                                        "phone": "+1 (403) 262-3443",
                                        "fax": "+1 (403) 262-3322",
                                        "email": "nancy@chinookcorp.com"
                                        },
                            "birthDate": "1988-04-28T23:00:00Z",
                            "hireDate": "2004-03-31T23:00:00Z",
                            "address": "1111 6 Ave SW",
                            "city": "Calgary",
                            "state": "AB",
                            "country": "Canada",
                            "postalCode": "T2P 5M5",
                            "phone": "+1 (403) 262-3443",
                            "fax": "+1 (403) 262-6712",
                            "email": "jane@chinookcorp.com"
                           }
                        }
                        """))
                .build();
        return getResponse(request);
    }

//    @Test
//    @DisplayName("Given all customers, return 200 status")
//    public void


}