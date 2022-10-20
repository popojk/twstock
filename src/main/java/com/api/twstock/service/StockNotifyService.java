package com.api.twstock.service;

import com.api.twstock.model.entity.StockNotifyList;
import com.api.twstock.model.jsonFormat.fugle.Data;
import com.api.twstock.model.jsonFormat.fugle.TradeData;
import com.api.twstock.repo.StockNameRepo;
import com.api.twstock.repo.StockNotifyRepo;
import com.api.twstock.utils.FetchAPIUtil;
import com.api.twstock.utils.StockPriceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockNotifyService {

    LineNotifyService lineNotifyService;
    StockNotifyRepo stockNotifyRepo;
    StockNameRepo stockNameRepo;

    public StockNotifyService(LineNotifyService lineNotifyService, StockNotifyRepo stockNotifyRepo, StockNameRepo stockNameRepo) {
        this.lineNotifyService = lineNotifyService;
        this.stockNotifyRepo = stockNotifyRepo;
        this.stockNameRepo = stockNameRepo;
    }

    public List<StockNotifyList> getNotifyListByLineId(String userLineId){
        return stockNotifyRepo.findByUserLineId(userLineId);
    }

    public StockNotifyList addStockNotifyList(String stockId, Float targetPrice, String strategy, String comment){
        StockNotifyList tempListItem = new StockNotifyList(stockId, targetPrice, strategy, comment, null);
        stockNotifyRepo.save(tempListItem);
        return tempListItem;
    }

    public StockNotifyList addStockNotifyList(String stockId, Float targetPrice, String strategy, String comment, String userLineId){
        StockNotifyList tempListItem = new StockNotifyList(stockId, targetPrice, strategy, comment, userLineId);
        stockNotifyRepo.save(tempListItem);
        return tempListItem;
    }

    public int updateStockNotifyList(Integer id, Float targetPrice, String strat, String comment){
        return stockNotifyRepo.updateStockNotifyListById(id, targetPrice, strat, comment);
    }

    public void deleteStockNotifyList(Integer id){
        stockNotifyRepo.deleteById(id);
    }

    //send notice when stock price hit target price
    @Scheduled(fixedRate = 20000)
    public void hitPriceNotice() {
        ObjectMapper mapper = new ObjectMapper();
        //get stock notify list from db
        List<StockNotifyList> notifyList = stockNotifyRepo.findAll();
        String today = LocalDate.now().toString();
        if(!notifyList.isEmpty()) {
            for (int i = 0; i <= notifyList.size() - 1; i++) {

                //fetch data from finmind api
            /*List<QuoteData> fetchData = FetchAPIUtil.fetchFinmindAPI("TaiwanStockPriceTick",
                            notifyList.get(i).getStockId(), today, HttpMethod.GET, FinmindQuoteData.class)
                    .getData();
            List<QuoteData> data = mapper.convertValue(fetchData, new TypeReference<List<QuoteData>>(){});*/

                TradeData data = FetchAPIUtil.fetchFugleAPIToGetQuote(notifyList.get(i).getStockId(), HttpMethod.GET, Data.class, "quote").getQuote().getTrade().getTradeData();

                //get the latest quotation
                Float lastQuote = data.getPrice();

                //get stock name
                String stockName = stockNameRepo.getStockNameByStockId(notifyList.get(i).getStockId()).getStockName();

                //send line notify while hit target price
                if (notifyList.get(i).getStrat().equals("breakThrough")) {
                    if (StockPriceUtil.breakThrough(notifyList.get(i).getTargetPrice(), lastQuote)) {
                        lineNotifyService.pushNoticeMessageToLineUserId(stockName + "(" + notifyList.get(i).getStockId() + ")" + "目前股價" + lastQuote + "，股價已突破"
                                + notifyList.get(i).getTargetPrice() + "，" + notifyList.get(i).getComment(), notifyList.get(i).getUserLineId());
                        stockNotifyRepo.deleteById(notifyList.get(i).getId());
                    }
                } else if (notifyList.get(i).getStrat().equals("tradingStop")) {
                    if (StockPriceUtil.tradingStop(notifyList.get(i).getTargetPrice(), lastQuote)) {
                        lineNotifyService.pushNoticeMessageToLineUserId(stockName + "(" + notifyList.get(i).getStockId() + ")" + "目前股價" + lastQuote + "，股價已跌破"
                                + notifyList.get(i).getTargetPrice() + "，" + notifyList.get(i).getComment(), notifyList.get(i).getUserLineId());
                        stockNotifyRepo.deleteById(notifyList.get(i).getId());
                    }
                }
            }
        }
    }

}
