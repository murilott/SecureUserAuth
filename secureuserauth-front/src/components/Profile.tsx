import React from 'react'
import { useAuth } from '../auth/useAuth';
import UserCard from './UserCard';
import ErrorPage from './ErrorPage';

function Profile() {
    const { isAuthenticated, user } = useAuth();

    if (!isAuthenticated) {return <ErrorPage type={401} message='Please log in to use this feature.' />}
        
    return (
        <div className='content'>
            <h3>Profile</h3>

            <div>
                <UserCard user={user} />
            </div>
        </div>
    )
}

export default Profile