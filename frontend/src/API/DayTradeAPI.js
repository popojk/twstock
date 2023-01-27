
const FINMIND_URL = process.env.REACT_APP_FINMINDAPI_URL;
const FINMIND_TOKEN = process.env.REACT_APP_FINMIND_TOKEN;
const BACKEND_URL = process.env.REACT_APP_BACKEND_URL;


const fetchDayTradeData = (stockId) => {
    const user = JSON.parse(localStorage.getItem('user'));
    return fetch(`${BACKEND_URL}/stock/stats/daytradedata?stock_id=${stockId}`, {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${user.access_token}`
        }
    })
}

export default fetchDayTradeData;