package com.api.twstock.utils;

import com.api.twstock.model.jsonFormat.QuoteData;
import com.api.twstock.service.StockDataService;
import com.api.twstock.service.StockNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class LineMessageUtil {

    @Autowired
    StockNameService stockNameService;

    @Autowired
    StockDataService stockDataService;

    public String quotationMessageUtil(String message){
        //get ticker
        String ticker = message.split(" ")[1];
        //取得股票名稱
        String stockName = stockNameService.getStockNameById(ticker).toString();
        //取得最新股票資訊
        QuoteData quoteData = stockDataService.getLatestQuotationDataByTicker(ticker);
        //取得即時股價
        Float lastQuote = quoteData.getDealPrice();
        //取得日期
        String date = quoteData.getDate();
        //取得時間
        String time = quoteData.getTime().substring(0, 5);

        return date+" "+time+"，"+stockName+"("+ticker+")"+
                ": "+String.valueOf(lastQuote);
    }

    public String defaultMessageUtil(){
        return "輸入指令錯誤喔，請參考指令說明重新輸入";
    }
}
