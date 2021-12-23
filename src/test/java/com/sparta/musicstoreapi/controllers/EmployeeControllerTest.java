package com.sparta.musicstoreapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.musicstoreapi.entities.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EmployeeControllerTest {

    private static final String GET_ALL_EMPLOYEES = "http://localhost:8080/chinook/employees/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";
    private static final String GET_EMPLOYEES_BY_ID = "http://localhost:8080/chinook/employee/1/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";
    private static final String GET_EMPLOYEE_BY_LASTNAME = "http://localhost:8080/chinook/employee/employee-by-lastname?lastName=Adams/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";
    private static final String POST_EMPLOYEE = "http://localhost:8080/chinook/employee/add/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";
    private static final String PUT_EMPLOYEE = "http://localhost:8080/chinook/employee/update/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg";

    private static HttpResponse<String> getAllEmployeesResponse = null;
    private static HttpResponse<String> getOneEmployeeResponse = null;
    private static Employee getOneEmployeeResponseJSON = null;
    private static HttpResponse<String> searchByLastnameResponse = null;
    private static HttpResponse<String> postEmployeeResponse = null;
    private static HttpResponse<String> putEmployeeResponse = null;

    @BeforeAll
    public static void getConnections() {
        getAllEmployeesResponse = getRequest(GET_ALL_EMPLOYEES);
        getOneEmployeeResponse = getRequest(GET_EMPLOYEES_BY_ID);
        getOneEmployeeResponseJSON = getObjectMapper(getRequest(GET_EMPLOYEES_BY_ID).body());
        searchByLastnameResponse = getRequest(GET_EMPLOYEE_BY_LASTNAME);
        postEmployeeResponse = postRequest(POST_EMPLOYEE);
        putEmployeeResponse = putRequest(PUT_EMPLOYEE);
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

    public static Employee getObjectMapper(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        Employee employee = null;
        try {
            employee = mapper.readValue(json, Employee.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public static HttpResponse<String> postRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                            "lastName": "Pap",
                            "firstName": "Nik",
                             "title": "Manager",
                             "reportsTo": null,
                             "birthDate": "1986-10-15T00:00:00Z",
                             "hireDate": "2008-08-13T23:00:00Z",
                             "address": "2045 Kevin Golden SW",
                             "city": "Edmonton",
                             "state": "AB",
                             "country": "Canada",
                             "postalCode": "T5H 4H3",
                             "phone": "+1 (780) 428-9483",
                             "fax": "+1 (780) 428-3458",
                             "email": "nikpap@chinookcorp.com"
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
                                "id": 9,
                                "lastName": "Durant",
                                "firstName": "Kevin",
                                "title": "General Manager",
                                "reportsTo": null,
                                "birthDate": "1987-08-11T00:00:00Z",
                                "hireDate": "2004-08-13T23:00:00Z",
                                "address": "1112 Casper Ave NW",
                                "city": "Edmonton",
                                "state": "AB",
                                "country": "Canada",
                                "postalCode": "T8K 1N3",
                                "phone": "+1 (780) 512-9482",
                                "fax": "+1 (780) 512-3457",
                                "email": "kdurant@chinookcorp.com"
                        }
                                                
                        """))
                .build();
        return getResponse(request);
    }

    @Test
    @DisplayName("Given all employees request, return 200 status")
    public void getAllEmployeesStatusCodeTest(){
        Assertions.assertEquals(200, getAllEmployeesResponse.statusCode());
    }

    @Test
    @DisplayName("Given employee id, return 200 status")
    public void getOneEmployeeStatusCodeTest(){
        Assertions.assertEquals(200, getOneEmployeeResponse.statusCode());
    }

    @Test
    @DisplayName("Given emploee id, return the id")
    public void getEmployeeIdTest(){
        Assertions.assertEquals(1, getOneEmployeeResponseJSON.getId());
    }

    @Test
    @DisplayName("Given a new employee, return 200 status")
    public void postEmployeeStatusCheckTest(){
        Assertions.assertEquals(200, postEmployeeResponse.statusCode());
    }

    @Test
    @DisplayName("Given an employee update, return 200 status")
    public void putEmployeeStatusCheckTest(){
        Assertions.assertEquals(200, putEmployeeResponse.statusCode());
    }

}
