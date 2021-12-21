package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Bulkdiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulkdiscountRepository extends JpaRepository<Bulkdiscount, Integer> {
}