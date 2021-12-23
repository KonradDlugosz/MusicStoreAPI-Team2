package com.sparta.musicstoreapi.entities;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;

@Entity
@Table(name = "playlist")
@Indexed
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlaylistId", nullable = false)
    private Integer id;

    @Column(name = "Name", length = 120)
    @Field
    private String name;

    public Playlist(Integer id) {
        this.id = id;
    }

    public Playlist() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}