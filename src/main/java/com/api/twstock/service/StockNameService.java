package com.api.twstock.service;

import com.api.twstock.exception.ApiException;
import com.api.twstock.model.entity.StockName;
import com.api.twstock.model.jsonFormat.finmind.FinmindStockNameData;
import com.api.twstock.model.jsonFormat.finmind.StockNameData;
import com.api.twstock.repo.StockNameRepo;
import com.api.twstock.utils.FetchAPIUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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
        //將股票名稱存入db
        for(int i=0; i< data.size(); i++){
            StockName stockName = new StockName(data.get(i).getStockId(), data.get(i).getStockName()
            , data.get(i).getMarketType());
            if(stockNameRepo.getStockStockIdByStockName(stockName.getStockName()) == null){
                stockNameRepo.save(stockName);
            }
            //redisService.set(stockName.getStockId(), stockName.getStockName());
            //redisService.set(stockName.getStockName(), stockName.getStockId());
        }
        return data;
    }

    public String getStockNameOrId(String stockNameOrId){
            //return redisService.get(stockNameOrId);
            if(stockNameRepo.getStockNameByStockId(stockNameOrId) != null){
                return stockNameRepo.getStockNameByStockId(stockNameOrId).getStockName();
            }
            if(stockNameRepo.getStockStockIdByStockName(stockNameOrId) != null){
                return stockNameRepo.getStockStockIdByStockName(stockNameOrId).getStockId();
            }
            return null;
    }
}
