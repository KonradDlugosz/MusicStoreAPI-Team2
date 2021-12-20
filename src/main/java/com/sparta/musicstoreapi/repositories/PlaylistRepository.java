package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
}