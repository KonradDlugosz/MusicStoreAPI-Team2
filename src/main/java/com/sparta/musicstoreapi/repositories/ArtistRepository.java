package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
}