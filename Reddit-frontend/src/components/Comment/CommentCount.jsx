import commentsIcon from '../../assets/comments-icon.svg'

const CommentCount = ({ count }) => {
    return (
        <button className='p-1 flex justify-center space-x-1 bg-gray-200 hover:bg-gray-300 cursor-pointer rounded-full'
                onClick={() => console.log('')}>
            <img className='w-6 h-6' src={commentsIcon} alt='comments' />
            <span className='font-semibold'>{count}</span>
        </button>
    )
}

export default CommentCount