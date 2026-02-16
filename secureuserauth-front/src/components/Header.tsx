import React from 'react'
import "../style/header.css"
import { useAuth } from '../auth/useAuth'
import { useNavigate } from 'react-router-dom';

function Header() {
    const { user, logoutToken, isAuthenticated, isAdmin } = useAuth();

    const navigate = useNavigate();

    function logout() {
        logoutToken();
        navigate("/", { replace: true });
    }

    return (
        <div className='header'>
            <div className='header-opts'>
                <h3>SecureUserAuth</h3>

                <ul className='font-big'>
                    <li><a href="/">Home</a></li>
                    {isAdmin && <li><a href="/user">User</a></li>}
                    {isAuthenticated && <li><a href="/profile">Profile</a></li>}
                </ul>
            </div>


            {isAuthenticated ?
                <div className='auth-opts font-big'>
                    <span>Welcome, {user?.name}</span>
                    <button type='button' onClick={logout}>Logout</button>
                </div>
                :
                <div className='auth-opts font-big'>
                    <ul>
                        <li><a href="/login">Login</a></li>
                        <li><a href="/register">Register</a></li>
                    </ul>
                </div>
            }
        </div>
    )
}

export default Header