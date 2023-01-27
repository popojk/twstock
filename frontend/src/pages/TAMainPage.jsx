import { useState } from 'react';
import { useParams } from 'react-router-dom';
import KBarChart from '../components/TaiwanStockTA/KBarChart/KBarChart';
import InstitutionBuySell from '../components/TaiwanStockTA/institutionBuySell/InstitutionBuySell';
import ThirtyDaysMarginBuyShortSale from '../components/TaiwanStockTA/marginPurchaseShortSale/ThirtyDaysMarginBuyShortSale';
import RevenueChart from '../components/TaiwanStockTA/Revenue/RevenueChart';
import RevenueTable from '../components/TaiwanStockTA/Revenue/RevenueTable';
import EPSData from '../components/TaiwanStockTA/EPSData/EPSData';
import TopButtons from '../components/TaiwanStockTA/TopButtons';
import TopBasicInfo from '../components/TaiwanStockTA/topBasicInfo/BasicInfo';
import Index from '../components/TaiwanStockTA/Index';
import StockNews from '../components/StockNews';


function TAMainPage() {
    const { id } = useParams();
    const [revenueData, setRevenueData] = useState({});

    const handleSetRevenueData = (revenueData => {
        setRevenueData(revenueData);
    })

    return (
        <>
            <div className="stock_main_page">
                <TopBasicInfo stockNo={id} />
                <TopButtons stockNo={id} />
                <KBarChart stockNo={id} />
                <div className="stock_main_first_div">
                    <InstitutionBuySell stockNo={id} />
                    <ThirtyDaysMarginBuyShortSale stockNo={id} />
                </div>
                <div className="stock_main_second_div">
                    <RevenueChart stockNo={id} revenueDataSetter={handleSetRevenueData} />
                    <RevenueTable stockNo={id} revenueData={revenueData} />
                    <EPSData stockNo={id} />
                </div>
                <div className="stock_main_third_div">
                    <StockNews stockId={id} />
                </div>
            </div>
        </>
    )
}

export default TAMainPage
