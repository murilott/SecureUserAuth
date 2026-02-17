import React, { useState } from 'react'
import type { CreatePostRequestDto } from '../dto/request/CreatePostRequestDto';
import { useAuth } from '../auth/useAuth';
import { createPostRequestNew } from '../new/CreatePostRequestDto';
import { createPost, listPosts } from '../service/PostService';
import { QueryClient, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { isUserRegisterError } from '../error/error.types';
import { login } from '../service/AuthService';
import type { PostDto } from '../types/PostDto';
import type { ApiAxiosError } from '../utils/errors';
import PostCard from './PostCard';
import "../style/postcard.css"
import "../style/home.css"
import PostList from './PostList';

function Home() {
    const [postDrafting, setPostDrafting] = useState<boolean>(false);
    const [postDraft, setPostDraft] = useState<CreatePostRequestDto>(createPostRequestNew);

    const [errors, setErrors] = useState({title: "", content: ""});

    const { isAuthenticated, user } = useAuth();

    const queryClient = useQueryClient();

    const handleFormChange = (e:
        React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement | null>
    ) => {
        const { name, value } = e.target;

        setPostDraft({
            ...postDraft,
            [name]: value,
        });
    };

    function cancelPost() {
        setPostDrafting(false);
        setPostDraft(createPostRequestNew);
        setErrors({title: "", content: ""});
    }

    const { mutateAsync, isPending } = useMutation({
        mutationFn: createPost,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['posts'] });
        },
    });

    async function newPost() {
        setErrors({title: "", content: ""});

        if (!isAuthenticated || !user || !user.id) {
            console.log("Forbidden access");
            
            return;
        }

        if(!postDraft.content.trim() || !postDraft.title.trim()) {
            setErrors({title: !postDraft.title ? "Title cannot be blank" : "", 
                content: !postDraft.content ? "Content cannot be blank" : ""});

            return;
        }

        const payload: CreatePostRequestDto = {
            ...postDraft,
            authorId: user.id,
        }

        console.log(payload);

        try {
            const response = await mutateAsync(payload);
            console.log("Response created: " + response.toString());
            cancelPost();
        } catch (err: unknown) {
            console.error("Unexpected error:", err);
        }
    }

    const { isLoading, error, data: posts } = useQuery<PostDto[], ApiAxiosError>({
        queryKey: ['posts'],
        queryFn: listPosts,
        enabled: isAuthenticated,
    });

    return (
        <div className="content">
            <h3>Home</h3>

            {!isAuthenticated ?
                <div>
                    <p>Log in or register to see your profile and posts.</p>
                </div>
                :
                <>
                    <div>
                        <button onClick={() => setPostDrafting(true)}>New post</button>
                    </div>

                    <main className='page'>
                        {postDrafting ?
                            <div className='new-post'>
                                <h3>New Post:</h3>

                                <div className='post-comps'>
                                    <label htmlFor="title">Title:</label>
                                    <input type="text" name='title' value={postDraft.title} onChange={handleFormChange} />
                                </div>

                                <div className='post-comps'>
                                    <label htmlFor="content">Content:</label>
                                    <textarea name='content' value={postDraft.content} onChange={handleFormChange} rows={10} cols={60} />
                                </div>

                                <div className='post-comps'>
                                    <button onClick={newPost}>Create Post</button>
                                    <button onClick={cancelPost}>Cancel</button>
                                </div>

                                {(errors.content || errors.title) &&
                                    <div>
                                        <p>Error:</p>
                                        {(errors.title) && <p>{errors.title}</p>}
                                        {(errors.content) && <p>{errors.content}</p>}
                                    </div>
                                }
                            </div>
                            :
                            <div>
                                <h3>All posts</h3>

                                <PostList posts={posts} isLoading={isLoading} error={error} />
                                
                            </div>
                        }
                    </main>
                </>
            }

        </div>
    )
}

export default Home