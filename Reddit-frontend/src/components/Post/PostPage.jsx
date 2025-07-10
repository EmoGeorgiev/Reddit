import { useEffect, useState } from 'react'
import { useAuth } from '../Authentication/AuthContext'
import Pagination from '../Common/Pagination'
import postService from '../../services/posts'
import PostList from './PostList'

const PostPage = ({ query, getPosts }) => {
    const [posts, setPosts] = useState([])
    const [page, setPage] = useState(0)
    const [isFirst, setIsFirst] = useState(true)
    const [isLast, setIsLast] = useState(true)
    const { user } = useAuth()

    useEffect(() => {
        const getPostPage = async () => {
            try {
                const postPage = await getPosts(query, { page })

                setPosts(postPage.content)
                setIsFirst(postPage.first)
                setIsLast(postPage.last)
            } catch (error) {
                console.log(error)
            }
        }

        getPostPage()
    }, [query, page])

    const deletePost = async (postId) => {
        try {
            await postService.deletePost(postId, user.id)

            setPosts(posts.filter(post => post.id !== postId))
        } catch (error) {
            console.log(error)
        }
    }

    const handlePageChange = (change) => {
        setPage(page + change)
    }

    return (
        <div className='my-4'>
            <PostList posts={posts} deletePost={deletePost} />
            <Pagination handlePageChange={handlePageChange} isFirst={isFirst} isLast={isLast} />
        </div>
    )
}

export default PostPage