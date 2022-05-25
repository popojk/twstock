package com.api.twstock.service;

import com.api.twstock.model.DTO.TopTwentyTradingStockDTO;
import com.api.twstock.utils.FetchAPIUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class TWSIService {

    public TopTwentyTradingStockDTO[] getTopTwentyTradingStock(){
        TopTwentyTradingStockDTO[] resultList =
                FetchAPIUtil.send(
                        "https://openapi.twse.com.tw/v1/exchangeReport/MI_INDEX20",
                        HttpMethod.GET,
                        TopTwentyTradingStockDTO[].class
                );
        return resultList;
    }
}
