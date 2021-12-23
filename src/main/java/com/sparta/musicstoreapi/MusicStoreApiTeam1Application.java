package com.sparta.musicstoreapi;

import org.hibernate.search.annotations.Indexed;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Indexed
public class MusicStoreApiTeam1Application {

    public static void main(String[] args) {
        SpringApplication.run(MusicStoreApiTeam1Application.class, args);
    }

}
