package com.api.twstock.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateStockNotifyDto {

    private Integer id;

    private Float targetPrice;

    private String strat;

    private String comment;
}
