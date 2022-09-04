package com.api.twstock.model.jsonFormat.fugle;

import com.api.twstock.model.jsonFormat.StockNameData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Quote {

    @JsonProperty(value="quote")
    Trade trade;


}
