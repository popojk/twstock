import TopTwentyTradeStock from "../mainpage/TopTwentyTradeStock"
import Index from "../components/TaiwanStockTA/Index"
import TAIEXChart from "../components/TAIEXChart"
import TPEXChart from "../components/TPEXChart"
import TAIEXDayTradeChart from "../components/stockNotifyForm/TAIEXDayTradeChart"
import TPEXDayTradeChart from "../components/TPEXDayTradeChart"
import TopAndLastTenIndex from "../components/TopAndLastTenIndex"


function Home() {
    return (
        <div className="home_page">
            <div className="market_index_charts">
                <div className="TAIEX">
                    <TAIEXDayTradeChart />
                </div>
                <div className="tpex">
                    <TPEXDayTradeChart />
                </div>
            </div>
            <div>
                <TopAndLastTenIndex />
            </div>
        </div>
    )
}

export default Home
