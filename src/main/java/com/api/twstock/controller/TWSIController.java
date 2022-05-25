package com.api.twstock.controller;

import com.api.twstock.model.DTO.TopTwentyTradingStockDTO;
import com.api.twstock.service.TWSIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock/search")
@CrossOrigin(origins = "http://localhost:3000")
public class TWSIController {

    @Autowired
    TWSIService twsiService;

    @GetMapping("/exchange/gettoptwentytradingstock")
    public TopTwentyTradingStockDTO[] getTopTwentyTradingStock(){

        TopTwentyTradingStockDTO[] topTwentyTradingStockDTOS = twsiService.getTopTwentyTradingStock();
        return topTwentyTradingStockDTOS;
    }


}
