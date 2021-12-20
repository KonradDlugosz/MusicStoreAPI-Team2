package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Integer> {
}