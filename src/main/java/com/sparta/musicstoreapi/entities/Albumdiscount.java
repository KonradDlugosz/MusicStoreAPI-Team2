package com.sparta.musicstoreapi.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "albumdiscount")
public class Albumdiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "AlbumId")
    private Album albumId;

    @Column(name = "ExpiryDate")
    private Date expiryDate;

    @Column(name = "Amount", precision = 3, scale = 2)
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getAlbumId() {
        return albumId.getId();
    }

    public void setAlbumId(Album albumId) {
        this.albumId = albumId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}