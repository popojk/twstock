package com.api.twstock.utils;

import java.util.function.Predicate;

public class StockPriceUtil {

    public static boolean breakThrough(Float targetPrice, Float stockPrice) {
        if (stockPrice > targetPrice){
            return true;
        }
        return false;
    }

    public static boolean tradingStop(Float targetPrice, Float stockPrice) {
        if (stockPrice < targetPrice){
            return true;
        }
        return false;
    }
}
