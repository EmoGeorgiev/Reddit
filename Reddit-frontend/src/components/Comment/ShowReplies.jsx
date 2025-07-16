const ShowReplies = ({ showReplies, handleShow, length }) => {
    return (
        <>
            {!showReplies && length > 0 && 
                <button className='w-fit mx-2 text-gray-700 font-semibold hover:underline hover:text-black' 
                        onClick={handleShow}>
                    Show {length === 1 ? 'reply' : 'replies'} ({length})
                </button>}
        </>
    )
}

export default ShowReplies