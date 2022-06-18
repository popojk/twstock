package com.api.twstock.controller;

import com.api.twstock.model.DTO.BasicTaDto;
import com.api.twstock.service.StockDataService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock/stats")
@CrossOrigin(origins = "http://localhost:3000")
public class TaStatsController {

    @Autowired
    StockDataService stockDataService;

    @ApiOperation(value="依股票代號與起始日取得個股歷史股價")
    @GetMapping("/basic")
    public Object getBasicTa(@RequestParam(name="stock_id") String stockId,
                             @RequestParam(name="start_date") String startDate){
        BasicTaDto basicTaDto = new BasicTaDto();
        basicTaDto.setStockNo(stockId);
        basicTaDto.setStartDate(startDate);
        return stockDataService.getBasicTaData(basicTaDto);
    }

    @ApiOperation(value="依股票代號，起始日以及均線天數取得個股歷史股價及移動平均線資料")
    @GetMapping("basicwithma")
    public Object getBasicTaWithMA(@RequestBody BasicTaDto basicTaDto){
        return  stockDataService.getBasicTaDataWithMA(basicTaDto);
    }


}
