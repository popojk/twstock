package com.api.twstock.model.jsonFormat.finmind;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuoteData {

    @JsonProperty(value="date")
    String date;

    @JsonProperty(value="stock_id")
    String stockId;

    @JsonProperty(value="deal_price")
    Float dealPrice;

    @JsonProperty(value="volume")
    Float volume;

    @JsonProperty(value="Time")
    String time;

    @JsonProperty(value="TickType")
    int tickType;
}
