import axios from 'axios';

const STOCK_NOTIFY_URL = 'https://twstock.japaneast.azurecontainer.io/api/notify';

//Get user from local storage
const user = JSON.parse(localStorage.getItem('user'))

//create new notify item
const createNotifyListItem = async (notifyItemData) => {
    //twstock api headers
    const config = {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${user.access_token}`
        }
    }

    const body = {
        "stockId": notifyItemData.stockId,
        "targetPrice": notifyItemData.targetPrice,
        "tradingStrat": notifyItemData.strat,
        "message": notifyItemData.notifyMessage,
        "userLineId": user.userlineid
    }
    const response = await axios.post(`${STOCK_NOTIFY_URL}/frontend`, body, config)
    return response.data;
}

//get notify list by user line id
const getAllNotifyList = async () => {
    //twstock api headers
    const config = {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${user.access_token}`
        }
    }
    const response = await axios.get(`${STOCK_NOTIFY_URL}/getnotifylistbylineid?userlineid=${user.userlineid}`, config);
    return response.data;
}

const deleteNotifyListById = async (id) => {
    //twstock api headers
    const config = {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${user.access_token}`
        }
    }
    const response = await axios.delete(`${STOCK_NOTIFY_URL}/delete?id=${id}`, config);
    return response.data;
}

//update notify list item by id
const updateNotifyListByItem = async (notifyItemUpdateData) => {
    //twstock api headers
    const config = {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${user.access_token}`
        }
    }
    const body = {
        "id": notifyItemUpdateData.id,
        "targetPrice": notifyItemUpdateData.updateTargetPrice,
        "strat": notifyItemUpdateData.updateStrat,
        "comment": notifyItemUpdateData.updateComment,
    }
    const response = await axios.put(`${STOCK_NOTIFY_URL}/update`, body, config)

}


const stockNotifyService = {
    createNotifyListItem,
    getAllNotifyList,
    deleteNotifyListById,
    updateNotifyListByItem,
}

export default stockNotifyService;