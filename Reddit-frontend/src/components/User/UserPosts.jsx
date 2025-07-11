import { useState } from 'react'
import EmptyContent from '../Common/EmptyContent'
import postService from '../../services/posts'
import PostPage from '../Post/PostPage'

const UserPosts = ({ profile }) => {
    const [isEmpty, setIsEmpty] = useState(false)

    const getPostPage = async (id, pageable) => {
        const postPage = await postService.getPostsByUserId(id, pageable)

        setIsEmpty(postPage.empty)

        return postPage
    }

    if (isEmpty) {
        return (
            <>
                <EmptyContent text={`u/${profile.username} hasn't posted yet`} />
            </>
        )
    }

    return (
        <>
            <PostPage query={profile.id} getPosts={getPostPage} />
        </>
    )
}

export default UserPosts