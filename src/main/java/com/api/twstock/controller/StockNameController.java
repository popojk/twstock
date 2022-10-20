package com.api.twstock.controller;

import com.api.twstock.model.jsonFormat.finmind.StockNameData;
import com.api.twstock.service.StockNameService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stockname")
@CrossOrigin(origins = "http://localhost:3000")
public class StockNameController {

    @Autowired
    StockNameService stockNameService;

    //For one time use
    @GetMapping("/getnames")
    @ApiOperation(value="更新股票名稱存入資料庫")
    public List<StockNameData> getAndSaveStockNames(){
       return stockNameService.createStockNameList();
    }

    @GetMapping("/getname")
    @ApiOperation(value="以股票代號取得股票名稱")
    @Cacheable(value="stockName", key="#stockId",
    condition="@stockNameService.getStockNameOrId(#stockId) != null")
    public Object getStockNameById(@RequestParam(name="stockid") String stockId){
        if(stockNameService.getStockNameOrId(stockId) != null){
            return stockNameService.getStockNameOrId(stockId);
        }
        return ResponseEntity.status(404).body("股票代號不存在");
    }

    @GetMapping("/getid")
    @ApiOperation(value="以股票名稱取得股票代號")
    @Cacheable(value="stockId", key="#stockName",
    condition = "@stockNameService.getStockNameOrId(#stockName) != null")
    public Object getStockIdByName(@RequestParam(name="stockname") String stockName){
        if(stockNameService.getStockNameOrId(stockName) != null){
            return stockNameService.getStockNameOrId(stockName);
        }
        return ResponseEntity.status(404).body("股票名稱不存在");
    }
}
