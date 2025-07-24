import { useEffect, useState } from 'react'
import { usePagination } from '../../hooks/usePagination'
import EmptyContent from '../Common/EmptyContent'
import Pagination from '../Common/Pagination'
import commentService from '../../services/comments'
import CommentList from '../Comment/CommentList'

const UserComments = ({ profile }) => {
    const [comments, setComments] = useState([])
    const { 
        page,
        goToNextPage,
        goToPreviousPage,
        isEmpty,
        setIsEmpty,
        isFirst,
        setIsFirst,
        isLast,
        setIsLast
    } = usePagination()

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
            <Pagination goToNextPage={goToNextPage} goToPreviousPage={goToPreviousPage} isFirst={isFirst} isLast={isLast} />
        </div>
    )
}

export default UserComments