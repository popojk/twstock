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
public class StockDataController {

    @Autowired
    StockDataService stockDataService;

    @ApiOperation(value="取得finmind API資料")
    @GetMapping
    public Object getFinmindAPIData(@RequestParam(name="dataset") String dataset,
                                    @RequestParam(name="stock_id") String stockId,
                                    @RequestParam(name="start_date") String start_date,
                                    @RequestParam(name="end_date") String end_date){
           return stockDataService.getFinmindAPIData(dataset, stockId, start_date);
    }

    @ApiOperation(value="依股票代號與起始日取得個股歷史股價")
    @GetMapping("/basic")
    public Object getData(@RequestParam(name="stock_id") String stockId,
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

    @ApiOperation(value="取得前後時類股指數漲跌幅")
    @GetMapping("/indexbytype")
    public Object getIndexByType(){

        return null;
    }

}
