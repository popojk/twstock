package com.api.twstock.model.jsonFormat.fugle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeData {

    @JsonProperty(value="at")
    String at;

    @JsonProperty(value="price")
    Float price;

    @JsonProperty(value="serial")
    Integer serial;
}
