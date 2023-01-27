import axios from "axios";

const API_URL = 'https://twstock.japaneast.azurecontainer.io/api/auth'

//register user
const register = async (userData) => {
    const response = await axios.post(`${API_URL}/create`, userData)
    if (response.data) {
        localStorage.setItem('user', JSON.stringify(response.data))
        return response.data
    }
}

//login user
const login = async (userData) => {
    const response = await axios.post(`${API_URL}/login`, userData)
    if (response.data) {
        localStorage.setItem('user', JSON.stringify(response.data));
    }
    return response.data;
}

//logout user
const logout = () => localStorage.removeItem('user');


const authService = {
    register,
    login,
    logout,
}

export default authService;