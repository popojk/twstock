package com.api.twstock.controller;

import com.api.twstock.handlers.EventHandler;
import com.api.twstock.handlers.MessageHandler;
import com.api.twstock.model.DTO.TradeNotifyDTO;
import com.api.twstock.model.DTO.UpdateStockNotifyDto;
import com.api.twstock.model.entity.StockNotifyList;
import com.api.twstock.model.jsonFormat.FinmindQuoteData;
import com.api.twstock.model.lineEntity.Event;
import com.api.twstock.model.lineEntity.EventWrapper;
import com.api.twstock.service.LineNotifyService;
import com.api.twstock.service.ReplyService;
import com.api.twstock.service.StockNotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notify")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class NoticeController {

    private final HashMap<String, EventHandler> handlers;

    LineNotifyService lineNotifyService;
    StockNotifyService stockNotifyService;
    TaskScheduler taskScheduler;
    ReplyService replyService;

    public NoticeController(LineNotifyService lineNotifyService, StockNotifyService stockNotifyService,
                            TaskScheduler taskScheduler, MessageHandler messageHandler, ReplyService replyService){
        this.lineNotifyService = lineNotifyService;
        this.stockNotifyService = stockNotifyService;
        this.taskScheduler = taskScheduler;
        this.replyService = replyService;

        handlers = new HashMap<>() {{
            put("message", messageHandler);
        }};
    }

    @PostMapping("/hello")
    public ResponseEntity printHello(){
        System.out.println("Hello");
        return ResponseEntity.ok("歡迎使用台股小幫手");
    }

    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<Object> receiveMessage(@RequestBody EventWrapper eventWrapper){
        // filter : 篩出所有是 *訊息* |而且| 是 *文字* 的訊息
        // collect : 將其加入Map中 為 <replyToken, Event>
        log.info("receive request");
        Map<String, Object> data = eventWrapper.getEvents().stream().collect(Collectors.toMap(Event::getReplyToken, x -> x));
        Optional<Event> event;

        //business logic
        for(Map.Entry<String, Object> entry : data.entrySet()){
            event = Optional.ofNullable((Event) entry.getValue());
            String eventType = event.get().getType();
            if(handlers.containsKey(eventType)){
                handlers.get(eventType).handle(event);
            }
        }
        return ResponseEntity.ok().body("123");
    }

    @GetMapping("/getall")
    public List<StockNotifyList> getAllNotifyList(){
        return stockNotifyService.getAllNotifyList();
    }

    @PostMapping("/line")
    public String getLineNotice(){
        return lineNotifyService.sendNoticeToAlex("美股go to hell");
    }

    @PostMapping("/hitprice")
    public StockNotifyList addNotifyList(@RequestBody TradeNotifyDTO tradeNotifyDTO){
        StockNotifyList stockNotifyList = stockNotifyService.addStockNotifyList(tradeNotifyDTO.getStockId(),
                tradeNotifyDTO.getTargetPrice(),
                tradeNotifyDTO.getTradingStrat(),
                tradeNotifyDTO.getMessage());
        return stockNotifyList;
    }

    @PutMapping("update")
    public int updateNotifyListItem(@RequestBody UpdateStockNotifyDto updateStockNotifyDto){
      return stockNotifyService.updateStockNotifyList(updateStockNotifyDto.getId(),
               updateStockNotifyDto.getTargetPrice(), updateStockNotifyDto.getStrat(),
               updateStockNotifyDto.getComment());
    }

    @DeleteMapping("/delete")
    public void deleteStockNotifyListItem(@RequestParam Integer id){
        stockNotifyService.deleteStockNotifyList(id);
    }

    @GetMapping("/test")
    public void replyTest(){
        replyService.replyTest();
    }

}
