import React, { useState } from 'react'
import type { LoginResponseDto } from '../dto/response/LoginResponseDto';
import { loginResponseNew } from '../new/LoginResponse';
import { useNavigate } from 'react-router-dom';
import { useMutation } from '@tanstack/react-query';
import { login } from '../service/AuthService';
import { useAuth } from '../auth/useAuth';
import { isUserRegisterError, type UserRegisterError } from '../error/error.types';

function Login() {
    const [loginResponse, setLoginResponse] = useState<LoginResponseDto>(loginResponseNew);
    const [errors, setErrors] = useState<UserRegisterError | null>(null);


    const { loginToken } = useAuth();

    const navigate = useNavigate();

    const handleFormChange = (e:
        React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement | null>
    ) => {
        const { name, value } = e.target;

        setLoginResponse({
            ...loginResponse,
            [name]: value,
        });
    };

    const { mutateAsync, isPending } = useMutation({
        mutationFn: login,
    });

    async function handleLogin(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();

        const payload = { ...loginResponse };

        try {
            const token = await mutateAsync(payload);
            console.log("Token created: " + token.data.token);
            loginToken(token.data.token);
            navigate("/", { replace: true });
        } catch (err: unknown) {
            if (isUserRegisterError(err)) {
                console.error("Validation errors:", err);
                setErrors(err);
            } else {
                console.error("Unexpected error:", err);
            }
        }

    }

    return (
        <div className='content'>
            <form onSubmit={handleLogin}>
                <h4>Login</h4>

                <div>
                    <div>
                        <label htmlFor="email">Email</label>
                        <input type="text" name='email' value={loginResponse.email} onChange={handleFormChange} />
                    </div>

                    <div>
                        <label htmlFor="password">Password</label>
                        <input type="password" name='password' value={loginResponse.password} onChange={handleFormChange} />
                    </div>

                    <button type='submit'>Login</button>
                    <button type='button'>Forgot password</button>

                    {isPending &&
                        <p>Loading...</p>
                    }

                    {errors &&
                        <div>
                            <p>Error:</p>
                            <p>{errors?.email && "Email: " + errors?.email}</p>
                            <p>{errors?.password && "Password: " + errors?.password}</p>
                        </div>
                    }
                </div>
            </form>

            <div>
                <p>Don't have an account? <a href="/register">Register</a></p>
            </div>
        </div>
    )
}

export default Login