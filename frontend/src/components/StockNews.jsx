import { useState, useEffect, useCallback } from "react";
import fetchTWStockAPIData from "../API/twStockAPI";
import ReactLoading from "react-loading";

function StockNews(props) {

    const today = new Date();
    const [stockNews, setStockNews] = useState();
    const [isLoading, setIsLoading] = useState(true);

    const fetchData = useCallback(
        async (stockNo) => {
            let parse_date = new Date(today.valueOf() - 10 * 1000 * 60 * 60 * 24).toISOString().slice(0, 10);
            return fetchTWStockAPIData({
                dataset: "TaiwanStockNews",
                data_id: stockNo,
                start_date: parse_date,
                end_date: ""
            }).then((response) => response.json())
                .then((response) => {
                    let twentyNewsList = [];
                    let dataLength = response.data.length;
                    for (let i = dataLength - 1; i >= dataLength - 50; i--) {
                        if (i === 0) {
                            twentyNewsList.push({ id: i, ...response.data[i] });
                        }
                        if (twentyNewsList.length <= 8 && i > 0) {
                            if (response.data[i - 1].title !== response.data[i].title)
                                twentyNewsList.push(response.data[i]);
                        }
                    }
                    setStockNews(twentyNewsList);
                    setIsLoading(false);
                })
        }
        , [])

    useEffect(() => {
        fetchData(props.stockId);
    }, [props])


    return (
        <>
            {isLoading && <ReactLoading type={'spin'} color={'#89CCDF'} width={100} />}
            {!isLoading &&
                <div className="stock_news_table">
                    <h1 className="stock_news_main_title">焦點新聞</h1>
                    <ul className="stock_news_list">
                        {Array.from(stockNews).map((news) => (
                            <a key={news.id} href={news.link} target="_blank">
                                <li className="stock_news_item">
                                    <span className="stock_news_date">{news.date}</span>
                                    <span className="stock_news_title">{news.title}</span>
                                </li>
                            </a>
                        ))}
                    </ul>
                </div>
            }
        </>
    )
}

export default StockNews
