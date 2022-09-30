package com.api.twstock.controller;

import com.api.twstock.handlers.EventHandler;
import com.api.twstock.handlers.MessageHandler;
import com.api.twstock.model.DTO.TradeNotifyDTO;
import com.api.twstock.model.DTO.UpdateStockNotifyDto;
import com.api.twstock.model.entity.StockNotifyList;
import com.api.twstock.model.lineEntity.Event;
import com.api.twstock.model.lineEntity.EventWrapper;
import com.api.twstock.service.LineNotifyService;
import com.api.twstock.service.ReplyService;
import com.api.twstock.service.StockNotifyService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
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

    LineNotifyService lineNotifyService;
    StockNotifyService stockNotifyService;
    TaskScheduler taskScheduler;
    ReplyService replyService;
    private final HashMap<String, EventHandler> handlers;

    public NoticeController(LineNotifyService lineNotifyService, StockNotifyService stockNotifyService,
                            TaskScheduler taskScheduler, MessageHandler messageHandler,ReplyService replyService){
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
    @ApiOperation(value="取得line訊息")
    public ResponseEntity<Object> receiveMessage(@RequestBody EventWrapper eventWrapper){
        // filter : 篩出所有是 *訊息* |而且| 是 *文字* 的訊息
        // collect : 將其加入Map中 為 <replyToken, Event>
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
        return ResponseEntity.ok().body("Response success");
    }

    @PostMapping("/frontend")
    @ResponseBody
    @ApiOperation(value="從台股小幫手網站新增到價通知")
    public ResponseEntity<Object> addNotifyListFromFrontend(@RequestBody TradeNotifyDTO tradeNotifyDTO){
        if(stockNotifyService.addStockNotifyList(tradeNotifyDTO.getStockId(), tradeNotifyDTO.getTargetPrice(),
                tradeNotifyDTO.getTradingStrat(), tradeNotifyDTO.getMessage(), tradeNotifyDTO.getUserLineId()) != null
        ){
            return ResponseEntity.ok().build();
        } return ResponseEntity.status(401).body("新增失敗");
    }

    @GetMapping("/getnotifylistbylineid")
    @ApiOperation(value="用line id取得到價通知清單")
    public List<StockNotifyList> getNotifyListByLineId(@RequestParam String userlineid){
        return stockNotifyService.getNotifyListByLineId(userlineid);
    }

    @PostMapping("/hitprice")
    @ApiOperation(value="新增到價通知")
    public StockNotifyList addNotifyList(@RequestBody TradeNotifyDTO tradeNotifyDTO){
        log.info("get via db");
        return stockNotifyService.addStockNotifyList(tradeNotifyDTO.getStockId(),
                tradeNotifyDTO.getTargetPrice(),
                tradeNotifyDTO.getTradingStrat(),
                tradeNotifyDTO.getMessage());
    }

    @PutMapping("update")
    @ApiOperation(value="更新到價通知")
    public int updateNotifyListItem(@RequestBody UpdateStockNotifyDto updateStockNotifyDto){
      return stockNotifyService.updateStockNotifyList(updateStockNotifyDto.getId(),
               updateStockNotifyDto.getTargetPrice(), updateStockNotifyDto.getStrat(),
               updateStockNotifyDto.getComment());
    }

    @DeleteMapping("/delete")
    @ApiOperation(value="刪除到價通知")
    public void deleteStockNotifyListItem(@RequestParam Integer id){
        stockNotifyService.deleteStockNotifyList(id);
    }

    //僅為測試用
    @GetMapping("/test")
    public void replyTest(){
        replyService.replyTest();
    }

}
