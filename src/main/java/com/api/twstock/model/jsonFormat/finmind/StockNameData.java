package com.api.twstock.model.jsonFormat.finmind;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockNameData {

    @JsonProperty(value="stock_id")
    String stockId;

    @JsonProperty(value="stock_name")
    String stockName;

    @JsonProperty(value="type")
    String marketType;
}
