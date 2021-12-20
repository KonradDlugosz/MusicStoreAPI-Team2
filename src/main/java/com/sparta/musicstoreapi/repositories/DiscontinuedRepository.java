package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Discontinued;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscontinuedRepository extends JpaRepository<Discontinued, Integer> {
}