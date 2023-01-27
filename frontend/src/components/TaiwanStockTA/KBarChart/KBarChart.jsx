import React, { useCallback, useState, useEffect } from 'react';
import ReactLoading from "react-loading";
import styles from './KBarChart.module.css';
import Highcharts from 'highcharts/highstock';
import HighChartsReact from 'highcharts-react-official';
import fetchTWStockAPIData from '../../../API/twStockAPI';

function KBarChart(props) {
    const today = new Date();
    const [chartSeries, setChartSeries] = useState({});
    const [isLoading, setIsLoading] = useState(true);

    const colorHandler = (close, open) => {
        if (close - open > 0) {
            return '#E00000'
        } else if (close - open === 0) {
            return '#FFFFFF';
        } else {
            return '#00D600';
        }

    }

    const fetchData = useCallback(
        async (stockNo) => {
            let parse_date = new Date(today.valueOf() - 380 * 1000 * 60 * 60 * 24).toISOString().slice(0, 10);
            return fetchTWStockAPIData({
                dataset: "TaiwanStockPrice",
                data_id: stockNo,
                start_date: parse_date,
                end_date: ""
            }).then((response) => response.json())
                .then((response) => {
                    let stockId = response.data[0].stock_id,
                        latestTwoClose = [],
                        ohlc = [],
                        volume = [],
                        dataLength = response.data.length,
                        groupingUnits = [
                            ['day', [1]],
                            ['week', [1]],
                            ['month', [1, 2, 3, 4, 5, 6]]
                        ]

                    for (let i = 0; i < dataLength; i++) {
                        ohlc.push({
                            x: new Date(response.data[i].date).getTime(),
                            open: response.data[i].open,
                            high: response.data[i].max,
                            low: response.data[i].min,
                            close: response.data[i].close,
                            color: colorHandler(response.data[i].close, response.data[i].open)
                        });

                        volume.push([
                            new Date(response.data[i].date).getTime(),
                            response.data[i].Trading_Volume
                        ]);

                        if (i === dataLength - 2) {
                            latestTwoClose.push(response.data[i].close)
                        }
                        if (i === dataLength - 1) {
                            latestTwoClose.push(response.data[i].close)
                        }
                    }

                    setChartSeries(() => {
                        return {
                            stockId,
                            series: [{
                                type: 'candlestick',
                                name: props.stockNo,
                                id: 'kBarChart',
                                data: ohlc,
                                yAxis: 0,
                                dataGrouping: {
                                    units: groupingUnits
                                }
                            }, {
                                type: 'column',
                                name: 'Volumn',
                                data: volume,
                                yAxis: 1,
                                dataGrouping: {
                                    units: groupingUnits
                                }
                            }
                            ],
                            spread_value: Math.round((latestTwoClose[1] - latestTwoClose[0]) * 100) / 100,
                            spread_percent: Math.round((latestTwoClose[1] - latestTwoClose[0]) / latestTwoClose[0] * 10000) / 100
                        }
                    })

                    setIsLoading(false);
                })
        }, [props]);

    useEffect(() => {
        setIsLoading(true);
        fetchData(props.stockNo)
    }, [props, fetchData])

    return (
        <>
            {isLoading && <ReactLoading className={styles.loading_icon} type={'spin'} color={'#89CCDF'} width={100} />}
            {!isLoading &&
                <>
                    <div className="kd-chart">
                        <HighChartsReact
                            highcharts={Highcharts}
                            constructorType={`stockChart`}
                            options={{
                                rangeSelector: {
                                    selected: 2
                                },

                                chart: {
                                    height: 400,
                                    width: 1000,
                                    marginLeft: 23
                                },

                                yAxis: [{
                                    labels: {
                                        align: 'right',
                                        x: -3
                                    },
                                    title: {
                                        text: 'OHLC'
                                    },
                                    height: '60%',
                                    lineWidth: 2,
                                    resize: {
                                        enabled: true
                                    }
                                }, {
                                    labels: {
                                        align: 'right',
                                        x: -3
                                    },
                                    title: {
                                        text: 'Volume'
                                    },
                                    top: '65%',
                                    height: '35%',
                                    offset: 0,
                                    lineWidth: 2
                                }],
                                tooltip: {
                                    split: true
                                },
                                ...chartSeries
                            }}
                        />
                    </div>
                </>
            }
        </>
    )
}

export default KBarChart
