package com.api.twstock.service;

import com.api.twstock.model.DTO.BasicTaDto;
import com.api.twstock.model.jsonFormat.finmind.StockHistoryData;
import com.api.twstock.utils.TACalculationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BackTestingService {

    @Autowired
    StockDataService stockDataService;

    public Object twentyMaOverSixtyMa(String stockId, long investmentAmount, String startDate, String endDate){
        BasicTaDto basicTaDto = new BasicTaDto();
        basicTaDto.setStockNo(stockId);
        basicTaDto.setStartDate(startDate);
        basicTaDto.getMovingAverageList().add(20);
        basicTaDto.getMovingAverageList().add(60);

        //fetch stock price data
        List<StockHistoryData> basicData = stockDataService.getBasicTaData(basicTaDto);

        //fetch moving average data
        Map<Integer, List<Map<String, Double>>> MASet = TACalculationUtil.getMASet(basicData, basicTaDto);

        List<Map<String, Double>> twentyMaList = MASet.get(20);
        List<Map<String, Double>> sixtyMaList = MASet.get(60);

        for(int i=0; i<=twentyMaList.size(); i++){
           String date = basicData.get(i).getDate();

        }

        return MASet;

        //strea




    }
}
