package com.api.twstock.service;

import com.api.twstock.exception.ApiException;
import com.api.twstock.model.entity.StockWatchlist;
import com.api.twstock.model.security.User;
import com.api.twstock.repo.StockWatchlistRepo;
import com.api.twstock.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistService {

    StockNameService stockNameService;
    UserService userService;
    StockWatchlistRepo stockWatchlistRepo;
    UserRepo userRepo;

    public WatchlistService(StockNameService stockNameService,
                            UserRepo userRepo,
                            UserService userService,
                            StockWatchlistRepo stockWatchlistRepo){
        this.stockNameService = stockNameService;
        this.userRepo = userRepo;
        this.userService = userService;
        this.stockWatchlistRepo = stockWatchlistRepo;
    }

    public void addWatchlistByUsernameAndStockId(String username, String stockId) throws ApiException {
        String stockName = stockNameService.getStockNameOrId(stockId).toString();
        User tempUser = userService.getUserByUsername(username);
        List<StockWatchlist> currentWatchlist = tempUser.getWatchList();

        //check if the watchlist item already exists
        for(int i=0; i <= currentWatchlist.size()-1; i++){
            if(currentWatchlist.get(i).getStockId().equals(stockId)){
                throw new ApiException("Stock ID already exists");
            } else if(currentWatchlist.size() >= 5){
                throw new ApiException("Watchlist item cannot over 5");
            }
        }
        //add new watchlist item
        StockWatchlist stockWatchList = new StockWatchlist(stockId, stockName);
        stockWatchList.setUser(tempUser);
        stockWatchlistRepo.save(stockWatchList);
    }

    public boolean deleteWatchlistByUsernameAndStockId(String username, String stockId) throws ApiException {
        String stockName = stockNameService.getStockNameOrId(stockId).toString();
        User tempUser = userService.getUserByUsername(username);
        List<StockWatchlist> currentWatchlist = tempUser.getWatchList();
        boolean deleted = false;

        for(int i = 0 ; i<=currentWatchlist.size()-1; i++){
            if(currentWatchlist.get(i).getStockId().equals(stockId)){
                stockWatchlistRepo.deleteById(currentWatchlist.get(i).getId());
                deleted = true;
            }
        }
        return deleted;
    }

    public List<StockWatchlist> getStockWatchlistByUserName(String username){
        return userService.getUserByUsername(username).getWatchList();
    }

    public void deleteStockWatchlistByUsernameAndStockId(String username, String stockId){
        List<StockWatchlist> watchlist = userService.getUserByUsername(username).getWatchList();
        //delete watchlist from watchlist repo by id
        for(int i = 0; i<=watchlist.size()-1 ; i++){
            if(watchlist.get(i).getStockId().equals(stockId)){
                stockWatchlistRepo.deleteById(watchlist.get(i).getId());
            }
        }
    }
}
