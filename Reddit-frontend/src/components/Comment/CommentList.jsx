import Comment from './Comment'

const CommentList = ({ comments }) => {
    return (
        <>
            <ul className='mb-8'>
                {comments.map(comment => 
                    <li key={comment.id}>
                        <Comment comment={comment} />
                    </li>
                )}
            </ul>
        </>
    )
}

export default CommentList