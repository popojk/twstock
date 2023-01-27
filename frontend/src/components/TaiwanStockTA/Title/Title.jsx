import { useState } from 'react';
import styles from './Title.module.css';

function Title(props) {
    const [enteredStockNo, setEnteredStockNo] = useState('');
    const [responseData, setResponseData] = useState({})
    const BACKEND_URL = process.env.REACT_APP_BACKEND_URL;

    const stockNoChangeHandler = (e) => {
        setEnteredStockNo(e.target.value);
    };

    const submitHandler = async (e) => {
        e.preventDefault();
        await fetch(`${BACKEND_URL}/stockname/getname?stockid=${enteredStockNo}`, {
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': `Bearer ${props.user.access_token}`
            }
        }).then(response => {
            if (response.status === 404) {
                setEnteredStockNo('股票代號不存在');
            } else {
                props.stocknochange(enteredStockNo);
            }
        })
    }

    return (
        <div className={styles.titleBack}>
            <form onSubmit={submitHandler} className={styles.inputSection}>
                <input
                    placeholder="請輸入股票代號"
                    type="text"
                    className={styles.search_input}
                    value={enteredStockNo}
                    onChange={stockNoChangeHandler}
                />
            </form>
        </div>
    )
}

export default Title
