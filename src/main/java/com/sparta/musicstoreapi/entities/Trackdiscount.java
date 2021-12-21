package com.sparta.musicstoreapi.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "trackdiscount")
public class Trackdiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "TrackId")
    private Track trackId;

    @Column(name = "ExpiryDate")
    private LocalDate expiryDate;

    @Column(name = "Amount", precision = 3, scale = 2)
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getTrackId() {
        return trackId.getId();
    }

    public void setTrackId(Track trackId) {
        this.trackId = trackId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}