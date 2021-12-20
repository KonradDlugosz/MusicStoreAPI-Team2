package com.sparta.musicstoreapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "discontinued")
public class Discontinued {
    @Id
    @ManyToOne
    @JoinColumn(name = "TrackId")
    private Track trackId;

    @Column(name = "IsDiscontinued")
    private Boolean isDiscontinued;

    public Boolean getIsDiscontinued() {
        return isDiscontinued;
    }

    public void setIsDiscontinued(Boolean isDiscontinued) {
        this.isDiscontinued = isDiscontinued;
    }

    public Track getTrackId() {
        return trackId;
    }

    public void setTrackId(Track trackId) {
        this.trackId = trackId;
    }
}