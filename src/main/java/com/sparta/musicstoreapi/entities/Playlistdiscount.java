package com.sparta.musicstoreapi.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "playlistdiscount")
public class Playlistdiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "PlayListId")
    private Playlist playListId;

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

    public Integer getPlayListId() {
        return playListId.getId();
    }

    public void setPlayListId(Playlist playListId) {
        this.playListId = playListId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}