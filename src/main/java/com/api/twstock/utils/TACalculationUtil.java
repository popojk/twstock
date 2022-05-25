package com.api.twstock.utils;

import com.api.twstock.model.jsonFormat.StockHistoryData;

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
}
