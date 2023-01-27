import { Link } from 'react-router-dom'

function TopButtons(props) {
    return (
        <div className="stock_top_buttons">
            <Link to={`/stock_detail/${props.stockNo}`}>
                <button className="button-8" role="button">個股概況</button>
            </Link>
            <Link to={`/stock_detail/${props.stockNo}/institutionstats`}>
                <button className="button-8" role="button">法人動態</button>
            </Link>
            <Link to={`/stock_detail/${props.stockNo}/marginshort`}>
                <button className="button-8" role="button">券資變化</button>
            </Link>
            <Link to={`/stock_detail/${props.stockNo}/stocknotify`}>
                <button className="button-8" role="button">觸價通知</button>
            </Link>
        </div>
    )
}

export default TopButtons
