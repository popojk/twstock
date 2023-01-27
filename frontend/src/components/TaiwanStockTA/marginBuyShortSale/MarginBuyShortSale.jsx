import { useParams } from 'react-router-dom';

function MarginBuyShortSale() {
    const { id } = useParams();

    return (
        <div>
            <MarginPurchaseShortSale stockNo={id} />
        </div>
    )
}

export default MarginBuyShortSale;