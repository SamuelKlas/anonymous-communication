import {Route, Redirect, Navigate} from "react-router-dom";
import {isLoggedIn} from "./Utils";

const PrivateRoute = ({ loggedIn, ...props }) => {
    return isLoggedIn() === true
        ? <Route {...props} />
        : <Navigate replace to="/login" />
}

export default PrivateRoute