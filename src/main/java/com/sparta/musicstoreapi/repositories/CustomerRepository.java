package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}