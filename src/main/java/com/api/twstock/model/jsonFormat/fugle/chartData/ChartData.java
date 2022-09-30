package com.api.twstock.model.jsonFormat.fugle.chartData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChartData {

    @JsonProperty(value="data")
    ChartDataElements chartDataElements;
}
