package com.api.twstock.model.jsonFormat.fugle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trade {

    @JsonProperty(value="trade")
    TradeData tradeData;

    @JsonProperty(value="changePercent")
    Float changePercent;

}
