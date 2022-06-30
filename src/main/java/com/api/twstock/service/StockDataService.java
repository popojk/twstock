package com.api.twstock.service;

import com.api.twstock.model.DTO.BasicTaDto;
import com.api.twstock.model.jsonFormat.FinmindQuoteData;
import com.api.twstock.model.jsonFormat.QuoteData;
import com.api.twstock.model.jsonFormat.StockHistoryData;
import com.api.twstock.model.util.MAData;
import com.api.twstock.utils.FetchAPIUtil;
import com.api.twstock.utils.TACalculationUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockDataService {

    private static final Logger logger = LoggerFactory.getLogger(StockDataService.class);

    public QuoteData getLatestQuotationDataByTicker(String ticker){
        ObjectMapper mapper = new ObjectMapper();
        LocalDate today = LocalDate.now();
        List<QuoteData> data =
            FetchAPIUtil.fetchFinmindAPI("TaiwanStockPriceTick",
                            ticker, "2022-06-13", HttpMethod.GET, FinmindQuoteData.class)
                    .getData();
        List<QuoteData> list = mapper.convertValue(data, new TypeReference<List<QuoteData>>(){});

        //get the latest quotation
        QuoteData lastQuote = list.get(list.size() - 1);

        return lastQuote;
    }

    //fetch finmind API data
    public Object getFinmindAPIData(String dataset, String stockId, String startDate){
        return FetchAPIUtil.fetchFinmindAPI(dataset,
               stockId, startDate, HttpMethod.GET, FinmindQuoteData.class);
    }

    public List<StockHistoryData> getBasicTaData(BasicTaDto basicTaDto){
        ObjectMapper mapper = new ObjectMapper();
        List<StockHistoryData> basicData= FetchAPIUtil.fetchFinmindAPI(
                "TaiwanStockPrice",
                basicTaDto.getStockNo(),
                basicTaDto.getStartDate(),
                HttpMethod.GET,
                FinmindQuoteData.class).getData();
        List<StockHistoryData> list = mapper.convertValue(basicData, new TypeReference<List<StockHistoryData>>(){});
        logger.info("data fetch success");
        return list;
    }

    public Object getBasicTaDataWithMA(BasicTaDto basicTaDto) {
        List<StockHistoryData> basicData = getBasicTaData(basicTaDto);

        Map<Object, Object> finalResultMap = new HashMap<>();
        Map<Integer, List<Map<String, Double>>> MASet = TACalculationUtil.getMASet(basicData, basicTaDto);

        finalResultMap.put(1, basicData);
        finalResultMap.put(2, MASet);
        return finalResultMap;
    }


}
