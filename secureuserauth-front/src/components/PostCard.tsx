import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useAuth } from '../auth/useAuth';
import { deletePost } from '../service/PostService';
import type { PostDto } from '../types/PostDto';
// import "../style/postcard.css"

interface PostCardProps {
    post: PostDto | null;
}

function PostCard({ post }: PostCardProps) {
    const { isAdmin, user } = useAuth();

    const queryClient = useQueryClient();

    const { mutate: deletePostMutation, isPending } = useMutation({
        mutationFn: deletePost,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['posts'] });
        },
        onError: (error) => {
            console.error("Error on delete:", error);
        }
    });

    const formatDate = (dateValue: Date | string) => {
        if (!dateValue) return "";
        const date = new Date(dateValue);
        return date.toLocaleString('pt-BR', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
        });
    };


    if (!post) { return }

    return (
        <div key={post.id} className='postCard'>
            <div className='post-header'>
                <div className='post-header-title'>
                    <h4>{post.title} -</h4>
                    <span><strong>{post.authorName}</strong></span>
                </div>
                <span>{formatDate(post.createdAt)}</span>
            </div>

            <div className='post-body'>
                <p>{post.content}</p>

                {(isAdmin || user?.id == post.authorId) &&
                    <button onClick={() => deletePostMutation(post.id)} className='delete-button'>Delete</button>
                }

                {isPending && <span>Deleting...</span>}
            </div>

        </div>
    )
}

export default PostCard