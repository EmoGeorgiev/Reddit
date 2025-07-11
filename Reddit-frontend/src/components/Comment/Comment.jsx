
const Comment = ({ comment }) => {
    return (
        <div>
            <div>{comment.user.username}</div>
            <div>{comment.text}</div>
            <div>{comment.created}</div>
            <div>{comment.score}</div>
        </div>
    )
}

export default Comment