package com.api.twstock.model.entity;

import com.api.twstock.model.security.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="watchlist")
@AllArgsConstructor
@Getter
@Setter
public class StockWatchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="stockId")
    private String stockId;

    @Column(name="stockName")
    private String stockName;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public StockWatchlist(){};

    public StockWatchlist(String stockId, String stockName) {
        this.stockId = stockId;
        this.stockName = stockName;
    }
}
