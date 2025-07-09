import Feed from './Feed'
import postService from '../../services/posts'

const AllFeed = () => {
    const getPosts = async (pageable) => {
        const postPage = await postService.getPosts(pageable)
        return postPage
    }

    return (
        <div>
            <Feed query={null} getPosts={getPosts} />
        </div>
    )
}

export default AllFeed