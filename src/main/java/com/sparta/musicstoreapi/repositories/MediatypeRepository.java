package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Mediatype;
import com.sparta.musicstoreapi.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediatypeRepository extends JpaRepository<Mediatype, Integer> {
}