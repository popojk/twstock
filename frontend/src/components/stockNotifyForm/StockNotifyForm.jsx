import { useSelector, useDispatch } from 'react-redux';
import TopButtons from '../TaiwanStockTA/TopButtons';
import TopBasicInfo from '../TaiwanStockTA/topBasicInfo/BasicInfo';
import { useState, useEffect } from 'react';
import { createStockNotifyListItem, getAllStockNotifyList } from '../../features/StockNotify/stockNotifySlice';
import StockNotifyItem from '../StockNotifyItem';
import stockNames from '../TaiwanStockTA/topBasicInfo/stockName.json';
import ReactLoading from "react-loading";

function StockNotifyForm(param) {
    const { stockNotifyList, isLoading, isError, isSuccess, message } = useSelector((state) => state.stockNotify)
    const [stockId] = useState(param.stockNo);
    const [stockName] = useState(stockNames[param.stockNo]);
    const [strat, setStrat] = useState('');
    const [targetPrice, setTargetPrice] = useState('');
    const [notifyMessage, setNotifyMessage] = useState('');

    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(getAllStockNotifyList());
    }, [])

    const onSubmit = async (e) => {
        e.preventDefault()
        await dispatch(createStockNotifyListItem({ stockId, stockName, strat, targetPrice, notifyMessage }))
        await dispatch(getAllStockNotifyList());
        onClear();
    }

    const onClear = () => {
        setStrat('');
        setTargetPrice('');
        setNotifyMessage('');
    }

    return (
        <>
            {isLoading && <ReactLoading className="loading_icon" type={'spin'} color={'#89CCDF'} width={100} />}
            {!isLoading &&
                <>
                    <div className="stock_notify_crreate_form">
                        <form onSubmit={onSubmit}>
                            <div className="form_group_1">
                                <div className="button_div">
                                    <button type="button" className={strat === "breakThrough" ? "break_through_button_clicked" : "break_through_button"} onClick={() => setStrat('breakThrough')}>
                                        突破
                                    </button>
                                </div>
                                <div className="button_div">
                                    <button type="button" className={strat === "tradingStop" ? "trading_stop_button_clicked" : "trading_stop_button"} onClick={() => setStrat('tradingStop')}>
                                        跌破
                                    </button>
                                </div>
                            </div>
                            <div className="form_group_2">
                                <label>股票代號</label>
                                <input type="text" value={stockId} disabled />
                                <div>{stockNames[param.stockNo]}</div>
                            </div>
                            <div className="form_group_3">
                                <label>目標價格</label>
                                <input type="tel"
                                    id="target_price"
                                    name="target_price"
                                    value={targetPrice}
                                    onChange={(e) => setTargetPrice(e.target.value)}
                                    placeholder='請輸入目標價'
                                    required
                                />
                            </div>
                            <div className="form_group_4">
                                <label>通知內容</label>
                                <textarea

                                    name='message'
                                    id='message'
                                    className='message'
                                    placeholder='請輸入文字'
                                    value={notifyMessage}
                                    onChange={(e) => setNotifyMessage(e.target.value)}
                                />
                            </div>
                            <div className="form_group_5">
                                <button className="form_group_5_submit">提交</button>
                                <button className="form_group_5_clear" onClick={onClear}>清除</button>
                            </div>
                        </form>
                    </div>
                    <div className="stock_notify_list">
                        <title>我的通知清單</title>
                        <div className="stock_notify_list_head">
                            <div className="notify_item_1">股票名稱</div>
                            <div className="notify_item_2">股票代號</div>
                            <div className="notify_item_3">通知種類</div>
                            <div className="notify_item_4">目標價</div>
                            <div className="notify_item_5">通知內容</div>
                            <div className="notify_item_modify">修改</div>
                            <div className="notify_item_delete">刪除</div>
                            <div></div>
                        </div>
                        {Array.from(stockNotifyList).map((stockNotifyItem) => (
                            <StockNotifyItem key={stockNotifyItem.id} stockName={stockNames[stockNotifyItem.stockId]} stockNotifyListItem={stockNotifyItem} />
                        ))}

                    </div>
                </>
            }
        </>
    )
}

export default StockNotifyForm
