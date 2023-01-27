import ReactLoading from "react-loading";
import { useState, useEffect, useCallback } from "react";
import fetchTopAndLastTenIndex from "../API/TopAndLastTenIndexAPI";

function TopAndLastTenIndex() {
    const [isLoading, setIsLoading] = useState(true);
    const [topTenIndexData, setTopTenIndexData] = useState([]);
    const [lastTenIndexData, setLastTenIndexData] = useState([]);

    const fetchData = useCallback(
        async () => {
            return fetchTopAndLastTenIndex()
                .then((response) => response.json())
                .then((response) => {
                    setTopTenIndexData(response.topTenList);
                    setLastTenIndexData(response.lastTenList);
                    setIsLoading(false);
                })
        }, [topTenIndexData, lastTenIndexData]
    )

    useEffect(async () => {
        fetchData();
    }, [])

    return (
        <>
            {isLoading && <ReactLoading type={'spin'} color={'#89CCDF'} width={100} />}
            {!isLoading &&
                <div className="stock_index_table">
                    <table className="top_ten_index_table">
                        <caption className="stock_index_caption"><h2>強勢類股</h2></caption>
                        <thead className="stock_index_thead">
                            <tr className="stock_index_tr">
                                <th className="stock_index_th">No</th>
                                <th className="stock_index_th">類股名稱</th>
                                <th className="stock_index_th">漲跌幅％</th>
                                <th className="stock_index_th">No</th>
                                <th className="stock_index_th">類股名稱</th>
                                <th className="stock_index_th">漲跌幅％</th>
                            </tr>
                        </thead>
                        <tbody className="stock_index_tbody">
                            <tr className="stock_index_tr">
                                <td className="stock_index_td">1</td>
                                <td className="stock_index_td">{Object.keys(topTenIndexData[0])}</td>
                                <td className="stock_index_td"><span className={Object.values(topTenIndexData[0]) > 0 ? "up" : "down"}>{Object.values(topTenIndexData[0])}%</span></td>
                                <td className="stock_index_td">6</td>
                                <td className="stock_index_td">{Object.keys(topTenIndexData[5])}</td>
                                <td className="stock_index_td"><span className={Object.values(topTenIndexData[5]) > 0 ? "up" : "down"}>{Object.values(topTenIndexData[5])}%</span></td>
                            </tr>
                            <tr className="stock_index_tr">
                                <td className="stock_index_td">2</td>
                                <td className="stock_index_td">{Object.keys(topTenIndexData[1])}</td>
                                <td className="stock_index_td"><span className={Object.values(topTenIndexData[1]) > 0 ? "up" : "down"}>{Object.values(topTenIndexData[1])}%</span></td>
                                <td className="stock_index_td">7</td>
                                <td className="stock_index_td">{Object.keys(topTenIndexData[6])}</td>
                                <td className="stock_index_td"><span className={Object.values(topTenIndexData[6]) > 0 ? "up" : "down"}>{Object.values(topTenIndexData[6])}%</span></td>
                            </tr>
                            <tr className="stock_index_tr">
                                <td className="stock_index_td">3</td>
                                <td className="stock_index_td">{Object.keys(topTenIndexData[2])}</td>
                                <td className="stock_index_td"><span className={Object.values(topTenIndexData[2]) > 0 ? "up" : "down"}>{Object.values(topTenIndexData[2])}%</span></td>
                                <td className="stock_index_td">8</td>
                                <td className="stock_index_td">{Object.keys(topTenIndexData[7])}</td>
                                <td className="stock_index_td"><span className={Object.values(topTenIndexData[7]) > 0 ? "up" : "down"}>{Object.values(topTenIndexData[7])}%</span></td>
                            </tr>
                            <tr className="stock_index_tr">
                                <td className="stock_index_td">4</td>
                                <td className="stock_index_td">{Object.keys(topTenIndexData[3])}</td>
                                <td className="stock_index_td"><span className={Object.values(topTenIndexData[3]) > 0 ? "up" : "down"}>{Object.values(topTenIndexData[3])}%</span></td>
                                <td className="stock_index_td">9</td>
                                <td className="stock_index_td">{Object.keys(topTenIndexData[8])}</td>
                                <td className="stock_index_td"><span className={Object.values(topTenIndexData[8]) > 0 ? "up" : "down"}>{Object.values(topTenIndexData[8])}%</span></td>
                            </tr>
                            <tr className="stock_index_tr">
                                <td className="stock_index_td">5</td>
                                <td className="stock_index_td">{Object.keys(topTenIndexData[4])}</td>
                                <td className="stock_index_td"><span className={Object.values(topTenIndexData[4]) > 0 ? "up" : "down"}>{Object.values(topTenIndexData[4])}%</span></td>
                                <td className="stock_index_td">10</td>
                                <td className="stock_index_td">{Object.keys(topTenIndexData[9])}</td>
                                <td className="stock_index_td"><span className={Object.values(topTenIndexData[9]) > 0 ? "up" : "down"}>{Object.values(topTenIndexData[9])}%</span></td>
                            </tr>
                        </tbody>
                    </table>

                    <table className="last_ten_index_table">
                        <caption className="stock_index_caption"><h2>弱勢類股</h2></caption>
                        <thead className="stock_index_thead">
                            <tr className="stock_index_tr">
                                <th className="stock_index_th">No</th>
                                <th className="stock_index_th">類股名稱</th>
                                <th className="stock_index_th">漲跌幅％</th>
                                <th className="stock_index_th">No</th>
                                <th className="stock_index_th">類股名稱</th>
                                <th className="stock_index_th">漲跌幅％</th>
                            </tr>
                        </thead>
                        <tbody className="stock_index_tbody">
                            <tr className="stock_index_tr">
                                <td className="stock_index_td">1</td>
                                <td className="stock_index_td">{Object.keys(lastTenIndexData[0])}</td>
                                <td className="stock_index_td"><span className={Object.values(lastTenIndexData[0]) > 0 ? "up" : "down"}>{Object.values(lastTenIndexData[0])}%</span></td>
                                <td className="stock_index_td">6</td>
                                <td className="stock_index_td">{Object.keys(lastTenIndexData[5])}</td>
                                <td className="stock_index_td"><span className={Object.values(lastTenIndexData[5]) > 0 ? "up" : "down"}>{Object.values(lastTenIndexData[5])}%</span></td>
                            </tr>
                            <tr className="stock_index_tr">
                                <td className="stock_index_td">2</td>
                                <td className="stock_index_td">{Object.keys(lastTenIndexData[1])}</td>
                                <td className="stock_index_td"><span className={Object.values(lastTenIndexData[1]) > 0 ? "up" : "down"}>{Object.values(lastTenIndexData[1])}%</span></td>
                                <td className="stock_index_td">7</td>
                                <td className="stock_index_td">{Object.keys(lastTenIndexData[6])}</td>
                                <td className="stock_index_td"><span className={Object.values(lastTenIndexData[6]) > 0 ? "up" : "down"}>{Object.values(lastTenIndexData[6])}%</span></td>
                            </tr>
                            <tr className="stock_index_tr">
                                <td className="stock_index_td">3</td>
                                <td className="stock_index_td">{Object.keys(lastTenIndexData[2])}</td>
                                <td className="stock_index_td"><span className={Object.values(lastTenIndexData[2]) > 0 ? "up" : "down"}>{Object.values(lastTenIndexData[2])}%</span></td>
                                <td className="stock_index_td">8</td>
                                <td className="stock_index_td">{Object.keys(lastTenIndexData[7])}</td>
                                <td className="stock_index_td"><span className={Object.values(lastTenIndexData[7]) > 0 ? "up" : "down"}>{Object.values(lastTenIndexData[7])}%</span></td>
                            </tr>
                            <tr className="stock_index_tr">
                                <td className="stock_index_td">4</td>
                                <td className="stock_index_td">{Object.keys(lastTenIndexData[3])}</td>
                                <td className="stock_index_td"><span className={Object.values(lastTenIndexData[3]) > 0 ? "up" : "down"}>{Object.values(lastTenIndexData[3])}%</span></td>
                                <td className="stock_index_td">9</td>
                                <td className="stock_index_td">{Object.keys(lastTenIndexData[8])}</td>
                                <td className="stock_index_td"><span className={Object.values(lastTenIndexData[8]) > 0 ? "up" : "down"}>{Object.values(lastTenIndexData[8])}%</span></td>
                            </tr>
                            <tr className="stock_index_tr">
                                <td className="stock_index_td">5</td>
                                <td className="stock_index_td">{Object.keys(lastTenIndexData[4])}</td>
                                <td className="stock_index_td"><span className={Object.values(lastTenIndexData[4]) > 0 ? "up" : "down"}>{Object.values(lastTenIndexData[4])}%</span></td>
                                <td className="stock_index_td">10</td>
                                <td className="stock_index_td">{Object.keys(lastTenIndexData[9])}</td>
                                <td className="stock_index_td"><span className={Object.values(lastTenIndexData[9]) > 0 ? "up" : "down"}>{Object.values(lastTenIndexData[9])}%</span></td>
                            </tr>
                        </tbody>
                    </table>
                </div>}
        </>
    )
}

export default TopAndLastTenIndex
