import { useParams } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate } from "react-router";
import TopButtons from '../components/TaiwanStockTA/TopButtons';
import TopBasicInfo from '../components/TaiwanStockTA/topBasicInfo/BasicInfo';
import { useState, useEffect } from 'react';
import { login } from '../features/auth/authSlice';
import StockNotifyForm from '../components/stockNotifyForm/StockNotifyForm';
import TwStockQRCode from '../static/TwStockQRCode.png';

function StockNotify() {
    const { id } = useParams();

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const { user, isLoading, isError, isSuccess, message } = useSelector(state => state.auth);
    const [userLineId, setUserLineId] = useState("");

    useEffect(() => {
        if (user) {
            if (user.userlineid) {
                setUserLineId(user.userlineid);
            }
        }
    }, [user])

    const qrCodeOnclickHandler = () => {
        dispatch(login({ "username": user.username, "password": user.password }));
    }


    return (
        <>
            {!userLineId &&
                <>
                    <div className="qr_code_page">
                        <TopButtons stockNo={id} />
                        <img src={TwStockQRCode} className="tw_stock_qr_code" />
                        <h1 className="qr_code_wording">
                            請掃描QR Code加入官方帳號，<br />並傳送"account + 空格 + 您的帳號"
                        </h1>
                        <button className="qr_code_button"
                            onClick={() => qrCodeOnclickHandler()}
                        >已加入Line好友</button>
                    </div>
                </>
            }
            {userLineId &&
                <div className="stock_notify_page">
                    <TopBasicInfo stockNo={id} />
                    <TopButtons stockNo={id} />
                    <StockNotifyForm stockNo={id} />
                </div>}
        </>
    )
}

export default StockNotify
