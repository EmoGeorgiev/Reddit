import PostPage from '../Post/PostPage'

const Feed = ({ query, getPosts }) => {
    return (
        <div className='w-4/5 h-full mx-auto'>
            <PostPage query={query} getPosts={getPosts} />
        </div>
    )
}

export default Feed