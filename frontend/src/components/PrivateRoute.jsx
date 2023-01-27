import { Navigate, Outlet } from "react-router-dom";
import { useAuthStatus } from "../hooks/useAuthStatus";
import ReactLoading from "react-loading";

function PrivateRoute() {
    const { loggedIn, checkingStatus } = useAuthStatus()

    if (checkingStatus) {
        return <ReactLoading />
    }

    return loggedIn ? <Outlet /> : <Navigate to='/user/login' />
}

export default PrivateRoute



