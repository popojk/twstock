import fetchDayTradeData from "../API/DayTradeAPI";
import { useState, useEffect, useCallback } from "react";
import Highcharts, { time } from 'highcharts/highstock';
import HighChartsReact from 'highcharts-react-official';
import ReactLoading from "react-loading";

function TPEXDayTradeChart() {

    const [dayTradeChartData, setDayTradeChartData] = useState({});
    const [isLoading, setIsLoading] = useState(true);

    const fetchData = useCallback(
        async () => {
            return fetchDayTradeData("IX0043")
                .then((response) => response.json())
                .then((response) => {
                    const quote = response.data.chart.o;
                    const timeStamp = response.data.chart.t;
                    const dataLength = quote.length;
                    const dayTradeData = [];

                    for (let i = 0; i < dataLength; i++) {
                        let localeTimeObj = timeStamp[i] + (8 * 60 * 60 * 1000);
                        dayTradeData.push({
                            x: localeTimeObj,
                            y: quote[i]
                        })
                    }

                    setDayTradeChartData(() => {
                        return {
                            series: [{
                                name: '櫃買指數',
                                type: 'line',
                                data: dayTradeData
                            },]
                        }
                    })
                    setIsLoading(false);
                })
        }
        , []
    )

    useEffect(() => {
        fetchData();
    }, [])


    return (
        <>
            {isLoading && <ReactLoading type={'spin'} color={'#89CCDF'} width={100} />}
            {!isLoading &&
                <>
                    <div className="TPEX_daytrade_chart">
                        <HighChartsReact
                            highcharts={Highcharts}
                            constructorType={`stockChart`}
                            options={{
                                title: {
                                    text: '櫃買指數',
                                    style: {
                                        color: "#F7DC6F"
                                    }

                                },
                                navigator: {
                                    enabled: false
                                },
                                scrollbar: {
                                    enabled: false
                                },
                                plotOptions: {
                                    series: {
                                        cumulative: false,
                                        color: "#F7DC6F"
                                        //pointIntervalUnit: 'day'
                                    }
                                },
                                chart: {
                                    backgroundColor: "#454545",
                                    height: 300,
                                    width: 400,
                                    marginLeft: 10,
                                    margintop: 5,
                                    borderRadius: 10
                                },
                                rangeSelector: {
                                    enabled: false
                                },
                                xAxis: [{
                                    type: 'datetime',
                                    labels: {
                                        style: {
                                            color: "#FFFFFF"
                                        }
                                    }
                                }],
                                yAxis: [{
                                    labels: {
                                        style: {
                                            color: "#FFFFFF"
                                        }
                                    }
                                }],
                                tooltip: {
                                    split: true,
                                    valueDecimals: 2,
                                },
                                ...dayTradeChartData
                            }}
                        />
                    </div>
                </>
            }
        </>
    )
}

export default TPEXDayTradeChart
