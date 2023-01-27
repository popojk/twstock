import { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import { FaSignInAlt } from "react-icons/fa";
import { useSelector, useDispatch } from "react-redux";
import { reset, login } from "../features/auth/authSlice";
import { toast } from "react-toastify";

function Login() {
    const [formData, setFormData] = useState({
        username: '',
        password: ''
    })

    const { username, password } = formData;

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const { user, isLoading, isError, isSuccess, message } = useSelector(state => state.auth)

    useEffect(() => {
        if (isError) {
            toast.error(message)
        }
        //Redirect when logged in
        if (isSuccess || user) {
            navigate('/home');
        }
        dispatch(reset);
    }, [isError, isSuccess, user, message, navigate, dispatch])

    const onChange = (e) => {
        setFormData((prevState) => ({
            ...prevState,
            [e.target.name]: e.target.value
        }))
    }

    const onSubmit = (e) => {
        e.preventDefault();
        const userData = {
            username,
            password
        }
        dispatch(login(userData));
    }

    const registerPageHandler = (e) => {
        navigate('/user/register');
    }

    return (
        <>
            <div className="login-page">
                <div className="wrapper">
                    <section className='login-heading'>
                        <h1>
                            <FaSignInAlt /> 登入台股小幫手會員
                        </h1>
                    </section>
                    <section className='form'>
                        <form onSubmit={onSubmit}>

                            <div className="form-group">
                                <input type="text"
                                    className="form-control"
                                    id='username'
                                    name='username'
                                    value={username}
                                    onChange={onChange}
                                    placeholder='請輸入帳號'
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <input type="password"
                                    className="form-control"
                                    id='password'
                                    name='password'
                                    value={password}
                                    onChange={onChange}
                                    placeholder='請輸入密碼'
                                    required
                                />
                            </div>
                            {message === "帳號或密碼錯誤" && <h1 className="login-warning">{message}</h1>}
                            <div className="login-and-register-button">
                                <div className='form-group'>
                                    <button className='login-button'>登入</button>
                                </div>
                                <div className='form-group'>
                                    <button className='register-button'
                                        type="button"
                                        onClick={registerPageHandler}>
                                        註冊
                                    </button>
                                </div>
                            </div>
                        </form>

                    </section>
                </div>
            </div>
        </>
    )
}

export default Login
