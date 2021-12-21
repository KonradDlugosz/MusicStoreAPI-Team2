package com.sparta.musicstoreapi.controllers;


public class DiscontinuedControllerTest {
    private static final String GET_ALL_DISCONTINUED = "localhost:8080/chinook/tracks/discontinued";
    private static final String GET_DISCONTINUED_BY_TRACK_ID = "http://localhost:8080/chinook/tracks/{id}";
    private static final String POST_DISCONTINUED_BY_TRACK_ID = "http://localhost:8080/chinook/track/discontinue/{id}";
    private static final String PUT_DISCONTINUED_BY_TRACK_ID = "http://localhost:8080/chinook/track/discontinue/update/{id}";
    private static final String DELETED_DISCONTINUED_BY_TRACK_ID = "ttp://localhost:8080/chinook/track/discontinued/delete/{id}";
}
