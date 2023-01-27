import { useEffect, useState, useCallback } from "react";
import ReactLoading from "react-loading";

function RevenueTable(props) {
    const revenueData = props.revenueData.data;
    const [isLoading, setIsLoading] = useState(true);
    const [revenueDataSet, setRevenueDataSet] = useState({});

    const setRevenueData = useCallback(async () => {
        let dataLength = revenueData.length - 1;
        let currentMonthData = revenueData[dataLength];
        let currentYearAccumulatedRevenue = () => {
            let totalRevenue = 0;
            for (let i = dataLength; i >= 0; i--) {
                if (revenueData[i].revenue_year === currentMonthData.revenue_year) {
                    totalRevenue += revenueData[i].revenue;
                }
            }
            return Math.round(totalRevenue / 100000000).toLocaleString('en-US');
        }
        let currentRevenueData = currentYearAccumulatedRevenue();
        let MOM = Math.round(((currentMonthData.revenue - revenueData[dataLength - 1].revenue) / revenueData[dataLength - 1].revenue) * 10000) / 100;
        let YOY = Math.round(((currentMonthData.revenue - revenueData[0].revenue) / revenueData[0].revenue) * 10000) / 100;

        setRevenueDataSet(() => {
            return {
                currentMonthData,
                currentRevenueData,
                MOM,
                YOY
            }
        })
        setIsLoading(false);

    })

    useEffect(() => {
        setRevenueData();
    }, [props])


    return (
        <>
            {isLoading && <ReactLoading className="loading_icon" type={'spin'} color={'#89CCDF'} width={100} />}
            {!isLoading && <>
                <div className="revenue_table">
                    <div className="revenue_table_title">營業收入</div>
                    <div>
                        <ul className="revenue_table_ul">
                            <li className="revenue_table_ul_item">
                                <div>{revenueDataSet.currentMonthData.revenue_month}月營收</div>
                                <span>{Math.round(revenueDataSet.currentMonthData.revenue / 100000000)}億元</span>
                            </li>
                            <li className="revenue_table_ul_item">
                                <div>{revenueDataSet.currentMonthData.revenue_year}年營收</div>
                                <span>{revenueDataSet.currentRevenueData}億元</span>
                            </li>
                            <li className="revenue_table_ul_item">
                                <div>月增率</div>
                                <span className={revenueDataSet.MOM > 0 ? "revenue_table_ul_item_number_up" : "revenue_table_ul_item_number_down"}>{revenueDataSet.MOM}%</span>
                            </li>
                            <li className="revenue_table_ul_item">
                                <div>年增率</div>
                                <span className={revenueDataSet.YOY > 0 ? "revenue_table_ul_item_number_up" : "revenue_table_ul_item_number_down"}>{revenueDataSet.YOY}%</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </>}
        </>
    )
}

export default RevenueTable
