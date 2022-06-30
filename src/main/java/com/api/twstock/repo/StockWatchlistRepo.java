package com.api.twstock.repo;

import com.api.twstock.model.entity.StockWatchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockWatchlistRepo extends JpaRepository<StockWatchlist, Integer> {


}
