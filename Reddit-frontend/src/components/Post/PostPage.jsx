import { useLocation } from 'react-router-dom'
import { useEffect, useState } from 'react'
import { useAuth } from '../../hooks/useAuth'
import { SortOptions } from '../../util/SortOptions'
import PostList from './PostList'
import Pagination from '../Common/Pagination'
import SortSelect from '../Common/SortSelect'
import postService from '../../services/posts'

const PostPage = ({ query, getPosts }) => {
    const [posts, setPosts] = useState([])
    const [sort, setSort] = useState(SortOptions.New)
    const [page, setPage] = useState(0)
    const [isFirst, setIsFirst] = useState(true)
    const [isLast, setIsLast] = useState(true)
    const { user } = useAuth()
    const location = useLocation()

    useEffect(() => {
        const getPostPage = async () => {
            try {
                const postPage = await getPosts(query, { page, 'sort': `${sort},desc` })

                setPosts(postPage.content)
                setIsFirst(postPage.first)
                setIsLast(postPage.last)
            } catch (error) {
                console.log(error)
            }
        }

        getPostPage()
    }, [query, page, sort])

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

    const showSort = () => {
        const subredditPath = '/r/'
        const currentPath = location.pathname.toLowerCase()

        return currentPath.includes(subredditPath)
    }

    return (
        <div className='my-4'>
            {showSort() && 
                <div className='m-4 font-semibold text-gray-700 flex space-x-2'>
                    <SortSelect selected={sort} handleChange={setSort} />
                </div>}
            <PostList posts={posts} deletePost={deletePost} />
            <Pagination handlePageChange={handlePageChange} isFirst={isFirst} isLast={isLast} />
        </div>
    )
}

export default PostPage