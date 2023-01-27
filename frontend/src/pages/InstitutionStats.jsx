import KBarChart from "../components/TaiwanStockTA/KBarChart/KBarChart"
import { useParams } from 'react-router-dom';
import InstitutionDataChart from "../components/TaiwanStockTA/institutionBuySell/InstitutionDataChart";
import TopButtons from '../components/TaiwanStockTA/TopButtons';
import TopBasicInfo from '../components/TaiwanStockTA/topBasicInfo/BasicInfo';
import Index from "../components/TaiwanStockTA/Index";


function InstitutionStats() {
    const { id } = useParams();

    return (
        <>
            <div className="institution_stats_page">
                <TopBasicInfo stockNo={id} />
                <TopButtons stockNo={id} />
                <KBarChart stockNo={id} />
                <InstitutionDataChart stockNo={id} />
            </div>
        </>
    )
}

export default InstitutionStats
