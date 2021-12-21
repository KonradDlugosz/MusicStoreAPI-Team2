package com.sparta.musicstoreapi.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bulkdiscount")
public class Bulkdiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "AmountOfTracks", nullable = false)
    private Integer amountOfTracks;

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

    public Integer getAmountOfTracks() {
        return amountOfTracks;
    }

    public void setAmountOfTracks(Integer amountOfTracks) {
        this.amountOfTracks = amountOfTracks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}