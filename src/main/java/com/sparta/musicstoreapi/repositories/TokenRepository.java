package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer>
{
    Optional<Token> findByToken(String token);
    Optional<Token> findByEmail(String email);
}