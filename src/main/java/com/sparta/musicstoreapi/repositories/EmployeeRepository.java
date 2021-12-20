package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}