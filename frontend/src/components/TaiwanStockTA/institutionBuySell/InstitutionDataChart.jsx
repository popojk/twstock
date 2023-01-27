import Highcharts from 'highcharts/highstock';
import HighChartsReact from 'highcharts-react-official';
import { useState, useEffect, useCallback } from 'react';
import api from '../../../API/finmindAPI';
import InstitutionDataService from '../../../Service/InstitutionDataService';
import ReactLoading from "react-loading";
import fetchTWStockAPIData from '../../../API/twStockAPI';

function InstitutionDataChart(props) {
    const today = new Date();
    const [isLoading, setIsLoading] = useState(true);
    const [foreignInvesterDataSeries, setForeignInvesterDataSreies] = useState({});
    const [investmentTrustDataSeries, setInvestmentTrustDataSreies] = useState({});
    const [dealerSelfDataSeries, setDealerSelfDataSreies] = useState({});
    const [firstDate, setFirstDate] = useState(0);
    const [lastDate, setLastDate] = useState(0);

    const fetchForeignInvesterData = useCallback(async (stockNo) => {
        const today = new Date();
        let parse_date = new Date(today.valueOf() - 100 * 1000 * 60 * 60 * 24).toISOString().slice(0, 10);
        return fetchTWStockAPIData({
            dataset: "TaiwanStockInstitutionalInvestorsBuySell",
            data_id: stockNo,
            start_date: parse_date,
            end_date: ""
        })
            .then((response) => (response.json()))
            .then((response) => {
                let foreignInvesterData = []
                let investmentTrustData = []
                let dealerSelfData = []

                response.data.filter(stat => stat.name === 'Foreign_Investor')
                    .map(dat => foreignInvesterData.push({
                        x: new Date(dat.date).getTime(), y: (dat.buy - dat.sell) / 1000,
                        color: dat.buy - dat.sell > 0 ? '#E00000' : '#00D600'
                    }));

                response.data.filter(stat => stat.name === 'Investment_Trust')
                    .map(dat => investmentTrustData.push({
                        x: new Date(dat.date).getTime(), y: (dat.buy - dat.sell) / 1000,
                        color: dat.buy - dat.sell > 0 ? '#E00000' : '#00D600'
                    }));

                response.data.filter(stat => stat.name === 'Dealer_self')
                    .map(dat => dealerSelfData.push({
                        x: new Date(dat.date).getTime(), y: (dat.buy - dat.sell) / 1000,
                        color: dat.buy - dat.sell > 0 ? '#E00000' : '#00D600'
                    }));

                setForeignInvesterDataSreies(() => {
                    return {
                        series: [{
                            name: '外資買/賣超',
                            showInLegend: false,
                            data: foreignInvesterData
                        }]
                    }
                })

                setInvestmentTrustDataSreies(() => {
                    return {
                        series: [{
                            name: '投信買/賣超',
                            showInLegend: false,
                            data: investmentTrustData
                        }]
                    }
                })

                setDealerSelfDataSreies(() => {
                    return {
                        series: [{
                            name: '自營商買/賣超',
                            showInLegend: false,
                            data: dealerSelfData
                        }]
                    }
                })

                setLastDate(foreignInvesterData[foreignInvesterData.length - 1].x);
                setFirstDate(foreignInvesterData[foreignInvesterData.length - 40].x);
                setIsLoading(false);
            })
    }, [props.stockNo])


    useEffect(() => {
        fetchForeignInvesterData(props.stockNo);
        setIsLoading(true);
    }, [props, fetchForeignInvesterData])

    return (
        <>
            {isLoading && <ReactLoading className="loading_icon" type={'spin'} color={'#89CCDF'} width={100} />}
            {!isLoading && <>
                <div className="institution_charts">
                    <HighChartsReact
                        highcharts={Highcharts}
                        options={{
                            chart: {
                                type: 'column',
                                width: 970,
                                height: 220,
                            },
                            title: {
                                text: '外資買/賣(張)'
                            },
                            xAxis: {
                                type: 'datetime',
                                ordinal: true,//skip blank columns
                                min: firstDate,
                                max: lastDate,
                                scrollbar: {
                                    enabled: true
                                },
                                /*events: {
                                    setExtremes() {
                                        this.min = 10 * 1000 * 60 * 60 * 24;
                                        this.max = 30 * 1000 * 60 * 60 * 24;
                                    }
                                }*/
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
                            credits: {
                                enabled: false
                            },
                            ...foreignInvesterDataSeries
                        }} />

                    <HighChartsReact
                        highcharts={Highcharts}
                        options={{
                            chart: {
                                type: 'column',
                                height: 220,
                                width: 970,
                            },
                            title: {
                                text: '投信買/賣(張)'
                            },
                            xAxis: {
                                type: 'datetime',
                                ordinal: true,//skip blank columns
                                min: firstDate,
                                max: lastDate,
                                scrollbar: {
                                    enabled: true
                                },
                                /*events: {
                                    setExtremes() {
                                        this.min = 10 * 1000 * 60 * 60 * 24;
                                        this.max = 30 * 1000 * 60 * 60 * 24;
                                    }
                                }*/
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
                            credits: {
                                enabled: false
                            },
                            ...investmentTrustDataSeries
                        }} />

                    <HighChartsReact
                        highcharts={Highcharts}
                        options={{
                            chart: {
                                type: 'column',
                                width: 970,
                                height: 220,
                            },
                            title: {
                                text: '自營商買/賣(張)'
                            },
                            xAxis: {
                                type: 'datetime',
                                ordinal: true,//skip blank columns
                                min: firstDate,
                                max: lastDate,
                                scrollbar: {
                                    enabled: true
                                },
                                /*events: {
                                    setExtremes() {
                                        this.min = 10 * 1000 * 60 * 60 * 24;
                                        this.max = 30 * 1000 * 60 * 60 * 24;
                                    }
                                }*/
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
                            credits: {
                                enabled: false
                            },
                            ...dealerSelfDataSeries
                        }} />
                </div>
            </>}
        </>
    )
}

export default InstitutionDataChart
