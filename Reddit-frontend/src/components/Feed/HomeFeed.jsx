import { useAuth } from '../../hooks/useAuth'
import Feed from './Feed'
import postService from '../../services/posts'

const HomeFeed = () => {
    const { user } = useAuth()

    const getPostsByUserSubscriptions = async (pageable) => {
        const postPage = await postService.getPostsByUserSubscriptions(user.id, pageable)
        return postPage
    }

    return (
        <div>
            <Feed query={null} getPosts={getPostsByUserSubscriptions} />
        </div>
    )
}

export default HomeFeed