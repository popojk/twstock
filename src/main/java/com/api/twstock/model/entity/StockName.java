package com.api.twstock.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="stock_name")
@Data
@AllArgsConstructor
public class StockName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="stock_id")
    private String stockId;

    @Column(name="stock_name")
    private String stockName;

    @Column(name="market_type")
    private String marketType;

    public StockName(){};

    public StockName(String stockId, String stockName, String marketType) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.marketType = marketType;
    }
}
