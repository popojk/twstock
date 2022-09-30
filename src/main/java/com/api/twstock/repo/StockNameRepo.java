package com.api.twstock.repo;

import com.api.twstock.model.entity.StockName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface StockNameRepo extends JpaRepository<StockName, Integer> {

    StockName getStockNameByStockId(String stockId);

    StockName getStockStockIdByStockName(String stockName);
}
