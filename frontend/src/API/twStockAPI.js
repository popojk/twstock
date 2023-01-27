
const FINMIND_URL = process.env.REACT_APP_FINMINDAPI_URL;
const FINMIND_TOKEN = process.env.REACT_APP_FINMIND_TOKEN;
const BACKEND_URL = process.env.REACT_APP_BACKEND_URL;


const fetchTWStockAPIData = ({ dataset, data_id, start_date, end_date }) => {
    const user = JSON.parse(localStorage.getItem('user'));
    return fetch(`${BACKEND_URL}/stock/stats?dataset=${dataset}&stock_id=${data_id}&start_date=${start_date}&end_date=${end_date}`, {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${user.access_token}`
        }
    })
}

export default fetchTWStockAPIData