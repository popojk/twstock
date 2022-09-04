package com.api.twstock.controller;

import com.api.twstock.service.BackTestingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//未完成
@RestController
@RequestMapping("/stock/backtesting")
@CrossOrigin(origins = "http://localhost:3000")
public class BackTestingController {

    @Autowired
    BackTestingService backTestingService;

    @ApiOperation(value="回測月線穿越季線黃金交叉")
    @GetMapping("/twentymaoversixtyma")
    public Object backTestTwentyMaOverSixtyMa(@RequestParam(name="stockid") String stockId,
                                              @RequestParam(name="investmentamount") long investmentAmount,
                                              @RequestParam(name="startdate") String startDate,
                                              @RequestParam(name="enddate") String endDate){

        return backTestingService.twentyMaOverSixtyMa(stockId, investmentAmount, startDate, endDate);

    }
}
