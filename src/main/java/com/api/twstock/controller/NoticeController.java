package com.api.twstock.controller;

import com.api.twstock.model.DTO.TradeNotifyDTO;
import com.api.twstock.model.DTO.UpdateStockNotifyDto;
import com.api.twstock.model.entity.StockNotifyList;
import com.api.twstock.model.jsonFormat.FinmindQuoteData;
import com.api.twstock.service.LineNotifyService;
import com.api.twstock.service.StockNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notify")
@CrossOrigin(origins = "http://localhost:3000")
public class NoticeController {

    @Autowired
    LineNotifyService lineNotifyService;

    @Autowired
    StockNotifyService stockNotifyService;

    @Autowired
    TaskScheduler taskScheduler;

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

}
