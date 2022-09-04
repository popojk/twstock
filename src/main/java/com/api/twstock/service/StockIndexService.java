package com.api.twstock.service;

import com.api.twstock.repo.StockIndexRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockIndexService {

    @Autowired
    StockIndexRepo stockIndexRepo;

    public String getIndexNameById(String indexId){
        return stockIndexRepo.findByIndexId(indexId).getIndexName();
    }
}
