package com.api.twstock.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="notify_list")
@Data
@AllArgsConstructor
public class StockNotifyList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="stock_id")
    private String stockId;

    @Column(name="target_price")
    private Float targetPrice;

    @Column(name="strat")
    private String strat;

    @Column(name="comment")
    private String comment;

    @Column(name="user_line_id")
    private String userLineId;

    public StockNotifyList(){};

    public StockNotifyList(String stockId, Float targetPrice, String strat, String comment, String userLineId) {
        this.stockId = stockId;
        this.targetPrice = targetPrice;
        this.strat = strat;
        this.comment = comment;
        this.userLineId = userLineId;
    }
}
