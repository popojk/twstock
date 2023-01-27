import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import stockNotifyService from "./stockNotifyService";

const initialState = {
    stockNotify: {},
    stockNotifyList: [],
    isError: false,
    isSuccess: false,
    isLoading: false,
    message: ''
}

//create new stock notify list item
export const createStockNotifyListItem = createAsyncThunk('stockNotify/create', async (stockNotifyData, thunkAPI) => {
    try {
        return await stockNotifyService.createNotifyListItem(stockNotifyData);
    } catch (error) {
        const message = (error.response
            && error.response.data && error.response.data.message)
            || error.message || error.toString();
        return thunkAPI.rejectWithValue(message);
    }
});

//get all stock notify list
export const getAllStockNotifyList = createAsyncThunk('stockNotify/getAll', async (_, thunkAPI) => {
    try {
        return await stockNotifyService.getAllNotifyList();
    } catch (error) {
        const message = (error.response
            && error.response.data && error.response.data.message)
            || error.message || error.toString();
        return thunkAPI.rejectWithValue(message);
    }
})

//update stock notify item by id
export const updateStockNotifyListById = createAsyncThunk('stockNotify/update', async (stockNotifyUpdateData, thunkAPI) => {
    try {
        return await stockNotifyService.updateNotifyListByItem(stockNotifyUpdateData);
    } catch (error) {
        const message = (error.response
            && error.response.data && error.response.data.message)
            || error.message || error.toString();
        return thunkAPI.rejectWithValue(message);
    }
})

//delete stock notify item by id
export const deleteStockNotifyItemById = createAsyncThunk('stockNotify/delete', async (id, thunkAPI) => {
    try {
        return await stockNotifyService.deleteNotifyListById(id);
    } catch (error) {
        const message = (error.response
            && error.response.data && error.response.data.message)
            || error.message || error.toString();
        return thunkAPI.rejectWithValue(message);
    }
})

export const stockNotifySlice = createSlice({
    name: 'stockNotify',
    initialState,
    reducer: {
        reset: state => initialState
    },
    extraReducers: (builder) => {
        builder
            .addCase(createStockNotifyListItem.pending, (state) => {
                state.isLoading = true;
            })
            .addCase(createStockNotifyListItem.fulfilled, (state) => {
                state.isLoading = false;
                state.isSuccess = true;
            })
            .addCase(createStockNotifyListItem.rejected, (state, action) => {
                state.isSuccess = false;
                state.isError = true;
                state.message = action.payload;
            })
            .addCase(updateStockNotifyListById.pending, (state) => {
                state.isLoading = true;
            })
            .addCase(updateStockNotifyListById.fulfilled, (state) => {
                state.isLoading = false;
                state.isSuccess = true;
            })
            .addCase(updateStockNotifyListById.rejected, (state, action) => {
                state.isSuccess = false;
                state.isError = true;
                state.message = action.payload;
            })
            .addCase(getAllStockNotifyList.pending, (state) => {
                state.isLoading = true;
            })
            .addCase(getAllStockNotifyList.fulfilled, (state, action) => {
                state.isLoading = false;
                state.isSuccess = true;
                state.stockNotifyList = action.payload;
            })
            .addCase(getAllStockNotifyList.rejected, (state, action) => {
                state.isSuccess = false;
                state.isError = true;
                state.message = action.payload;
            })
            .addCase(deleteStockNotifyItemById.pending, (state) => {
                state.isLoading = true;
            })
            .addCase(deleteStockNotifyItemById.fulfilled, (state, action) => {
                state.isLoading = false;
                state.isSuccess = true;
                state.stockNotifyList = action.payload;
            })
            .addCase(deleteStockNotifyItemById.rejected, (state, action) => {
                state.isSuccess = false;
                state.isError = true;
                state.message = action.payload;
            })
    }
})

export const { reset } = stockNotifySlice.actions;
export default stockNotifySlice.reducer;