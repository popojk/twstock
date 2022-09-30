package com.api.twstock.model.jsonFormat.fugle.chartData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChartDataInfo {

    @JsonProperty(value="date")
    String date;

    @JsonProperty(value="type")
    String type;

    @JsonProperty(value="symbolId")
    String symbolId;
}
