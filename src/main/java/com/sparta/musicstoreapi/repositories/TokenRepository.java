package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer>
{
}