import React, { useCallback, useState, useEffect } from 'react';
import api from '../../../API/finmindAPI';
import Highcharts from 'highcharts/highstock';
import HighChartsReact from 'highcharts-react-official';
import ReactLoading from "react-loading";
import fetchTWStockAPIData from '../../../API/twStockAPI';

function EPSData(props) {
    const today = new Date();
    const [EPSSeries, setEPSSeries] = useState({});
    const [isLoading, setIsLoading] = useState(true);

    const quaterBuilder = (month) => {
        if (month === '12') {
            return 4
        } else if (month === '09') {
            return 3
        } else if (month === '06') {
            return 2
        }
        return 1

    }

    const fetchData = useCallback(
        async (stockNo) => {
            let parse_date = new Date(today.valueOf() - 500 * 1000 * 60 * 60 * 24).toISOString().slice(0, 10);
            return fetchTWStockAPIData({
                dataset: "TaiwanStockFinancialStatements",
                data_id: stockNo,
                start_date: parse_date,
                end_date: ""
            }).then((response) => response.json())
                .then((response) => {

                    let EPSDataSet = [];

                    response.data.filter((data) => data.type === 'EPS').map((data) =>
                        EPSDataSet.push([
                            `${data.date.slice(0, 4)} Q${quaterBuilder(data.date.slice(5, 7))}`
                            , data.value])
                    )

                    setEPSSeries(() => {
                        return {
                            series: [{
                                name: 'EPS',
                                showInLegend: true,
                                data: EPSDataSet,
                                color: '#5DBCEF'
                            },]
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
                <div className="eps_column">
                    <HighChartsReact
                        highcharts={Highcharts}
                        options={{
                            chart: {
                                type: 'column',
                                height: 240,
                                width: 220
                            },
                            title: {
                                text: '近一年每股盈餘(EPS)'
                            },
                            xAxis: {
                                type: 'category'
                            },
                            yAxis: {
                                title: {
                                    text: 'EPS'
                                },
                            },
                            tooltip: {
                                split: true,
                                valueDecimals: 2,
                                valueSuffix: '元',
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
                            ...EPSSeries
                        }} />
                </div>
            </>

            }
        </>
    )
}

export default EPSData
