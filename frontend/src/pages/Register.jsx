import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { toast } from 'react-toastify';
import { FaUser } from "react-icons/fa";
import { register, reset } from "../features/auth/authSlice";

function Register() {
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: '',
        password2: ''
    })

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const { user, isLoading, isError, isSuccess, message } = useSelector(state => state.auth)
    const { username, email, password, password2 } = formData;


    useEffect(() => {
        if (isError) {
            toast.error(message)
        }
        //redirect when logged in
        if (isSuccess || user) {
            navigate('/home');
        }
        dispatch(reset);
    }
        , [isError, isSuccess, user, message, navigate, dispatch])

    const onChange = (e) => {
        setFormData((prevState) => ({
            ...prevState,
            [e.target.name]: e.target.value
        }))
    }

    const onSubmit = (e) => {
        e.preventDefault();
        if (password !== password2) {
            toast.error(message);
        } else {
            const userData = {
                username,
                password,
                email
            }
            dispatch(register(userData));
            if (isError) {
                toast.error(message);
            }
        }
    }

    const loginHandler = (e) => {
        navigate('/user/login');
    }

    return (
        <>
            <div className="login-page">
                <div className="wrapper">
                    <section className='register-heading'>
                        <h1>
                            <FaUser /> 註冊帳號
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
                            {message === "帳號已存在" && <h1 className="register-warning">{message}</h1>}
                            <div className="form-group">
                                <input type="email"
                                    className="form-control"
                                    id='email'
                                    name='email'
                                    value={email}
                                    onChange={onChange}
                                    placeholder='請輸入您的Email'
                                    required
                                />
                            </div>
                            {message === "Email已被註冊" && <h1 className="register-warning">{message}</h1>}
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
                            <div className="form-group">
                                <input type="password"
                                    className="form-control"
                                    id='password2'
                                    name='password2'
                                    value={password2}
                                    onChange={onChange}
                                    placeholder='請再次輸入您的密碼'
                                    required
                                />
                            </div>
                            <div className='register-and-login-button'>
                                <button className='submit-register'>提交</button>
                                <button className='submit-login'
                                    type="button"
                                    onClick={loginHandler}
                                >
                                    登入
                                </button>
                            </div>
                        </form>
                    </section>
                </div>
            </div>
        </>
    )
}

export default Register
