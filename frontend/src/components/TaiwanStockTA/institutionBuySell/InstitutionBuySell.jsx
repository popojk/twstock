import { useCallback, useState, useEffect } from 'react';
import api from '../../../API/finmindAPI';
import ReactLoading from "react-loading";
import fetchTWStockAPIData from '../../../API/twStockAPI';

function InstitutionBuySell(props) {
    const today = new Date();
    const [lastTradingDateInstitutionBuySell, setLastTradingDateInstitutionBuySell] = useState({});
    const [isLoading, setIsLoading] = useState(true);

    const fetchData = useCallback(
        async (stockNo) => {
            let parse_date = new Date(today.valueOf() - 380 * 1000 * 60 * 60 * 24).toISOString().slice(0, 10);
            return fetchTWStockAPIData({
                dataset: "TaiwanStockInstitutionalInvestorsBuySell",
                data_id: stockNo,
                start_date: parse_date,
                end_date: ""
            })
                .then((response) => (response.json()))
                .then((response) => {
                    let lastTradingDateData = []
                    for (let i = response.data.length - 1; i >= response.data.length - 5; i--) {
                        lastTradingDateData.push(response.data[i])
                    }
                    setLastTradingDateInstitutionBuySell(() => {
                        return {
                            lastTradingDate: lastTradingDateData[0].date,
                            foreignInvesters: {
                                buy: Math.round(lastTradingDateData[4].buy / 1000),
                                sell: Math.round(lastTradingDateData[4].sell / 1000),
                                overBought: Math.round((lastTradingDateData[4].buy - lastTradingDateData[4].sell) / 1000)

                            },
                            investmentTrust: {
                                buy: Math.round(lastTradingDateData[2].buy / 1000),
                                sell: Math.round(lastTradingDateData[2].sell / 1000),
                                overBought: Math.round((lastTradingDateData[2].buy - lastTradingDateData[2].sell) / 1000)
                            },
                            dealerSelf: {
                                buy: Math.round((lastTradingDateData[0].buy + lastTradingDateData[1].buy) / 1000),
                                sell: Math.round((lastTradingDateData[0].sell + lastTradingDateData[1].sell) / 1000),
                                overBought: Math.round(((lastTradingDateData[0].buy + lastTradingDateData[1].buy) - (lastTradingDateData[0].sell + lastTradingDateData[1].sell)) / 1000)
                            }
                        }
                    })
                    setIsLoading(false);
                })

        }
        , [props.stockNo])

    useEffect(() => {
        fetchData(props.stockNo)
        setIsLoading(true);
    }
        , [props.stockNo])



    return (
        <>
            {isLoading && <ReactLoading type={'spin'} color={'#89CCDF'} width={100} />}
            {!isLoading &&
                <div className="institution_table">
                    <table width="50%" className="institution_table_table">
                        <caption className="institution_table_table_caption">
                            <h2 className="institution_table_h2">三大法人買賣超張數({lastTradingDateInstitutionBuySell.lastTradingDate})</h2>
                        </caption>
                        <thead className="institution_table_thead">
                            <tr className="institution_table_tr">
                                <th className="institution_table_th">
                                    三大法人
                                </th>
                                <th className="institution_table_th">
                                    買進
                                </th>
                                <th className="institution_table_th">
                                    賣出
                                </th>
                                <th className="institution_table_th">
                                    買超
                                </th>
                            </tr>
                        </thead>
                        <tbody className="institution_table_tbody">
                            <tr className="institution_table_tr">
                                <td className="institution_table_td">外資</td>
                                <td className="institution_table_td">{lastTradingDateInstitutionBuySell.foreignInvesters.buy.toLocaleString('en-US')}</td>
                                <td className="institution_table_td">{lastTradingDateInstitutionBuySell.foreignInvesters.sell.toLocaleString('en-US')}</td>
                                <td className={lastTradingDateInstitutionBuySell.foreignInvesters.overBought > 0 ?
                                    "up" : "down"}>
                                    {lastTradingDateInstitutionBuySell.foreignInvesters.overBought.toLocaleString('en-US')}</td>
                            </tr>
                            <tr className="institution_table_tr">
                                <td className="institution_table_td">投信</td>
                                <td className="institution_table_td">{lastTradingDateInstitutionBuySell.investmentTrust.buy.toLocaleString('en-US')}</td>
                                <td className="institution_table_td">{lastTradingDateInstitutionBuySell.investmentTrust.sell.toLocaleString('en-US')}</td>
                                <td className={lastTradingDateInstitutionBuySell.investmentTrust.overBought > 0 ?
                                    "up" : "down"}>
                                    {lastTradingDateInstitutionBuySell.investmentTrust.overBought.toLocaleString('en-US')}</td>
                            </tr>
                            <tr className="institution_table_tr">
                                <td className="institution_table_td">自營商</td>
                                <td className="institution_table_td">{lastTradingDateInstitutionBuySell.dealerSelf.buy.toLocaleString('en-US')}</td>
                                <td className="institution_table_td">{lastTradingDateInstitutionBuySell.dealerSelf.sell.toLocaleString('en-US')}</td>
                                <td className={lastTradingDateInstitutionBuySell.dealerSelf.overBought > 0 ? "up" : "down"}>
                                    {lastTradingDateInstitutionBuySell.dealerSelf.overBought.toLocaleString('en-US')}</td>
                            </tr>
                        </tbody>
                    </table>

                </div>
            }</>
    )
}

export default InstitutionBuySell
