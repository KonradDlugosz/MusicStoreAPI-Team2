package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Trackdiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackdiscountRepository extends JpaRepository<Trackdiscount, Integer> {
    Trackdiscount getByTrackId(Integer id);
}