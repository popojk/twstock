package com.api.twstock.service;

import com.api.twstock.model.entity.StockName;
import com.api.twstock.model.jsonFormat.FinmindQuoteData;
import com.api.twstock.model.jsonFormat.FinmindStockNameData;
import com.api.twstock.model.jsonFormat.QuoteData;
import com.api.twstock.model.jsonFormat.StockNameData;
import com.api.twstock.repo.StockNameRepo;
import com.api.twstock.repo.StockNotifyRepo;
import com.api.twstock.utils.FetchAPIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockNameService {

    @Autowired
    StockNameRepo stockNameRepo;

    public List<StockNameData> createStockNameList(){
        List<StockNameData> data = FetchAPIUtil.fetchFinmindAPI("TaiwanStockInfo",
                        null , null, HttpMethod.GET, FinmindStockNameData.class)
                .getData();

        for(int i=0; i<= data.size(); i++){
            StockName stockName = new StockName(data.get(i).getStockId(), data.get(i).getStockName()
            , data.get(i).getMarketType());
            stockNameRepo.save(stockName);
        }

        return data;
    }


}
