package com.api.twstock.service;

import com.api.twstock.model.DTO.BasicTaDto;
import com.api.twstock.model.jsonFormat.finmind.FinmindData;
import com.api.twstock.model.jsonFormat.finmind.QuoteData;
import com.api.twstock.model.jsonFormat.finmind.StockHistoryData;
import com.api.twstock.model.jsonFormat.fugle.Data;
import com.api.twstock.model.jsonFormat.fugle.TopAndLastTenObject;
import com.api.twstock.model.jsonFormat.fugle.chartData.ChartData;
import com.api.twstock.utils.FetchAPIUtil;
import com.api.twstock.utils.TACalculationUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class StockDataService {

    private static final Logger logger = LoggerFactory.getLogger(StockDataService.class);

    StockIndexService stockIndexService;

    public StockDataService(StockIndexService stockIndexService) {
        this.stockIndexService = stockIndexService;
    }

    //取得當前股價
    public QuoteData getLatestQuotationDataByTicker(String ticker){
        ObjectMapper mapper = new ObjectMapper();
        LocalDate today = LocalDate.now();
        List<QuoteData> data =
            FetchAPIUtil.fetchFinmindAPI("TaiwanStockPriceTick",
                            ticker, today.toString(), HttpMethod.GET, FinmindData.class)
                    .getData();
        List<QuoteData> list = mapper.convertValue(data, new TypeReference<List<QuoteData>>(){});

        //get the latest quotation
        QuoteData lastQuote = list.get(list.size() - 1);

        return lastQuote;
    }

    //取得finmind API資料
    public Object getFinmindAPIData(String dataset, String stockId, String startDate){
        return FetchAPIUtil.fetchFinmindAPI(dataset,
               stockId, startDate, HttpMethod.GET, FinmindData.class);
    }

    //取得技術分析資料
    public List<StockHistoryData> getBasicTaData(BasicTaDto basicTaDto){
        ObjectMapper mapper = new ObjectMapper();
        List<StockHistoryData> basicData= FetchAPIUtil.fetchFinmindAPI(
                "TaiwanStockPrice",
                basicTaDto.getStockNo(),
                basicTaDto.getStartDate(),
                HttpMethod.GET,
                FinmindData.class).getData();
        List<StockHistoryData> list = mapper.convertValue(basicData, new TypeReference<List<StockHistoryData>>(){});
        return list;
    }

    //取得移動平均線資料
    public Object getBasicTaDataWithMA(BasicTaDto basicTaDto) {
        List<StockHistoryData> basicData = getBasicTaData(basicTaDto);

        Map<Object, Object> finalResultMap = new HashMap<>();
        Map<Integer, List<Map<String, Double>>> MASet = TACalculationUtil.getMASet(basicData, basicTaDto);

        finalResultMap.put(1, basicData);
        finalResultMap.put(2, MASet);
        return finalResultMap;
    }

    //取得前20與20類股指數當日漲跌福
    public Object getTopAndLastTenStockTypeIndexData(){
        Map<String, Float> allIndexMap = new HashMap<>();
        //loop over stock index and insert data in allIndexMap
        for(int i = 10 ; i <= 41 ; i++){
            Data fetchData = FetchAPIUtil.fetchFugleAPIToGetQuote("IX00"+i,
                    HttpMethod.GET, Data.class, "quote");
            String indexName = stockIndexService.getIndexNameById("IX00"+i);
            allIndexMap.put(indexName,
                    fetchData.getQuote().getTrade().getChangePercent());
        }
        //get top 10 indexs
        List<Map.Entry<String, Float>> list = new ArrayList<>(allIndexMap.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);
        List<Map.Entry<String, Float>> topTenList = list.subList(0, 10);
        //get last 10 indexs
        List<Map.Entry<String, Float>> lastTenList = list.subList(list.size()-10, list.size());
        Collections.reverse(lastTenList);
        //merge to JSON object
        TopAndLastTenObject returnObject = new TopAndLastTenObject(topTenList, lastTenList);
         //return result
        return returnObject;
    }

    //取得指數/個股日線圖資料
    public Object getDayTradeData(String index){
        return FetchAPIUtil.fetchFugleAPIToGetQuote(index, HttpMethod.GET, ChartData.class, "chart");
    }


}
