import React, { useState } from 'react'
import type { LoginResponseDto } from '../dto/response/LoginResponseDto';
import { loginResponseNew } from '../new/LoginResponse';
import type { RegisterResponseDto } from '../dto/response/RegisterResponseDto';
import { registerResponseNew } from '../new/RegisterResponse';
import { createUser } from '../service/UserService';
import type { UserDto } from '../types/UserDto';
import { useMutation } from '@tanstack/react-query';
import { Navigate, useNavigate } from 'react-router-dom';
import { isUserRegisterError, type UserRegisterError } from '../error/error.types';

function Register() {
    const [registerResponse, setRegisterResponse] = useState<RegisterResponseDto>(registerResponseNew);
    const [errors, setErrors] = useState<UserRegisterError | null>(null);

    const [pass, setPass] = useState<boolean>(false);

    const navigate = useNavigate();

    const handleFormChange = (e:
        React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement | null>
    ) => {
        const { name, value } = e.target;

        setRegisterResponse({
            ...registerResponse,
            [name]: value,
        });
    };

    const { mutateAsync, isPending } = useMutation({
        mutationFn: createUser,
    });

    async function handleRegister(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();
        setErrors(null);

        const payload: RegisterResponseDto = { ...registerResponse };

        try {
            const user = await mutateAsync(payload);
            console.log("User created: " + user.data.name + " " + user.data.email);
            navigate("/login", { replace: true });
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
            <form onSubmit={handleRegister}>
                <h4>Register</h4>

                <div>
                    <div>
                        <label htmlFor="email">Email</label>
                        <input type="text" name='email' value={registerResponse.email} onChange={handleFormChange} />
                    </div>

                    <div>
                        <label htmlFor="name">Name</label>
                        <input type="text" name='name' value={registerResponse.name} onChange={handleFormChange} />
                    </div>

                    <div>
                        <label htmlFor="password">Password</label>
                        <input onFocus={() => setPass(true)} onBlur={() => setPass(false)} type="text" name='password' value={registerResponse.password} onChange={handleFormChange} />
                    </div>

                    {pass && <p>Password must be at least 8 characters long</p>}

                    <button type='submit'>Register</button>

                    {isPending &&
                        <p>Registering user...</p>
                    }

                    {errors &&
                        <div>
                            <p>Error:</p>
                            <p>{errors?.email && "Email: " + errors?.email}</p>
                            <p>{errors?.name && "Name:" + errors?.name}</p>
                            <p>{errors?.password && "Password: " + errors?.password}</p>
                        </div>
                    }
                </div>
            </form>
        </div>
    )
}

export default Register