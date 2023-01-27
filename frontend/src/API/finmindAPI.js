
const FINMIND_URL = process.env.REACT_APP_FINMINDAPI_URL;
const FINMIND_TOKEN = process.env.REACT_APP_FINMIND_TOKEN;
const BACKEND_URL = process.env.REACT_APP_BACKEND_URL;

const api = ({ dataset, data_id, start_date, end_date }) => {
    return fetch(`${FINMIND_URL}?dataset=${dataset}&data_id=${data_id}&start_date=${start_date}&end_date=${end_date}&token=${FINMIND_TOKEN}`, {
        headers: {
            'Target-URL': 'https://api.finmindtrade.com/api/v4',
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    })
}

export default api;


