package com.api.twstock.utils;

import com.api.twstock.model.DTO.BasicTaDto;
import com.api.twstock.model.jsonFormat.StockHistoryData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TACalculationUtil {

    public static Object calculateKDIndex(List<StockHistoryData> historyData){
        Map<String, Double[]> result = new HashMap<>();
        for(int i = historyData.size() -1; i > 9; i--){
            List<StockHistoryData> currentNineData = historyData.subList(i-8,i);
            double currentNineLowest = currentNineData.stream().mapToDouble(data -> data.getMin()).min().getAsDouble();
            double currentNineHighest = currentNineData.stream().mapToDouble(data -> data.getMax()).max().getAsDouble();
            double todayRSV = (double) (historyData.get(i).getClose() - currentNineLowest) /
                    (currentNineHighest - currentNineLowest);

        }
        return result;
    }

    public static Map<Integer, List<Map<String, Double>>> getMASet(List<StockHistoryData> basicData, BasicTaDto basicTaDto){
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
        return MASet;
    }


}
