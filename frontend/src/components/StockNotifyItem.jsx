import { useState, useEffect } from "react";
import { useSelector, useDispatch } from 'react-redux';
import { FaTrash, FaSyncAlt, FaCheck, FaUndo } from 'react-icons/fa';
import { deleteStockNotifyItemById, updateStockNotifyListById, getAllStockNotifyList } from "../features/StockNotify/stockNotifySlice";

function StockNotifyItem(props) {
    const stockName = props.stockName;
    const { id, stockId, targetPrice, strat, comment } = props.stockNotifyListItem;
    const [onUpdate, setOnUpdate] = useState(false);
    const [updateTargetPrice, setUpdateTargetPrice] = useState(targetPrice);
    const [updateStrat, setUpdateStrat] = useState(strat);
    const [updateComment, setUpdateComment] = useState(comment);


    const dispatch = useDispatch();

    const deleteHandler = async () => {
        await dispatch(deleteStockNotifyItemById(id));
        await dispatch(getAllStockNotifyList());
    }

    const updateHandler = async () => {
        await dispatch(updateStockNotifyListById({ id, updateTargetPrice, updateStrat, updateComment }));
        setOnUpdate(false);
        await dispatch(getAllStockNotifyList());
    }

    return (
        <div className="stock_notify_items">
            <div className="notify_item_1">{stockName}</div>
            <div className="notify_item_2">{stockId}</div>
            <div className="notify_item_3">{
                onUpdate ?
                    <select
                        onChange={(e) => setUpdateStrat(e.target.value)}>
                        <option value="breakThrough">突破</option>
                        <option value="tradingStop">跌破</option>
                    </select>
                    : strat === "breakThrough" ? "突破" : "跌破"
            }
            </div>
            <div className="notify_item_4">
                {onUpdate ?
                    <input
                        type="tel"
                        value={updateTargetPrice}
                        onChange={(e) => setUpdateTargetPrice(e.target.value)}
                        placeholder='請輸入目標價'
                        required
                    />
                    : targetPrice}
            </div>
            <div className="notify_item_5">{onUpdate ?
                <input
                    type="text"
                    value={updateComment}
                    onChange={(e) => setUpdateComment(e.target.value)}
                    required
                />
                :
                comment}</div>
            {!onUpdate ?
                <div className="notify_item_6"><FaSyncAlt onClick={() => setOnUpdate(true)} /></div>
                :
                <div className="notify_item_6"><FaCheck onClick={() => updateHandler()} /></div>
            }
            {!onUpdate ?
                <div className="notify_item_7"><FaTrash onClick={() => deleteHandler()} /></div>
                :
                <div className="notify_item_7"><FaUndo onClick={() => setOnUpdate(false)} /></div>
            }
        </div>
    )
}

export default StockNotifyItem