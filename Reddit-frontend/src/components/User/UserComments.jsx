import { useEffect, useState } from 'react'
import EmptyContent from '../Common/EmptyContent'
import Pagination from '../Common/Pagination'
import commentService from '../../services/comments'
import CommentList from '../Comment/CommentList'

const UserComments = ({ profile }) => {
    const [comments, setComments] = useState([])
    const [isEmpty, setIsEmpty] = useState(true)
    const [page, setPage] = useState(0)
    const [isFirst, setIsFirst] = useState(true)
    const [isLast, setIsLast] = useState(true)

    useEffect(() => {
        const getComments = async () => {
            try {
                const commentPage = await commentService.getCommentsByUserId(profile.id, { page })

                setComments(commentPage.content.map(comment => { return { ...comment, 'replies': [] } }))
                setIsEmpty(commentPage.empty)
                setIsFirst(commentPage.first)
                setIsLast(commentPage.last)
            } catch (error) {
                console.log(error)
            }
        }

        getComments()
    }, [profile.id, page])

    const handlePageChange = (change) => {
        setPage(page + change)
    }

    const deleteComment = async (commentId, userId) => {
        try {
            await commentService.deleteComment(commentId, userId)
            
            setComments(comments.filter(comment => comment.id !== commentId))
        } catch (error) {
            console.log(error)
        }
    }

    if (isEmpty) {
        return (
            <>
                <EmptyContent text={`u/${profile.username} hasn't commented yet`} />
            </>
        )
    }

    return (
        <div>
            <CommentList comments={comments} deleteComment={deleteComment} />
            <Pagination handlePageChange={handlePageChange} isFirst={isFirst} isLast={isLast} />
        </div>
    )
}

export default UserComments