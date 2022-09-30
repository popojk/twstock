package com.api.twstock.controller;

import com.api.twstock.model.DTO.BasicTaDto;
import com.api.twstock.service.StockDataService;
import com.api.twstock.service.StockIndexService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock/stats")
@CrossOrigin(origins = "http://localhost:3000")
public class TaStatsController {


    StockDataService stockDataService;
    StockIndexService stockIndexService;

    public TaStatsController(StockDataService stockDataService, StockIndexService stockIndexService) {
        this.stockDataService = stockDataService;
        this.stockIndexService = stockIndexService;
    }

    @GetMapping
    @ApiOperation(value="取得finmind API資料")
    public Object getFinmindAPIData(@RequestParam(name="dataset") String dataset,
                                    @RequestParam(name="stock_id") String stockId,
                                    @RequestParam(name="start_date") String start_date,
                                    @RequestParam(name="end_date") String end_date){
           return stockDataService.getFinmindAPIData(dataset, stockId, start_date);
    }

    @GetMapping("/basic")
    @ApiOperation(value="依股票代號與起始日取得個股歷史股價")
    public Object getData(@RequestParam(name="stock_id") String stockId,
                             @RequestParam(name="start_date") String startDate){
        BasicTaDto basicTaDto = new BasicTaDto();
        basicTaDto.setStockNo(stockId);
        basicTaDto.setStartDate(startDate);
        return stockDataService.getBasicTaData(basicTaDto);
    }

    @GetMapping("/daytradedata")
    @ApiOperation(value="依指數/股票代號取得日交易線圖資料")
    public Object getDayTradeData(@RequestParam(name="stock_id") String stockId){
        return stockDataService.getDayTradeData(stockId);
    }

    @GetMapping("/basicwithma")
    @ApiOperation(value="依股票代號，起始日以及均線天數取得個股歷史股價及移動平均線資料")
    public Object getBasicTaWithMA(@RequestBody BasicTaDto basicTaDto){
        return  stockDataService.getBasicTaDataWithMA(basicTaDto);
    }

    @GetMapping("/indexbytype")
    @ApiOperation(value="取得前後10類股指數漲跌幅")
    public Object getIndexByType(){
        return stockDataService.getTopAndLastTenStockTypeIndexData();
    }

}
