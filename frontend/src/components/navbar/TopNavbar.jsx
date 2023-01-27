import Navbar from 'react-bootstrap/Navbar';
import styles from './Navbar.module.css';
import svgIcon from './navbaricon.svg';
import Title from '../TaiwanStockTA/Title/Title';
import Nav from 'react-bootstrap/Nav';
import { useState } from 'react';
import { Link, Navigate, useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { reset, logout } from '../../features/auth/authSlice';
import fetchTWStockAPIData from '../../API/twStockAPI';

function TopNavbar(props) {

    const dispatch = useDispatch();
    const { user } = useSelector((state) => state.auth)
    let navigate = useNavigate();

    const checkStockNoOrStockName = (stockNoOrName) => {
        var pattern = new RegExp("[0-9]+");
        if (pattern.test(stockNoOrName)) {
            return true;
        }
        return false;
    }


    const stockNoChangeHandler = (enteredStockNoOrName) => {
        navigate(`/stock_detail/${enteredStockNoOrName}`);
    }

    const onLogout = () => {
        dispatch(logout());
        dispatch(reset());
        Navigate('/home');
    }


    return (
        <Navbar className={styles.navbar}>
            <Navbar.Brand className={styles.title} href="/home">
                <img
                    alt=""
                    src={svgIcon}
                    width="30"
                    height="30"
                    className="d-inline-block align-top"
                />{' '}<span className="navbar_title">台股小幫手</span>
            </Navbar.Brand>
            <div className="nav_search_bar">
                {user && <Title stocknochange={stockNoChangeHandler} user={user} />}
            </div>
            <div className="username_and_logout_button">
                {user && <h1 className="username">{user.username}</h1>}
                {user && <button className="logout_button" onClick={onLogout}>Logout</button>}
            </div>
        </Navbar>
    )
}

export default TopNavbar
