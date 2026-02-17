import React from 'react'
import { useAuth } from '../auth/useAuth';
import UserCard from './UserCard';
import ErrorPage from './ErrorPage';
import { useQuery } from '@tanstack/react-query';
import { listPosts } from '../service/PostService';
import type { PostDto } from '../types/PostDto';
import type { ApiAxiosError } from '../utils/errors';
import PostList from './PostList';
import { listUserPosts } from '../service/UserService';

function Profile() {
    const { isAuthenticated, user } = useAuth();

    const { isLoading, error, data: posts } = useQuery<PostDto[], ApiAxiosError>({
        queryKey: ['posts'],
        queryFn: listUserPosts,
        enabled: isAuthenticated,
    });

    if (!isAuthenticated) {return <ErrorPage type={401} message='Please log in to use this feature.' />}
        
    return (
        <div className='content'>
            <h3>Profile</h3>

            <div>
                <UserCard user={user} />
            </div>

            <h3>Your posts</h3>

            <PostList posts={posts} isLoading={isLoading} error={error} />
        </div>
    )
}

export default Profile