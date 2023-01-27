import React, { useCallback, useState, useEffect } from 'react';
import api from '../../../API/finmindAPI';
import Highcharts from 'highcharts/highstock';
import HighChartsReact from 'highcharts-react-official';
import ReactLoading from "react-loading";
import fetchTWStockAPIData from '../../../API/twStockAPI';

function RevenueChart(props) {
    const today = new Date();
    const [isLoading, setIsLoading] = useState(true);
    const { stockNo, revenueDataSetter } = props
    const [revenueSeries, setRevenueSeries] = useState({});

    const fetchData = useCallback(
        async (stockNo) => {
            let parse_date = new Date(today.valueOf() - 400 * 1000 * 60 * 60 * 24).toISOString().slice(0, 10);
            return fetchTWStockAPIData({
                dataset: "TaiwanStockMonthRevenue",
                data_id: stockNo,
                start_date: parse_date,
                end_date: ""
            }).then((response) => response.json())
                .then((response) => {
                    let dataLength = response.data.length - 1;
                    let revenueSets = [];

                    for (let i = dataLength; i >= 0; i--) {
                        revenueSets.unshift([`${response.data[i].revenue_year}年${response.data[i].revenue_month}月`, response.data[i].revenue / 100000000])
                    }

                    revenueDataSetter(() => {
                        return response;
                    })

                    setRevenueSeries(() => {
                        return {
                            series: [{
                                name: '營收(億)',
                                showInLegend: true,
                                data: revenueSets,
                                color: '#F38D07'
                            },]
                        }
                    })

                    setIsLoading(false);
                })
        })

    useEffect(() => {
        fetchData(stockNo)
        setIsLoading(true);
    }, [stockNo])

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
                                width: 450
                            },
                            title: {
                                text: '近一年每月營收(億)'
                            },
                            xAxis: {
                                type: 'category'
                            },
                            yAxis: {
                                title: {
                                    text: '營收(億)'
                                },
                            },
                            tooltip: {
                                split: true,
                                valueDecimals: 0,
                                valueSuffix: '億',
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
                            ...revenueSeries
                        }} />
                </div>
            </>

            }
        </>

    )
}

export default RevenueChart
