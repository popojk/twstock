package com.api.twstock.service;

import com.api.twstock.model.entity.StockNotifyList;
import com.api.twstock.model.lineEntity.EntityMessage;
import com.api.twstock.model.lineEntity.Reply;
import com.api.twstock.model.lineEntity.TextMessage;
import com.api.twstock.utils.LineMessageAPI;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReplyService {

    private final LineMessageAPI lineMessageAPI;
    private StockNotifyService stockNotifyService;

    public ReplyService(LineMessageAPI lineMessageAPI, StockNotifyService stockNotifyService){
        this.lineMessageAPI = lineMessageAPI;
        this.stockNotifyService = stockNotifyService;
    }

    public void replyTest(){

        Response response = lineMessageAPI.test();

        if(response.status() != 200){
            log.info("error while replying");
            log.info(String.valueOf(response.status()));
            log.info(String.valueOf(response.reason()));
            return;
        }

        log.info("reply succeed");
    }

    public void sendResponseMessage(String replyToken, String... messages){

        List<EntityMessage> messagesList = new ArrayList<>();

        for(String message : messages){
            messagesList.add(new TextMessage("text", message));
        }

        Reply reply = new Reply(replyToken, messagesList);
        reply(reply);
    }

    public void recordNoticeInfoAndSendMessage(String replyToken, String userId,String message){

        String[] messageDetail = message.split(" ");

        String strategy;
        if(messageDetail[3].equals("突破")){
            strategy = "breakThrough";
        } else {
            strategy = "tradingStop";
        }

        stockNotifyService.addStockNotifyList(messageDetail[1], Float.parseFloat(messageDetail[2]),
                strategy, messageDetail[4], userId);
        sendResponseMessage(replyToken, "已新增到價通知");
    }

    private void reply(Reply reply) {

        Response response = lineMessageAPI.reply(reply);

        if(response.status() != 200){
            log.info("error while replying");
            log.info(String.valueOf(response.status()));
            log.info(String.valueOf(response.reason()));
            return;
        }
        log.info("reply succeed");
    }
}
