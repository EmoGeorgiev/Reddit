import { useState } from 'react'
import { useAuth } from '../Authentication/AuthContext'

const CreateCommentForm = ({ isOpen, setIsOpen, createComment, postId, parentId }) => {
    const [text, setText] = useState('')
    const { user } = useAuth()

    const enableFocus = () => {
        setIsOpen(true)
    }

    const disableFocus = () => {
        setIsOpen(false)
        setText('')
    }

    const handleCreateComment = async (e) => {
        e.preventDefault()

        const comment = {
            user,
            text,
            postId,
            parentId
        }

        try {
            await createComment(comment)

            disableFocus()
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <div className={`my-8 ${isOpen ? 'h-48' : 'h-12'}`}>
            <form className='h-full flex flex-col' onSubmit={handleCreateComment}>
                <textarea className='h-full p-2 flex-1 border border-gray-300 focus:outline-none focus:border-gray-500 rounded-2xl resize-none'
                            value={text}
                            onChange={(e) => setText(e.target.value)}
                            placeholder='Join the conversation'
                            name='textArea'
                            onFocus={enableFocus}>

                </textarea>

                {isOpen && 
                    <div className='mt-2 flex justify-end'>
                        <button className='active-form-cancel-btn' type='button'
                                onClick={disableFocus}>
                            Cancel
                        </button>
                            
                        <button className='active-form-confirm-btn' type='submit'> 
                            Comment
                        </button>
                    </div>}
            </form>
        </div>
    )
}

export default CreateCommentForm