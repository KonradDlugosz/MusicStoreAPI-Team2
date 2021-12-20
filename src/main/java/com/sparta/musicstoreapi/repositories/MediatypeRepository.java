package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Mediatype;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediatypeRepository extends JpaRepository<Mediatype, Integer> {
}