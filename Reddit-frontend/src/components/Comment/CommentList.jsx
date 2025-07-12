import Comment from './Comment'

const CommentList = ({ comments, deleteComment }) => {
    return (
        <ul className='mb-8'>
            {comments.map(comment => 
                <li key={comment.id}>
                    <Comment comment={comment} deleteComment={deleteComment} />
                </li>
            )}
        </ul>
    )
}

export default CommentList