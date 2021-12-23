package com.sparta.musicstoreapi.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;

@Entity
@Table(name = "album")
@Indexed
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AlbumId", nullable = false)
    private Integer id;

    @Column(name = "Title", nullable = false, length = 160)
    @Field
    private String title;

    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "ArtistId", nullable = false)
    private Artist artistId;

    public Artist getArtistId() {
        return artistId;
    }

    public void setArtistId(Artist artistId) {
        this.artistId = artistId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Album() {

    }

    public Album(Integer id) {
        this.id = id;
    }
}