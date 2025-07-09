import PostList from '../Post/PostList'

const Feed = ({ query, getPosts }) => {
    return (
        <div className='w-4/5 h-full mx-auto'>
            <PostList query={query} getPosts={getPosts} />
        </div>
    )
}

export default Feed