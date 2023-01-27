import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import jwtDecode from "jwt-decode";


export const useAuthStatus = () => {
    const [loggedIn, setLoggedIn] = useState(false);
    const [checkingStatus, setCheckingStatus] = useState(true);
    const { user } = useSelector((state) => state.auth);

    useEffect(() => {
        const date = Date.now()
        if (user) {
            if ((jwtDecode(user.access_token).exp * 1000) < date) {
                setLoggedIn(false);
            }
            setLoggedIn(true);
        } else {
            setLoggedIn(false);
        }
        setCheckingStatus(false);
    }, [user])
    return { loggedIn, checkingStatus }
}