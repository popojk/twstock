package com.api.twstock.service;

import com.api.twstock.exception.ApiException;
import com.api.twstock.model.entity.StockNotifyList;
import com.api.twstock.model.entity.StockWatchlist;
import com.api.twstock.model.lineEntity.EntityMessage;
import com.api.twstock.model.lineEntity.Reply;
import com.api.twstock.model.lineEntity.TextMessage;
import com.api.twstock.utils.LineMessageAPI;
import com.api.twstock.utils.LineMessageUtil;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReplyService {

    private final LineMessageAPI lineMessageAPI;
    private StockNotifyService stockNotifyService;
    private WatchlistService watchlistService;
    private StockNameService stockNameService;
    private UserService userService;
    private LineMessageUtil lineMessageUtil;
    private JwtUserDetailsServiceImpl jwtUserDetailsService;

    public ReplyService(LineMessageAPI lineMessageAPI,
                        StockNotifyService stockNotifyService,
                        WatchlistService watchlistService,
                        StockNameService stockNameService,
                        UserService userService,
                        LineMessageUtil lineMessageUtil,
                        JwtUserDetailsServiceImpl jwtUserDetailsService){
        this.lineMessageAPI = lineMessageAPI;
        this.stockNotifyService = stockNotifyService;
        this.watchlistService = watchlistService;
        this.stockNameService = stockNameService;
        this.userService = userService;
        this.lineMessageUtil = lineMessageUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    //only for test
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

    //傳送回覆訊息
    public void sendResponseMessage(String replyToken, String... messages){
        List<EntityMessage> messagesList = new ArrayList<>();

        for(String message : messages){
            messagesList.add(new TextMessage("text", message));
        }

        Reply reply = new Reply(replyToken, messagesList);
        reply(reply);
    }

    //新增line id至帳戶
    public void addLineIdToAccount(String replyToken, String userLineId, String message){
        String username = message.split(" ")[1];
        try {
            if (jwtUserDetailsService.addUserLineIdToAccount(username, userLineId)) {
                sendResponseMessage(replyToken, "恭喜你，line id加入成功，可以開始使用提醒功能");
            } else {
                sendResponseMessage(replyToken, "帳號已綁定line id");
            }
        } catch (Exception e){
            log.info(e.getMessage());
            sendResponseMessage(replyToken, "用戶名稱不存在");
        }
    }

    //於觀察清單新增股票代號(每個用戶清單上限5支標的)
    public void addStockToWatchlist(String replyToken, String userId, String message) throws ApiException {
        String stockId = message.split(" ")[1];
        try {
            //確認是否為股票代號
            if (!stockId.matches("[0-9]+")) {
                if (stockNameService.getStockNameOrId(stockId) == null) {
                    sendResponseMessage(replyToken, "股票代號/名稱不存在，請重新輸入!");
                }
                stockId = stockNameService.getStockNameOrId(stockId);
            }
            //取得user watch list
            List<StockWatchlist> userWatchlist = userService.getUserByLineId(userId).getWatchList();
            //確認觀察清單是否已超過5個
            if (userWatchlist.size() >= 5) {
                sendResponseMessage(replyToken, lineMessageUtil.watchlistOverFiveMessageUtil(userService.getUserByLineId(userId).getWatchList()));
            }
            //確認標的是否已存在觀察清單中
            for (StockWatchlist watchList : userWatchlist) {
                if(watchList.getStockId().equals(stockId)){
                    sendResponseMessage(replyToken, lineMessageUtil.alreadyExistsMessageUtil(watchList));
                }
            }
            String username = userService.getUserByLineId(userId).getUsername();
            watchlistService.addWatchlistByUsernameAndStockId(username, stockId);
            sendResponseMessage(replyToken, "已新增至觀察清單");
        }  catch (Exception e){
            log.info(e.getMessage());
        }
    }

    //從觀察清單刪除標的
    public void deleteStockFromWatchlist(String replyToken, String userId, String message){
        String stockId = message.split(" ")[1];
        //確認是否為股票代號
        if(!stockId.matches("[0-9]+")){
            stockId = stockNameService.getStockNameOrId(stockId);
        }
        //使用賬號取得觀察清單
        String username = userService.getUserByLineId(userId).getUsername();
        boolean deleted = watchlistService.deleteWatchlistByUsernameAndStockId(username, stockId);
        //傳送刪除成功或失敗通知
        if(deleted == true){
            String stockName = stockNameService.getStockNameOrId(stockId);
            sendResponseMessage(replyToken, lineMessageUtil.deleteWatchlistMessageUtil(stockName, stockId));
        } else if (deleted == false){
            sendResponseMessage(replyToken, "觀察清單中無此標的");
        }
    }

    //顯示觀察清單及股價
    public void showWatchlist(String replyToken, String userId){
        //產生觀察清單股票代碼list
        List<String> stockIdList = userService.getUserByLineId(userId)
                .getWatchList().stream().map(s -> s.getStockId()).collect(Collectors.toList());
        //傳入股票代碼list並傳送訊息
        String message = lineMessageUtil.getWatchlistQuotationMessageUtil(stockIdList);
        sendResponseMessage(replyToken, message);
    }

    //
    public void recordNoticeInfoAndSendMessage(String replyToken, String userId,String message) throws ApiException{
        String[] messageDetail = message.split(" ");
        String strategy = null;
        //取得股票代碼
        String stockId = message.split(" ")[1];
        if(!stockId.matches("[0-9]+")){
            if (stockNameService.getStockNameOrId(stockId) == null) {
                sendResponseMessage(replyToken, "找不到" + stockId + "，請確認後重新輸入");
                return;
            }
            stockId = stockNameService.getStockNameOrId(stockId).toString();
        }
        //確認報價策略，如果報價策略輸入錯誤回傳訊息
        if(messageDetail[3].equals("突破")){
            strategy = "breakThrough";
        } else if(messageDetail[3].equals("跌破")) {
            strategy = "tradingStop";
        } else{
            sendResponseMessage(replyToken, "輸入錯誤，請重新輸入");
            return;
        }
        //確認輸入含有小數點的數字，如果數字格式錯誤回傳通知
        if(messageDetail[2].matches("[+-]?(\\d+|\\d+\\.\\d+|\\.\\d+|\\d+\\.)([eE]\\d+)?")){
            stockNotifyService.addStockNotifyList(stockId, Float.parseFloat(messageDetail[2]),
                    strategy, messageDetail[4], userId);
        } else{
            sendResponseMessage(replyToken, "請輸入到價通知價格");
            return;
        }
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
