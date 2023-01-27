import React, { useCallback, useState, useEffect } from 'react';
import api from '../API/finmindAPI';

const today = new Date();

//Fetch foreign invester trade data
const fetchForeignInvesterData = async (stockNo) => {
    let parse_date = new Date(today.valueOf() - 380 * 1000 * 60 * 60 * 24).toISOString().slice(0, 10);
    return api({
        dataset: "TaiwanStockInstitutionalInvestorsBuySell",
        data_id: stockNo,
        start_date: parse_date,
        end_date: ""
    })
        .then((response) => (response.json()))
        .then((response) => {
            let TradingData = []
            let data = []
            response.data.filter(stat => stat.name === 'Foreign_Investor').map(stat => TradingData.push(stat));
            for (let i = 0; i < TradingData.length; i++) {
                data.push([new Date(TradingData[i].date).getTime(),
                TradingData[i].buy - TradingData[i].sell])
            }
            return {
                series: [{
                    name: '外資買賣超',
                    data: data
                }]
            }
        })
}


const InstitutionDataService = {
    fetchForeignInvesterData
}

export default InstitutionDataService;