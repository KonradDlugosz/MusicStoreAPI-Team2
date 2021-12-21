package com.sparta.musicstoreapi.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "discontinued")
@Component
public class Discontinued {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne(optional = false)
    @JoinColumn(name = "TrackId")
    private Track trackId;

    @Column(name = "IsDiscontinued")
    private Boolean isDiscontinued;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

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