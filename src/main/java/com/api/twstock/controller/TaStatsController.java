package com.api.twstock.controller;

import com.api.twstock.model.DTO.BasicTaDto;
import com.api.twstock.service.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock/stats")
@CrossOrigin(origins = "http://localhost:3000")
public class TaStatsController {

    @Autowired
    StockDataService stockDataService;

    @GetMapping("/basic")
    public Object getBasicTa(@RequestBody BasicTaDto basicTaDto){
        return stockDataService.getBasicTaData(basicTaDto);
    }

    @GetMapping("basicwithma")
    public Object getBasicTaWithMA(@RequestBody BasicTaDto basicTaDto){
        return  stockDataService.getBasicTaDataWithMA(basicTaDto);
    }
}
