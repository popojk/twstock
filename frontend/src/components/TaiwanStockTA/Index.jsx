import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom';
import Title from './Title/Title';
import TAMainPage from '../../pages/TAMainPage';


function Index() {
    const [stockNo, setStockeNo] = useState('2330');
    let navigate = useNavigate();


    const stockNoChangeHandler = (enteredStockNo) => {
        setStockeNo(enteredStockNo);
        navigate(`/stock_detail/${enteredStockNo}`);
    }

    return (
        <>
            <div className=".stock_index">
                <Title stocknochange={stockNoChangeHandler} />
            </div>
        </>
    )
}

export default Index;

