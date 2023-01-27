import axios from "axios";

const TWSE = axios.create({
    baseURL: 'https://twstock.japaneast.azurecontainer.io/api/stock/search/exchange',

})

//get top 20 day trade stock
export const searchTopTwentyTradingStocks = async () => {
    const response = await TWSE.get('/gettoptwentytradingstock')
    return response;
}