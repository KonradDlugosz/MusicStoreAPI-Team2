package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Discontinued;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiscontinuedRepository extends JpaRepository<Discontinued, Integer> {
    List<Discontinued> findAllByIsDiscontinuedTrue();
}