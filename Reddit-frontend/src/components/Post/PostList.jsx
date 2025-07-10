import Post from './Post'

const PostList = ({ posts, deletePost }) => {
    return (
        <ul>
            {posts.map(post => 
                <li key={post.id}>
                    <Post post={post}
                        deletePost={deletePost} />
                </li>)}
        </ul>
    )
}

export default PostList