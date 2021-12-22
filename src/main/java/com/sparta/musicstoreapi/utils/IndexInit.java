package com.sparta.musicstoreapi.utils;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class IndexInit {
    @PersistenceContext
    private EntityManager entityManager;

    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
    public void indexer() throws InterruptedException {
        fullTextEntityManager.createIndexer().startAndWait();
    }
}
