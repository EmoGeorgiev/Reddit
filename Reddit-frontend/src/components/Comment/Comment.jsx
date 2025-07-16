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
import UpdateTextForm from '../Common/UpdateTextForm'
import ShowReplies from './ShowReplies'

const Comment = ({ comment, deleteComment }) => {
    const [parentComment, setParentComment] = useState(comment)
    const [replies, setReplies] = useState(comment.replies)
    const [isOpen, setIsOpen] = useState(false)
    const [isEdited, setIsEdited] = useState(false)
    const [showReplies, setShowReplies] = useState(false)
    const { user } = useAuth()

    const createComment = async (text) => {
        try {
            const newComment = {
                user,
                text,
                'postId': parentComment.postId,
                'parentId': parentComment.id
            }

            const createdComment = await commentService.addComment(newComment)
            setReplies([...replies, createdComment])
        } catch (error) {
            console.log(error)
        }
    }

    const updateComment = async (text) => {
        try {
            const newComment = {
                ...parentComment,
                text
            }
            const updatedComment = await commentService.updateComment(newComment.id, newComment)
            setParentComment(updatedComment)
        } catch (error) {
            console.log(error)
        }
    }

    const handleDelete = () => {
        if (window.confirm('Are you sure you want to delete this comment?')) {
            deleteComment(parentComment.id, user.id)
        }
    }

    return (
        <div className='my-4 p-4 flex flex-col space-y-2.5 border border-gray-300 rounded-4xl'>
            <ContentHeader user={parentComment.user} 
                            subreddit={parentComment.subreddit} 
                            created={parentComment.created} />

            {isEdited && <UpdateTextForm handleClose={() => setIsEdited(false)} 
                                            originalText={parentComment.text}
                                            updateContent={updateComment} />}

            <ContentText text={parentComment.text} />
            
            {!parentComment.isDeleted && 
                <ContentButtonPanel>
                    <Vote contentId={parentComment.id} contentScore={parentComment.score} />
                    <Save contentId={parentComment.id} />
                    <ReplyButton handleOpen={() => setIsOpen(true)} />
                    <ContentEdit creatorId={parentComment.user.id} handleEdit={() => setIsEdited(true)} />
                    <ContentDelete creatorId={parentComment.user.id} 
                                    subreddit={parentComment.subreddit} 
                                    handleDelete={handleDelete} />
                </ContentButtonPanel>}
            
            {isOpen && <CreateCommentForm isOpen={isOpen} 
                                            setIsOpen={setIsOpen}
                                            createComment={createComment}
                                            postId={parentComment.postId}
                                            parentId={parentComment.id} />}

            <ShowReplies showReplies={showReplies} 
                            handleShow={() => setShowReplies(true)} 
                            length={replies.length} />
            
            {showReplies && replies?.length > 0 && (
                replies.map(reply => <Comment key={reply.id} 
                                                comment={reply} 
                                                deleteComment={deleteComment} />))}
        </div>
    )
}

export default Comment