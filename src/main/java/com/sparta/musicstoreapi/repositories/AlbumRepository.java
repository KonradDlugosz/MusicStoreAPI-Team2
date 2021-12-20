package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
}