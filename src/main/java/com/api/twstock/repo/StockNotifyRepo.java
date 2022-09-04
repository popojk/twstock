package com.api.twstock.repo;

import com.api.twstock.model.entity.StockNotifyList;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Entity;
import javax.transaction.Transactional;
import java.util.List;


public interface StockNotifyRepo extends JpaRepository<StockNotifyList, Integer> {

    @Override
    List<StockNotifyList> findAll();

    @Transactional
    @Modifying
    @Query("delete from StockNotifyList stockNotifyList where stockNotifyList.stockId = :stockId")
    int deleteStockNotifyListByStockId(@Param("stockId") String stockId);

    @Transactional
    @Modifying
    @Query("update StockNotifyList stockNotifyList set stockNotifyList.strat = :strat, " +
            "stockNotifyList.comment = :comment, stockNotifyList.targetPrice=:targetPrice" +
            " where stockNotifyList.id = :stockNotifyId")
    int updateStockNotifyListById(@Param("stockNotifyId") Integer Id,
                                              @Param("targetPrice") Float targetPrice,
                                              @Param("strat") String strat,
                                              @Param("comment") String comment
                                              );
    List<StockNotifyList> findByUserLineId(String userLineId);





}
