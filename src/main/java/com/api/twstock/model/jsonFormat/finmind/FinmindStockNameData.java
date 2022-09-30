package com.api.twstock.model.jsonFormat.finmind;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class FinmindStockNameData {

    @JsonProperty(value="data")
    List<StockNameData> data= new LinkedList<StockNameData>();
}
