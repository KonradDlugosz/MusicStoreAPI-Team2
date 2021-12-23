package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Discontinued;
import com.sparta.musicstoreapi.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscontinuedRepository extends JpaRepository<Discontinued, Integer> {
    List<Discontinued> findAllByIsDiscontinuedTrue();
    List<Discontinued> findAllByIsDiscontinuedFalse();
    Discontinued findByTrackId(Track trackId);
}