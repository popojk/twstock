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

    @ApiOperation(value = "測試用帳戶名新增股票至觀察清單")
    @PostMapping
    public void addStockToWatchListByUsernameAndStockId(@RequestParam(name="username") String username,
                                                        @RequestParam(name="stock_id") String stockId){
        watchlistService.addWatchlistByUsernameAndStockId(username, stockId);
    }

    @ApiOperation(value="依帳號取得觀察清單")
    @GetMapping("/get")
    public List<StockWatchlist> getWatchlistByUsername(@RequestParam(name="username") String username){
        return watchlistService.getStockWatchlistByUserName(username);
    }

    @ApiOperation(value="依帳號與股票代碼刪除觀察清項目")
    @DeleteMapping("/delete")
    public void deleteWatchlistItemByUsernameAndStockId(@RequestParam(name="username") String username,
                                                        @RequestParam(name="stockid") String stockId){
        watchlistService.deleteStockWatchlistByUsernameAndStockId(username, stockId);
    }


}
