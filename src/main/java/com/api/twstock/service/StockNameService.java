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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockNameService {

    StockNameRepo stockNameRepo;
    RedisService redisService;

    public StockNameService(StockNameRepo stockNameRepo, RedisService redisService) {
        this.stockNameRepo = stockNameRepo;
        this.redisService = redisService;
    }

    public List<StockNameData> createStockNameList(){
        List<StockNameData> data = FetchAPIUtil.fetchFinmindAPI("TaiwanStockInfo",
                        null , null, HttpMethod.GET, FinmindStockNameData.class)
                .getData();

        for(int i=0; i< data.size(); i++){
            StockName stockName = new StockName(data.get(i).getStockId(), data.get(i).getStockName()
            , data.get(i).getMarketType());
            //stockNameRepo.save(stockName);
            redisService.set(stockName.getStockId(), stockName.getStockName());
            redisService.set(stockName.getStockName(), stockName.getStockId());
        }
        return data;
    }

    public Object getStockNameOrId(String stockNameOrId){
        return redisService.get(stockNameOrId);
    }


}
