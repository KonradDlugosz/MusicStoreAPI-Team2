package com.sparta.musicstoreapi.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "playlisttrack")
public class Playlisttrack {
    @EmbeddedId
    private PlaylisttrackId id;

    public PlaylisttrackId getId() {
        return id;
    }

    public void setId(PlaylisttrackId id) {
        this.id = id;
    }

}