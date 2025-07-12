import { useState } from 'react'
import { useAuth } from '../Authentication/AuthContext'
import Vote from '../Common/Vote'
import Save from '../Common/Save'
import DeleteContent from '../Content/DeleteContent'
import CreateCommentForm from './CreateCommentForm'
import ContentDate from '../Content/ContentDate'
import ContentText from '../Content/ContentText'
import ContentButtonPanel from '../Content/ContentButtonPanel'
import ContentHeader from '../Content/ContentHeader'
import ContentUserHeader from '../Content/ContentUserHeader'
import commentService from '../../services/comments'

const Comment = ({ comment, deleteComment }) => {
    const [replies, setReplies] = useState(comment.replies)
    const [isOpen, setIsOpen] = useState(false)
    const { user } = useAuth()

    const createComment = async (comment) => {
        try {
            const newComment = await commentService.addComment(comment)
            setReplies([...replies, newComment])
        } catch (error) {
            console.log(error)
        }
    }

    const handleDelete = () => {
        deleteComment(comment.id, user.id)
    }

    return (
        <div className='m-3 p-4 flex flex-col space-y-2.5 border border-gray-300 rounded-4xl'>
            <ContentHeader>
                <ContentUserHeader user={comment.user} />
                <ContentDate created={comment.created} />
            </ContentHeader>

            <ContentText text={comment.text} />
            
            <ContentButtonPanel>
                <Vote contentId={comment.id} contentScore={comment.score} />
                <Save contentId={comment.id} />
                <button onClick={() => setIsOpen(!isOpen)}>
                    {isOpen ? 'Cancel reply' : 'Reply'}
                </button>
                {!comment.isDeleted && <DeleteContent contentId={comment.id} subreddit={comment.subreddit} handleDelete={handleDelete} />}
            </ContentButtonPanel>
            
            {isOpen && <CreateCommentForm createComment={createComment} postId={comment.postId} parentId={comment.id} />}

            {replies && replies.length > 0 && (
                replies.map(reply => <Comment key={reply.id} comment={reply} deleteComment={deleteComment} />))}
        </div>
    )
}

export default Comment