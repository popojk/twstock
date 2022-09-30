package com.api.twstock.model.jsonFormat.finmind;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StockHistoryData {

    @JsonProperty(value="date")
    String date;

    @JsonProperty(value="stock_id")
    String stockId;

    @JsonProperty(value="Trading_Volume")
    Integer tradingVolume;

    @JsonProperty(value="Trading_money")
    Long tradingMoney;

    @JsonProperty(value="open")
    Double open;

    @JsonProperty(value="max")
    Double max;

    @JsonProperty(value="min")
    Double min;

    @JsonProperty(value="close")
    Double close;

    @JsonProperty(value="spread")
    Double spread;

    @JsonProperty(value="Trading_turnover")
    Integer tradingTurnover;
}
