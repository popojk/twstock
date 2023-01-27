import React, { useCallback, useState, useEffect } from 'react';
import api from '../../../API/finmindAPI';
import Highcharts from 'highcharts/highstock';
import HighChartsReact from 'highcharts-react-official';
import ReactLoading from "react-loading";
import fetchTWStockAPIData from '../../../API/twStockAPI';

function ThirtyDaysMarginBuyShortSale(props) {

    const today = new Date();
    const [isLoading, setIsLoading] = useState(true);
    const [marginPurchaseShortSaleSeries, setMarginPurchaseShortSaleSeries] = useState({});

    const fetchData = useCallback(
        async (stockNo) => {
            let parse_date = new Date(today.valueOf() - 70 * 1000 * 60 * 60 * 24).toISOString().slice(0, 10);
            return fetchTWStockAPIData({
                dataset: "TaiwanStockMarginPurchaseShortSale",
                data_id: stockNo,
                start_date: parse_date,
                end_date: ""
            }).then((response) => response.json())
                .then((response) => {
                    let dataLength = response.data.length - 1;
                    let marginBuy = [];
                    let shortSell = [];

                    let oneDayData = response.data[dataLength];
                    let threeDayData = response.data[dataLength - 3];
                    let fiveDayData = response.data[dataLength - 5];
                    let tenDayData = response.data[dataLength - 10]
                    let thirtyDayData = response.data[dataLength - 30]

                    marginBuy.push(
                        oneDayData.MarginPurchaseBuy - oneDayData.MarginPurchaseSell,
                        oneDayData.MarginPurchaseTodayBalance - threeDayData.MarginPurchaseTodayBalance,
                        oneDayData.MarginPurchaseTodayBalance - fiveDayData.MarginPurchaseTodayBalance,
                        oneDayData.MarginPurchaseTodayBalance - tenDayData.MarginPurchaseTodayBalance,
                        oneDayData.MarginPurchaseTodayBalance - thirtyDayData.MarginPurchaseTodayBalance
                    )

                    shortSell.push(
                        oneDayData.ShortSaleSell - oneDayData.ShortSaleBuy,
                        oneDayData.ShortSaleTodayBalance - threeDayData.ShortSaleTodayBalance,
                        oneDayData.ShortSaleTodayBalance - fiveDayData.ShortSaleTodayBalance,
                        oneDayData.ShortSaleTodayBalance - tenDayData.ShortSaleTodayBalance,
                        oneDayData.ShortSaleTodayBalance - thirtyDayData.ShortSaleTodayBalance
                    )

                    setMarginPurchaseShortSaleSeries(() => {
                        return {
                            series: [{
                                name: '融資',
                                showInLegend: true,
                                data: marginBuy
                            },
                            {
                                name: '融券',
                                showInLegend: true,
                                data: shortSell,
                                color: '#FF0000'
                            }]
                        }
                    })
                    setIsLoading(false);
                })
        })

    useEffect(() => {
        fetchData(props.stockNo)
        setIsLoading(true);
    }, [props])



    return (
        <>
            {isLoading && <ReactLoading className="loading_icon" type={'spin'} color={'#89CCDF'} width={100} />}
            {!isLoading && <>
                <div className="thirty_days_column">
                    <HighChartsReact
                        highcharts={Highcharts}
                        options={{
                            chart: {
                                type: 'column',
                                height: 240,
                                width: 480
                            },
                            title: {
                                text: '近30日融資/券變化'
                            },
                            xAxis: {
                                categories: [
                                    '1日',
                                    '3日',
                                    '5日',
                                    '10日',
                                    '30日'
                                ],
                                crosshair: true
                            },
                            yAxis: {
                                title: {
                                    text: '張數'
                                },
                            },
                            tooltip: {
                                split: true,
                                valueDecimals: 0,
                                valueSuffix: '張',
                                shared: true,
                                useHTML: true
                            },
                            plotOptions: {
                                column: {
                                    pointPadding: 0.2,
                                    borderWidth: 0
                                }
                            },
                            credits: {
                                enabled: false
                            },
                            ...marginPurchaseShortSaleSeries
                        }} />
                </div>
            </>

            }
        </>

    )
}

export default ThirtyDaysMarginBuyShortSale
