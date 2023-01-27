import { useState, useEffect } from "react"
import { searchTopTwentyTradingStocks } from '../API/TWSEapi';

function TopTwentyTradeStock() {

    useEffect(() => {
        console.log(searchTopTwentyTradingStocks())
    }, [])

    return (
        <div>

        </div>
    )
}

export default TopTwentyTradeStock
