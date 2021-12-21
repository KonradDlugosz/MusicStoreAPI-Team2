package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {
    private static final String allCustomerUrl = "http://localhost:8080/chinook/allcustomer";
    private static final String customerIdOneJson = "{\n" +
            "    \"id\": 10,\n" +
            "    \"firstName\": \"Eduardo\",\n" +
            "    \"lastName\": \"Martins\",\n" +
            "    \"company\": \"Woodstock Discos\",\n" +
            "    \"address\": \"Rua Dr. Falcão Filho, 155\",\n" +
            "    \"city\": \"São Paulo\",\n" +
            "    \"state\": \"SP\",\n" +
            "    \"country\": \"Brazil\",\n" +
            "    \"postalCode\": \"01007-010\",\n" +
            "    \"phone\": \"+55 (11) 3033-5446\",\n" +
            "    \"fax\": \"+55 (11) 3033-4564\",\n" +
            "    \"email\": \"eduardo@woodstock.com.br\",\n" +
            "    \"supportRepId\": {\n" +
            "        \"id\": 4,\n" +
            "        \"lastName\": \"Park\",\n" +
            "        \"firstName\": \"Margaret\",\n" +
            "        \"title\": \"Sales Support Agent\",\n" +
            "        \"reportsTo\": {\n" +
            "            \"id\": 2,\n" +
            "            \"lastName\": \"Edwards\",\n" +
            "            \"firstName\": \"Nancy\",\n" +
            "            \"title\": \"Sales Manager\",\n" +
            "            \"reportsTo\": {\n" +
            "                \"id\": 1,\n" +
            "                \"lastName\": \"Adams\",\n" +
            "                \"firstName\": \"Andrew\",\n" +
            "                \"title\": \"General Manager\",\n" +
            "                \"reportsTo\": null,\n" +
            "                \"birthDate\": \"1962-02-18T00:00:00Z\",\n" +
            "                \"hireDate\": \"2002-08-13T23:00:00Z\",\n" +
            "                \"address\": \"11120 Jasper Ave NW\",\n" +
            "                \"city\": \"Edmonton\",\n" +
            "                \"state\": \"AB\",\n" +
            "                \"country\": \"Canada\",\n" +
            "                \"postalCode\": \"T5K 2N1\",\n" +
            "                \"phone\": \"+1 (780) 428-9482\",\n" +
            "                \"fax\": \"+1 (780) 428-3457\",\n" +
            "                \"email\": \"andrew@chinookcorp.com\"\n" +
            "            },\n" +
            "            \"birthDate\": \"1958-12-08T00:00:00Z\",\n" +
            "            \"hireDate\": \"2002-04-30T23:00:00Z\",\n" +
            "            \"address\": \"825 8 Ave SW\",\n" +
            "            \"city\": \"Calgary\",\n" +
            "            \"state\": \"AB\",\n" +
            "            \"country\": \"Canada\",\n" +
            "            \"postalCode\": \"T2P 2T3\",\n" +
            "            \"phone\": \"+1 (403) 262-3443\",\n" +
            "            \"fax\": \"+1 (403) 262-3322\",\n" +
            "            \"email\": \"nancy@chinookcorp.com\"\n" +
            "        },\n" +
            "        \"birthDate\": \"1947-09-18T23:00:00Z\",\n" +
            "        \"hireDate\": \"2003-05-02T23:00:00Z\",\n" +
            "        \"address\": \"683 10 Street SW\",\n" +
            "        \"city\": \"Calgary\",\n" +
            "        \"state\": \"AB\",\n" +
            "        \"country\": \"Canada\",\n" +
            "        \"postalCode\": \"T2P 5G3\",\n" +
            "        \"phone\": \"+1 (403) 263-4423\",\n" +
            "        \"fax\": \"+1 (403) 263-4289\",\n" +
            "        \"email\": \"margaret@chinookcorp.com\"\n" +
            "    }\n" +
            "}";

    @InjectMocks
    private CustomerController customerController;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(this.customerController).build();
    }


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
    @DisplayName("Check name for id 10. expect Eduardo")
    void findCustomerById() throws Exception {
        String url = "http://localhost:8080/chinook/customer/10";
        String expected = "Eduardo";
        MvcResult mvcResult = mockMvc
                             .perform(MockMvcRequestBuilders.get(url))
                             .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(customerIdOneJson);
        assertEquals(expected, node.get("firstName").asText());
    }

    @Test
    void addCustomer() {
    }

    @Test
    void updateCustomerById() {
    }
}