package com.api.twstock.model.jsonFormat.fugle.chartData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChartDataElements {

    @JsonProperty(value="info")
    ChartDataInfo chartDataInfo;

    @JsonProperty(value="chart")
    ChartDataFigures chartDataFigures;

}
