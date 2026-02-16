import React from 'react'
import { listUsers } from '../service/UserService';
import { useQuery } from '@tanstack/react-query';
import { getAxiosErrorMessage, type ApiAxiosError } from '../utils/errors';
import type { UserDto } from '../types/UserDto';
import { useAuth } from '../auth/useAuth';
import UserCard from './UserCard';
import ErrorPage from './ErrorPage';

function User() {
    const { isAuthenticated, isAdmin } = useAuth();

    const { isPending, isLoading, error, data: users } = useQuery<UserDto[], ApiAxiosError>({
        queryKey: ['users'],
        queryFn: listUsers,
        refetchOnWindowFocus: false,
        enabled: isAuthenticated,
    });

    if (!isAuthenticated) {return <ErrorPage type={401} message='Please log in to use this feature.' />}

    if (!isAdmin) {return <ErrorPage type={403} message="You don't have authorization necessary to view this page." />}

    return (
        <div className='content'>
            <h3>User</h3>

            <div>
                <h4>Registered users:</h4>

                {isLoading &&
                    <p>Loading...</p>
                }

                {error &&
                    <p>Error: {getAxiosErrorMessage(error)}</p>
                }

                {(users ?? []).length == 0 && !isPending && !error &&
                    <p>No users found.</p>
                }

                {users?.map((user) => (
                    <UserCard user={user} />
                ))}
            </div>
        </div>
    )
}

export default User