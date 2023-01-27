import React, { useCallback, useState, useEffect } from 'react';
import finmindAPI from '../API/finmindAPI';
import ReactLoading from "react-loading";
import Highcharts from 'highcharts/highstock';
import HighChartsReact from 'highcharts-react-official';
import { useParams } from 'react-router-dom';
import TopButtons from '../components/TaiwanStockTA/TopButtons';
import TopBasicInfo from '../components/TaiwanStockTA/topBasicInfo/BasicInfo';
import fetchTWStockAPIData from '../API/twStockAPI';
import Index from '../components/TaiwanStockTA/Index';


function TA() {

    const today = new Date();
    const [chartSeries, setChartSeries] = useState({});
    const [isLoading, setIsLoading] = useState(true);
    const { id } = useParams();

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
                    let stockId = response[0].stock_id,
                        latestTwoClose = [],
                        ohlc = [],
                        volume = [],
                        dataLength = response.length,
                        groupingUnits = [
                            ['day', [1]],
                            ['week', [1]],
                            ['month', [1, 2, 3, 4, 5, 6]]
                        ]

                    for (let i = 0; i < dataLength; i++) {
                        ohlc.push({
                            x: new Date(response[i].date).getTime(),
                            open: response[i].open,
                            high: response[i].max,
                            low: response[i].min,
                            close: response[i].close,
                            color: colorHandler(response[i].close, response[i].open)
                        });

                        volume.push([
                            new Date(response[i].date).getTime(),
                            response[i].Trading_Volume
                        ]);

                        if (i === dataLength - 2) {
                            latestTwoClose.push(response[i].close)
                        }
                        if (i === dataLength - 1) {
                            latestTwoClose.push(response[i].close)
                        }
                    }

                    setChartSeries(() => {
                        return {
                            stockId,
                            series: [{
                                type: 'candlestick',
                                name: stockNo,
                                id: 'kBarChart',
                                yAxis: 0,
                                data: ohlc,
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
                            }, {
                                name: 'KD',
                                //data: [1, 2, 3, 4, 5, 6, 7, 8, 9],
                                //data: [9, 8, 7, 6, 5, 4, 3, 2, 1],
                                yAxis: 2,
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
        }, [id]);

    useEffect(() => {
        fetchData(id)
        setIsLoading(true);
    }, [id, fetchData])

    return (
        <>
            {isLoading && <ReactLoading className="loading_icon" type={'spin'} color={'#89CCDF'} width={100} />}
            {!isLoading &&
                <>
                    <TopBasicInfo stockNo={id} />
                    <TopButtons stockNo={id} />
                    <div className="ta_chart">
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
                                },
                                {
                                    labels: {
                                        align: 'right',
                                        x: -3
                                    },
                                    title: {
                                        text: 'KD'
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

export default TA
