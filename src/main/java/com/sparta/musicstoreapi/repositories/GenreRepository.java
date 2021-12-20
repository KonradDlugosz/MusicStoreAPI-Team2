package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}