import { useState } from 'react'
import { useAuth } from '../Authentication/AuthContext'
import Vote from '../Common/Vote'
import Save from '../Common/Save'
import DeleteContent from '../Common/DeleteContent'
import userIcon from '../../assets/user-icon.svg'
import CreateCommentForm from './CreateCommentForm'
import commentService from '../../services/comments'

const Comment = ({ comment }) => {
    const [replies, setReplies] = useState(comment.replies)
    const [isOpen, setIsOpen] = useState(false)
    const { user } = useAuth()
    const date = comment.created.split('T')[0]

    const handleDelete = () => {

    }

    const createComment = async (comment) => {
        try {
            const newComment = await commentService.addComment(comment)
            
            console.log(newComment)

            setReplies([...replies, newComment])
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <div className='m-3 p-4 flex flex-col space-y-2.5 border border-gray-300 rounded-4xl'>
            <div className='flex justify-between items-center'>
                <div className='flex space-x-1.5 items-center'>
                    <img className='w-10 h-10' src={userIcon} alt='user icon' />
                    <span className='font-semibold'>u/{comment.user.username}</span>
                </div>
                <div className='font-light'>
                    {date}
                </div>
            </div>

            <div className='font-light break-words'>
                {comment.text}
            </div>
            
            <div className='flex space-x-4 items-center'>
                <Vote contentId={comment.id} contentScore={comment.score} />
                <Save contentId={comment.id} />
                <button onClick={() => setIsOpen(!isOpen)}>
                    {isOpen ? 'Cancel reply' : 'Reply'}
                </button>
                {(user?.id === comment.user.id) && <DeleteContent handleDelete={handleDelete} />}
            </div>
            
            {isOpen && <CreateCommentForm createComment={createComment} postId={comment.postId} parentId={comment.id} />}

            {replies && replies.length > 0 && (
                replies.map(reply => <Comment key={reply.id} comment={reply} />))}
        </div>
    )
}

export default Comment