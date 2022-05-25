package com.api.twstock.service;

import com.api.twstock.model.DTO.BasicTaDto;
import com.api.twstock.model.jsonFormat.FinmindQuoteData;
import com.api.twstock.model.jsonFormat.StockHistoryData;
import com.api.twstock.model.util.MAData;
import com.api.twstock.utils.FetchAPIUtil;
import com.api.twstock.utils.TACalculationUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockDataService {

    public List<StockHistoryData> getBasicTaData(BasicTaDto basicTaDto){
        ObjectMapper mapper = new ObjectMapper();
        List<StockHistoryData> basicData= FetchAPIUtil.fetchFinmindAPI(
                "TaiwanStockPrice",
                basicTaDto.getStockNo(),
                basicTaDto.getStartDate(),
                HttpMethod.GET,
                FinmindQuoteData.class).getData();
        List<StockHistoryData> list = mapper.convertValue(basicData, new TypeReference<List<StockHistoryData>>(){});
        return list;
    }

    public Object getBasicTaDataWithMA(BasicTaDto basicTaDto) {
        List<StockHistoryData> basicData = getBasicTaData(basicTaDto);

        Map<Object, Object> finalResultMap = new HashMap<>();
        Map<Integer, List<Map<String, Double>>> MASet = new HashMap<>();

        for(Integer movingAverage : basicTaDto.getMovingAverageList()) {
            List<Map<String, Double>> resultList = new ArrayList<>();

            //Loop over MA list and get MA data
            for (int i = basicData.size()-1; i > movingAverage ; i--) {
                Map<String, Double> MADataSet = new HashMap<>();
                Double MA;
                List<Double> currentDaysPrice = new ArrayList<>();
                Integer days = 0;

                String date = basicData.get(i).getDate();

                while (days < movingAverage) {
                    currentDaysPrice.add(basicData.get(i-days).getClose());
                    days++;
                }
                MA = currentDaysPrice.stream().mapToDouble(d -> d)
                        .average().getAsDouble();

                MADataSet.put(date, MA);
                resultList.add(MADataSet);
            }
            MASet.put(movingAverage, resultList);
        }

        Object KDDataSet = TACalculationUtil.calculateKDIndex(basicData);

        finalResultMap.put(1, basicData);
        finalResultMap.put(2, MASet);
        finalResultMap.put(3, KDDataSet);
        return finalResultMap;

    }
}
