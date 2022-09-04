package com.api.twstock.repo;

import com.api.twstock.model.entity.StockIndex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockIndexRepo extends JpaRepository<StockIndex, Integer> {

    StockIndex findByIndexId(String indexId);


}
