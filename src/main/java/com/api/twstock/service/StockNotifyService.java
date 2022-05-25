package com.api.twstock.service;

import com.api.twstock.model.entity.StockNotifyList;
import com.api.twstock.model.jsonFormat.FinmindQuoteData;
import com.api.twstock.model.jsonFormat.QuoteData;
import com.api.twstock.repo.StockNameRepo;
import com.api.twstock.repo.StockNotifyRepo;
import com.api.twstock.utils.FetchAPIUtil;
import com.api.twstock.utils.StockPriceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockNotifyService {

    @Autowired
    LineNotifyService lineNotifyService;

    @Autowired
    StockNotifyRepo stockNotifyRepo;

    @Autowired
    StockNameRepo stockNameRepo;

    public List<StockNotifyList> getAllNotifyList(){
        return stockNotifyRepo.findAll();
    }

    public StockNotifyList addStockNotifyList(String stockId, Float targetPrice, String strategy, String comment){
        StockNotifyList tempListItem = new StockNotifyList(stockId, targetPrice, strategy, comment);
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
    //@Scheduled(fixedRate = 5000)
    public void hitPriceNotice() {
        //get stock notify list from db
        List<StockNotifyList> notifyList = stockNotifyRepo.findAll();
        String today = LocalDate.now().toString();
        for (int i = 0; i <= notifyList.size()-1; i++) {

            //fetch data from finmind api
            List<QuoteData> data = FetchAPIUtil.fetchFinmindAPI("TaiwanStockPriceTick",
                            notifyList.get(i).getStockId(), today, HttpMethod.GET, FinmindQuoteData.class)
                    .getData();

            //get the latest quotation
            Float lastQuote = data.get(data.size() - 1).getDealPrice();

            //get stock name
            String stockName = stockNameRepo.getStockNameByStockId(notifyList.get(i).getStockId());

            //send line notify while hit target price
            if (notifyList.get(i).getStrat().equals("breakThrough")) {
                if (StockPriceUtil.breakThrough(notifyList.get(i).getTargetPrice(), lastQuote)) {
                    lineNotifyService.sendNoticeToAlex(stockName+"(" + notifyList.get(i).getStockId() + ")" + "目前股價" + lastQuote + "，股價已突破"
                            + notifyList.get(i).getTargetPrice() + "，" + notifyList.get(i).getComment());
                    stockNotifyRepo.deleteById(notifyList.get(i).getId());
                }
            } else if (notifyList.get(i).getStrat().equals("tradingStop")) {
                if (StockPriceUtil.tradingStop(notifyList.get(i).getTargetPrice(), lastQuote)) {
                    lineNotifyService.sendNoticeToAlex(stockName+"(" + notifyList.get(i).getStockId() + ")" + "目前股價" + lastQuote + "，股價已跌破"
                            + notifyList.get(i).getTargetPrice() + "，" + notifyList.get(i).getComment());
                    stockNotifyRepo.deleteById(notifyList.get(i).getId());
                }
            }
        }
    }

}
