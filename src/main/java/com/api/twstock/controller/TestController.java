package com.api.twstock.controller;

import com.api.twstock.model.jsonFormat.QuoteData;
import com.api.twstock.model.jsonFormat.fugle.Data;
import com.api.twstock.model.jsonFormat.fugle.Quote;
import com.api.twstock.utils.FetchAPIUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {

    @GetMapping
    public String testAPI(){
        return "access ok!";
    }

    @GetMapping("/quote")
    public Object testStockQuote(@RequestParam(name="stockid") String stockId){
        //ObjectMapper mapper = new ObjectMapper();
        Data fetchData = FetchAPIUtil.fetchFugleAPIToGetQuote(stockId, HttpMethod.GET, Data.class);
        //Data data = mapper.convertValue(fetchData, new TypeReference<Data>(){});
        return fetchData;
    }

}
