package com.api.twstock.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="stock_index_list",
indexes = {@Index(name="index_id_index", columnList="index_id")})
@Data
@AllArgsConstructor
public class StockIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="index_name")
    private String indexName;

    @Column(name="index_id")
    private String indexId;

    public StockIndex(){};
}
