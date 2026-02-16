import type { PostDto } from '../types/PostDto';
// import "../style/postcard.css"

interface PostCardProps {
    post: PostDto | null;
}

function PostCard({ post }: PostCardProps) {
    if (!post) { return }

    return (
        <div key={post.id} className='postCard'>
            <div className='post-header'>
                <div className='post-header-title'>
                    <h4>{post.title} -</h4>
                    <span><strong>{post.authorName}</strong></span>
                </div>
                <span><strong>{post.createdAt.toLocaleString()}</strong></span>
            </div>

            <div className='post-body'>
                <p>{post.content}</p>
            </div>
        </div>
    )
}

export default PostCard