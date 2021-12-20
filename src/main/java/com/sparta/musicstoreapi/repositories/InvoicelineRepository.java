package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Invoiceline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoicelineRepository extends JpaRepository<Invoiceline, Integer> {
}