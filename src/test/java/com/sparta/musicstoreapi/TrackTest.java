package com.sparta.musicstoreapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class TrackTest {
    public static HttpResponse getOneTrackResponse = null;

    @BeforeAll
    public static void getConnections(){
        getOneTrackResponse = TrackRequests.getOneTrack();
    }


    @Test
    @DisplayName("1.1 Given truck id, return 200 status")
    public void getOneTrackStatusCode(){
        Assertions.assertEquals(200, getOneTrackResponse.statusCode());
    }

    @Test
    @DisplayName("1.2 Given truck id, return Track name")
    public void getOneTrackName(){
        Assertions.assertEquals("name", getOneTrackResponse.body());
    }

}

class TrackRequests{

    /**
     * Base methods to get HttpResponse and deserialize JSON to Java Objects POJO
     * @param request -> Http request with headers, method like GET/POST/DELETE etc.
     * @return Staff Object containing JSON body
     */

    private static HttpResponse<String> getResponse(HttpRequest request) {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * GET by ID,
     * @return Response for track with ID: 10
     */

    public static HttpResponse getOneTrack(){
        String getOneTrackUrl = "http://localhost:8080/chinook/tracks/10";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(getOneTrackUrl))
                .header("Content-Type", "application/json")
                .build();

        return getResponse(request);
    }

}