import { configureStore } from '@reduxjs/toolkit';
import stockNotifyReducer from '../features/StockNotify/stockNotifySlice'
import authReducer from '../features/auth/authSlice'

export const store = configureStore({
    reducer: {
        stockNotify: stockNotifyReducer,
        auth: authReducer,
    }
})