package com.api.twstock.controller;

import com.api.twstock.model.entity.StockWatchlist;
import com.api.twstock.service.WatchlistService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchlist")
@CrossOrigin(origins = "http://localhost:3000")
public class WatchListController {

    @Autowired
    WatchlistService watchlistService;

    @PostMapping("/test")
    @ApiOperation(value = "測試用帳戶名新增股票至觀察清單")
    public void addStockToWatchListByUsernameAndStockId(@RequestParam(name="username") String username,
                                                        @RequestParam(name="stock_id") String stockId){
        watchlistService.addWatchlistByUsernameAndStockId(username, stockId);
    }

    @GetMapping("/get")
    @ApiOperation(value="依帳號取得觀察清單")
    public List<StockWatchlist> getWatchlistByUsername(@RequestParam(name="username") String username){
        return watchlistService.getStockWatchlistByUserName(username);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value="以帳號與股票代碼刪除觀察清單項目")
    public void deleteWatchlistItemByUsernameAndStockId(@RequestParam(name="username") String username,
                                                        @RequestParam(name="stockid") String stockId){
        watchlistService.deleteStockWatchlistByUsernameAndStockId(username, stockId);
    }


}
