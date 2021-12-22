package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Artist;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
}