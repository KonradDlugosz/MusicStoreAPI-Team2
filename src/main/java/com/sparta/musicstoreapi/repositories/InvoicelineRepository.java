package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Invoiceline;
import com.sparta.musicstoreapi.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoicelineRepository extends JpaRepository<Invoiceline, Integer> {
}