import Comment from './Comment'

const CommentList = ({ comments }) => {
    return (
        <>
            <ul>
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