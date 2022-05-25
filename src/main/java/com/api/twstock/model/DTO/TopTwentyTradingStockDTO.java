package com.api.twstock.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopTwentyTradingStockDTO {
    @JsonProperty(value="Rank")
    private String rank;

    @JsonProperty(value="Code")
    private String code;

    @JsonProperty(value="Name")
    private String name;

    @JsonProperty(value="ClosingPrice")
    private String closingPrice;

    public TopTwentyTradingStockDTO(){

    }

    public TopTwentyTradingStockDTO(String rank, String code, String name, String closingPrice) {
        this.rank = rank;
        this.code = code;
        this.name = name;
        this.closingPrice = closingPrice;
    }
}
