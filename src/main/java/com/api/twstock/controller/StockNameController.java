package com.api.twstock.controller;

import com.api.twstock.model.jsonFormat.StockNameData;
import com.api.twstock.service.StockNameService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<StockNameData> getAndSaveStockNames(){
       return stockNameService.createStockNameList();
    }

    @GetMapping("/getname")
    public Object getStockNameById(@RequestParam(name="stockid") String stockId){
        return stockNameService.getStockNameOrId(stockId);
    }

    @GetMapping("/getId")
    public Object getStockIdByName(@RequestParam(name="stockname") String stockName){
        return stockNameService.getStockNameOrId(stockName);
    }


}
