package com.api.twstock.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TradeNotifyDTO {
    private String stockId;

    private Float targetPrice;

    private String tradingStrat;

    private String message;
}
