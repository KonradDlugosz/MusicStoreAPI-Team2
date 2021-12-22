package com.sparta.musicstoreapi.utils;

import com.sparta.musicstoreapi.entities.Artist;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

public class ArtistDAO {
    @Autowired
    EntityManager entityManager;

    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

    QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
            .buildQueryBuilder()
            .forEntity(Artist.class)
            .get();

    org.apache.lucene.search.Query luceneQuery = queryBuilder
            .keyword()
            .onField("name")
            .matching("Aerosmith")
            .createQuery();
}
