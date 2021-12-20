package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Playlisttrack;
import com.sparta.musicstoreapi.entities.PlaylisttrackId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylisttrackRepository extends JpaRepository<Playlisttrack, PlaylisttrackId> {
}