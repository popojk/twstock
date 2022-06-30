package com.api.twstock.resolver;

import com.api.twstock.model.lineEntity.Event;
import com.api.twstock.service.ReplyService;
import com.api.twstock.service.StockDataService;
import com.api.twstock.service.StockNameService;
import com.api.twstock.utils.LineMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class TextResolver implements Resolver {

    StockDataService stockDataService;
    StockNameService stockNameService;
    ReplyService replyService;
    LineMessageUtil lineMessageUtil;

    public TextResolver(StockDataService stockDataService, StockNameService stockNameService,
                        ReplyService replyService, LineMessageUtil lineMessageUtil){
        this.stockDataService = stockDataService;
        this.stockNameService = stockNameService;
        this.replyService = replyService;
        this.lineMessageUtil = lineMessageUtil;
    }

    @Override
    public void solve(Optional<Event> event) {
        String replyToken = event.map(Event::getReplyToken).orElse(null);
        String message = event.map(e -> e.getMessage().getText()).orElse(null);
        //need to check source (user / room / group)
        //to do
        String userId = event.map(e -> e.getSource().getUserId()).orElse(null);
        log.info("replyToken = {}", replyToken);
        log.info("message = {}", message);

        if(replyToken == null || message == null){
            log.error("replyToken or message is null");
            return;
        }

        switch(message.split(" ")[0]){
            case "quotation":
                replyService.sendResponseMessage(replyToken, lineMessageUtil.quotationMessageUtil(message));
                break;
            case "notify":
                replyService.recordNoticeInfoAndSendMessage(replyToken, userId, message);
                break;
            case "add":
                replyService.addStockToWatchlist(replyToken, userId, message);
                break;
            case "delete":
                replyService.deleteStockFromWatchlist(replyToken, userId, message);
                break;
            case "watchlist":
                replyService.showWatchlist(replyToken, userId);
                break;
            default:
                replyService.sendResponseMessage(replyToken, lineMessageUtil.defaultMessageUtil());
                break;
        }
    }
}
