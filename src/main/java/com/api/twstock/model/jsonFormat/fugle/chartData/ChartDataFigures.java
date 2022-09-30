package com.api.twstock.model.jsonFormat.fugle.chartData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class ChartDataFigures {

    @JsonProperty(value="o")
    List<Double> quotes;

    @JsonProperty(value="v")
    List<BigInteger> volumes;

    @JsonProperty(value="t")
    List<BigInteger> timeStamp;

}
