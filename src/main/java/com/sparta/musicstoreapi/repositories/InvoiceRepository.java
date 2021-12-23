package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}