package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Track;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Integer> {
    List<Track> findByNameContains(String name);
}