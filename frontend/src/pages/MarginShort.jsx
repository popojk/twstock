import { useParams } from 'react-router-dom';
import TopBasicInfo from '../components/TaiwanStockTA/topBasicInfo/BasicInfo';
import TopButtons from '../components/TaiwanStockTA/TopButtons';
import KBarChart from '../components/TaiwanStockTA/KBarChart/KBarChart';
import MarginPurchaseShortSale from '../components/TaiwanStockTA/marginPurchaseShortSale/MarginPurchaseShortSale';

function MarginShort() {
    const { id } = useParams();

    return (
        <div className="marginshort_page">
            <TopBasicInfo stockNo={id} />
            <TopButtons stockNo={id} />
            <KBarChart stockNo={id} />
            <div className="margin-short-table">
                <MarginPurchaseShortSale stockNo={id} />
            </div>
        </div>
    )
}

export default MarginShort
