package com.api.twstock.utils;

import com.api.twstock.model.entity.StockWatchlist;
import com.api.twstock.model.jsonFormat.finmind.QuoteData;
import com.api.twstock.service.StockDataService;
import com.api.twstock.service.StockNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class LineMessageUtil {

    @Autowired
    StockNameService stockNameService;

    @Autowired
    StockDataService stockDataService;

    public String quotationMessageUtil(String message){
        //get ticker
        String ticker = message.split(" ")[1];
        //取得股票名稱
        String stockName = stockNameService.getStockNameOrId(ticker);
        //取得最新股票資訊
        QuoteData quoteData = stockDataService.getLatestQuotationDataByTicker(ticker);
        //取得即時股價
        Float lastQuote = quoteData.getDealPrice();

        return stockName+"("+ticker+"): "+ lastQuote;
    }

    //未完成，確認finmind歷史股價可否查詢即時報價
    public String getWatchlistQuotationMessageUtil(List<String> stockIdList){
        StringBuilder replyMessage = new StringBuilder();
        for(int i = 0 ; i < stockIdList.size(); i++){
            replyMessage.append(quotationMessageUtil("g "+stockIdList.get(i)) + "\n");
        }
        return replyMessage.toString();
    }

    public String alreadyExistsMessageUtil(StockWatchlist watchlist){
        return watchlist.getStockName()+"("+watchlist.getStockId()+")已存在於觀察清單中";
    }

    public String watchlistOverFiveMessageUtil(List<StockWatchlist> watchlist){
        StringBuilder replyMessage = new StringBuilder();
        replyMessage.append("觀察清單已超過5個標的上限，請先刪除部分標的，目前標的: ");
        for(int i = 0; i<watchlist.size()-1; i++){
            replyMessage.append(watchlist.get(i).getStockName()+
                    "("+watchlist.get(i).getStockId()+")");
            if(i != watchlist.size()-1){
                replyMessage.append("， ");
            }
        }
        return replyMessage.toString();
    }

    public String deleteWatchlistMessageUtil(String stockName, String stockId){
        StringBuilder replyMessage = new StringBuilder();
        replyMessage.append("已從觀察清單刪除"+stockName+"("+stockId+")");
        return replyMessage.toString();
    }

    public String defaultMessageUtil(){
        return "輸入指令錯誤喔，請參考指令說明重新輸入";
    }
}
