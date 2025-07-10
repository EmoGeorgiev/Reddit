import { useEffect, useState } from 'react'
import { contentToPost } from '../../util/contentMapper'
import PostList from '../Post/PostList'
import Pagination from '../Common/Pagination'

const UserVoted = ({ profile, getVoted }) => {
    const [contents, setContents] = useState([])
    const [page, setPage] = useState(0)
    const [isFirst, setIsFirst] = useState(true)
    const [isLast, setIsLast] = useState(true)

    useEffect(() => {
        const getVotedPage = async () => {
            try {
                const votedPage = await getVoted(profile.id, { page })
                
                setContents(votedPage.content
                                    .map(vote => vote.contentDto)
                                    .filter(content => content.contentType === 'POST')
                                    .map(content => contentToPost(content)))
                setIsFirst(votedPage.first)
                setIsLast(votedPage.last)
            } catch (error) {
                console.log(error)
            }
        }

        getVotedPage()
    }, [profile.id, page])

    const handlePageChange = (change) => {
        setPage(page + change)
    }

    return (
        <div>
            <PostList posts={contents} deletePost={null} />
            <Pagination handlePageChange={handlePageChange} isFirst={isFirst} isLast={isLast} />
        </div>
    )
}

export default UserVoted