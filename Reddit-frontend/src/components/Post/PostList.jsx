import { useEffect, useState } from 'react'
import Post from './Post'
import Pagination from '../Pagination/Pagination'

const PostList = ({ query, getPosts }) => {
    const [posts, setPosts] = useState([])
    const [page, setPage] = useState(0)
    const [isFirst, setIsFirst] = useState(true)
    const [isLast, setIsLast] = useState(true)

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

    const handlePageChange = (change) => {
        setPage(page + change)
    }

    return (
        <div className='my-4'>
            {posts.map(post => <Post key={post.id} post={post} />)}

            <Pagination handlePageChange={handlePageChange} isFirst={isFirst} isLast={isLast} />
        </div>
    )
}

export default PostList