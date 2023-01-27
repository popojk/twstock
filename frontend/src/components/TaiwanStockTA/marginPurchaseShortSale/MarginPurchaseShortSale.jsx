import React, { useCallback, useState, useEffect } from 'react';
import api from '../../../API/finmindAPI';
import Highcharts from 'highcharts/highstock';
import HighChartsReact from 'highcharts-react-official';
import ReactLoading from "react-loading";
import fetchTWStockAPIData from '../../../API/twStockAPI';

function MarginPurchaseShortSale(props) {
    const today = new Date();
    const [isLoading, setIsLoading] = useState(true);
    const [marginPurchaseShortSaleSeries, setMarginPurchaseShortSaleSeries] = useState({});

    const fetchData = useCallback(
        async (stockNo) => {
            let parse_date = new Date(today.valueOf() - 380 * 1000 * 60 * 60 * 24).toISOString().slice(0, 10);
            return fetchTWStockAPIData({
                dataset: "TaiwanStockMarginPurchaseShortSale",
                data_id: stockNo,
                start_date: parse_date,
                end_date: ""
            }).then((response) => response.json())
                .then((response) => {
                    let marginPruchaseBalance = [];
                    let shortSaleBalance = [];
                    let dataLength = response.data.length;

                    for (let i = 0; i < dataLength; i++) {
                        marginPruchaseBalance.push({
                            y: response.data[i].MarginPurchaseTodayBalance,
                            x: new Date(response.data[i].date).getTime()
                        });
                        shortSaleBalance.push({
                            y: response.data[i].ShortSaleTodayBalance,
                            x: new Date(response.data[i].date).getTime()
                        });
                    }
                    setMarginPurchaseShortSaleSeries(() => {
                        return {
                            series: [{
                                name: '融資',
                                type: 'line',
                                data: marginPruchaseBalance
                            }, {
                                name: '融券',
                                type: 'line',
                                data: shortSaleBalance
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
                <div>
                    <HighChartsReact
                        highcharts={Highcharts}
                        options={{
                            title: {
                                text: '融資/券餘額變化'
                            },
                            plotOptions: {
                                series: {
                                    cumulative: false,
                                    //pointStart: Date.UTC(2021, 0, 1),
                                    pointIntervalUnit: 'day'
                                }
                            },
                            chart: {
                                height: 400,
                                width: 970,
                                marginLeft: 50,
                                margintop: 5
                            },
                            rangeSelector: {
                                enabled: true,
                                selected: 2
                            },
                            xAxis: {
                                type: 'datetime',
                            },
                            yAxis: {
                                title: {
                                    text: '張數'
                                },
                            },
                            tooltip: {
                                split: true,
                                valueDecimals: 0,
                                valueSuffix: '張'

                            },
                            ...marginPurchaseShortSaleSeries
                        }}
                    />
                </div>
            </>}
        </>
    )
}

export default MarginPurchaseShortSale
