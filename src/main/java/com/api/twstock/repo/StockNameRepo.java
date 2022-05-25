package com.api.twstock.repo;

import com.api.twstock.model.entity.StockName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface StockNameRepo extends JpaRepository<StockName, Integer> {

    @Transactional
    @Query("select stockName.stockName from StockName stockName where stockName.stockId = :stockId")
    String getStockNameByStockId(@Param("stockId") String stockId);
}
