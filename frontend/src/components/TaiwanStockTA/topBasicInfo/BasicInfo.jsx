import React, { useCallback, useState, useEffect } from 'react';
import ReactLoading from "react-loading";
import api from '../../../API/finmindAPI';
import fetchTWStockAPIData from '../../../API/twStockAPI';
import styles from './TopBasicInfo.module.css';
import stockName from './stockName.json';

function TopBasicInfo(props) {
    const today = new Date();
    const [topBasicInfo, setTopBasicInfo] = useState({});
    const [isLoading, setIsLoading] = useState(true);

    const fetchData = useCallback(
        async (stockNo) => {
            let parse_date = new Date(today.valueOf() - 10 * 1000 * 60 * 60 * 24).toISOString().slice(0, 10);
            return fetchTWStockAPIData({
                dataset: "TaiwanStockPrice",
                data_id: stockNo,
                start_date: parse_date,
                end_date: ""
            }).then((response) => response.json())
                .then((response) => {
                    let lastTradingDateData = response.data[response.data.length - 1],
                        latestTwoClose = [],
                        dataLength = response.data.length

                    latestTwoClose.push(response.data[dataLength - 2].close);
                    latestTwoClose.push(response.data[dataLength - 1].close);

                    setTopBasicInfo(() => {
                        return {
                            open: (lastTradingDateData.open).toLocaleString('en-US'),
                            close: (lastTradingDateData.close).toLocaleString('en-US'),
                            max: (lastTradingDateData.max).toLocaleString('en-US'),
                            min: (lastTradingDateData.min).toLocaleString('en-US'),
                            last_close: (latestTwoClose[0]).toLocaleString('en-US'),
                            stockId: lastTradingDateData.stock_id,
                            volume: Math.round((lastTradingDateData.Trading_Volume / 1000)).toLocaleString('en-US'),
                            spreadValue: Math.round((latestTwoClose[1] - latestTwoClose[0]) * 100) / 100,
                            spread_percent: Math.round((latestTwoClose[1] - latestTwoClose[0]) / latestTwoClose[0] * 10000) / 100,
                            dayHighLowPercentage: Math.round((lastTradingDateData.max - lastTradingDateData.min) / latestTwoClose[0] * 10000) / 100
                        }
                    })
                    setIsLoading(false)
                })
        }
        , [props.stockNo])

    useEffect(() => {
        setIsLoading(true);
        fetchData(props.stockNo)
    }, [props, fetchData])

    return (
        <>
            {isLoading && <ReactLoading className={styles.loading_icon} type={'spin'} color={'#89CCDF'} width={100} />}
            {!isLoading &&
                <>

                    <div className={styles.price_container}>
                        <div className={styles.quate_info}>
                            <div className={styles.stock_name}>{stockName[topBasicInfo.stockId]}({topBasicInfo.stockId})</div>
                            <div className={styles.lastClose}>
                                <div className={styles.price}>{topBasicInfo.close}</div>
                                {topBasicInfo.spreadValue > 0 && <div className={`${styles.spread_value} ${styles.red_text}`}>+{topBasicInfo.spreadValue}</div>}
                                {topBasicInfo.spreadValue < 0 && <div className={`${styles.spread_value} ${styles.green_text}`}>{topBasicInfo.spreadValue}</div>}
                                {topBasicInfo.spreadValue > 0 && <div className={`${styles.spread_percent} ${styles.red_text}`}>(+{topBasicInfo.spread_percent}%)</div>}
                                {topBasicInfo.spreadValue < 0 && <div className={`${styles.spread_percent} ${styles.green_text}`}>({topBasicInfo.spread_percent}%)</div>}
                            </div>
                        </div>
                        <ul className={styles.lasty_detail}>
                            <li className={styles.lasty_detail_item}>
                                <div>開盤</div>
                                <span className={styles.lasty_detail_item_number}>{topBasicInfo.open}</span>
                            </li>
                            <li className={styles.lasty_detail_item}>
                                <div>最高</div>
                                <span className={styles.lasty_detail_item_number}>{topBasicInfo.max}</span>
                            </li>
                            <li className={styles.lasty_detail_item}>
                                <div>最低</div>
                                <span className={styles.lasty_detail_item_number}>{topBasicInfo.min}</span>
                            </li>
                            <li className={styles.lasty_detail_item}>
                                <div>震幅</div>
                                <span className={styles.lasty_detail_item_number}>{topBasicInfo.dayHighLowPercentage}%</span>
                            </li>
                            <li className={styles.lasty_detail_item}>
                                <div>成交量(張)</div>
                                <span className={styles.lasty_detail_item_number}>{topBasicInfo.volume}</span>
                            </li>
                            <li className={styles.lasty_detail_item}>
                                <div>昨收</div>
                                <span className={styles.lasty_detail_item_number}>{topBasicInfo.last_close}</span>
                            </li>

                        </ul>
                    </div>

                </>
            }

        </>
    )
}

export default TopBasicInfo
