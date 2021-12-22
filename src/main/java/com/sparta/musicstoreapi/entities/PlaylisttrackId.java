package com.sparta.musicstoreapi.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PlaylisttrackId implements Serializable {
    private static final long serialVersionUID = -2374584036195361466L;
    @Column(name = "PlaylistId", nullable = false)
    private Integer playlistId;

    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne(optional = false)
    @JoinColumn(name = "TrackId", nullable = false)
    private Track track;

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Integer getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Integer playlistId) {
        this.playlistId = playlistId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistId, track);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PlaylisttrackId entity = (PlaylisttrackId) o;
        return Objects.equals(this.playlistId, entity.playlistId) &&
                Objects.equals(this.track, entity.track);
    }
}