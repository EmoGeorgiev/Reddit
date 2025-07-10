import { useState } from 'react'
import EmptyContent from '../Common/EmptyContent'
import postService from '../../services/posts'
import PostList from '../Post/PostList'

const UserPosts = ({ profile }) => {
    const [isEmpty, setIsEmpty] = useState(true)

    const getPostPage = async (id, pageable) => {
        const postPage = await postService.getPostsByUserId(id, pageable)

        setIsEmpty(postPage.empty)

        return postPage
    }

    return (
        <div>
            {isEmpty && <EmptyContent text={`u/${profile.username} hasn't posted yet`} />}

            <PostList query={profile.id} getPosts={getPostPage} />
        </div>
    )
}

export default UserPosts