import { useState } from 'react'
import { useAuth } from '../Authentication/AuthContext'
import Vote from '../Common/Vote'
import Save from '../Common/Save'
import ContentDelete from '../Content/ContentDelete'
import CreateCommentForm from './CreateCommentForm'
import ContentText from '../Content/ContentText'
import ContentButtonPanel from '../Content/ContentButtonPanel'
import ContentHeader from '../Content/ContentHeader'
import commentService from '../../services/comments'
import ContentEdit from '../Content/ContentEdit'
import ReplyButton from './ReplyButton'

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
        if (window.confirm('Are you sure you want to delete this comment?')) {
            deleteComment(comment.id, user.id)
        }
    }

    return (
        <div className='my-4 p-4 flex flex-col space-y-2.5 border border-gray-300 rounded-4xl'>
            <ContentHeader user={comment.user} 
                            subreddit={comment.subreddit} 
                            created={comment.created} />

            <ContentText text={comment.text} />
            
            {!comment.isDeleted && 
                <ContentButtonPanel>
                    <Vote contentId={comment.id} contentScore={comment.score} />
                    <Save contentId={comment.id} />
                    <ReplyButton handleOpen={() => setIsOpen(true)} />
                    <ContentEdit creatorId={comment.user.id} handleEdit={null} />
                    <ContentDelete creatorId={comment.user.id} 
                                    subreddit={comment.subreddit} 
                                    handleDelete={handleDelete} />
                </ContentButtonPanel>}
            
            {isOpen && <CreateCommentForm isOpen={isOpen} 
                                            setIsOpen={setIsOpen}
                                            createComment={createComment}
                                            postId={comment.postId}
                                            parentId={comment.id} />}

            {replies && replies.length > 0 && (
                replies.map(reply => <Comment key={reply.id} 
                                                comment={reply} 
                                                deleteComment={deleteComment} />))}
        </div>
    )
}

export default Comment