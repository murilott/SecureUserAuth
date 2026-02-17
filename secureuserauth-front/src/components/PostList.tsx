import React from 'react'
import type { PostDto } from '../types/PostDto'
import type { ApiAxiosError } from '../utils/errors'
import PostCard from './PostCard'
import "../style/home.css"

interface PostListprops {
    posts: PostDto[] | undefined,
    isLoading: boolean,
    error: ApiAxiosError | null
}

function PostList({posts, isLoading, error}: PostListprops) {
    return (
        <div>
            {(posts ?? []).length == 0 && !isLoading && !error &&
                <p>No posts found.</p>
            }

            <div className='post-list'>
                {posts?.map((post) => (
                    <PostCard post={post} />
                ))}
            </div>
        </div>
    )
}

export default PostList