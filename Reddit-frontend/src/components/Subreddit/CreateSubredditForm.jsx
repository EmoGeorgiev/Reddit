import { useState } from 'react'
import FormHeader from '../Common/FormHeader'

const CreateSubredditForm = ({ addSubreddit, handleClose }) => {
    const [title, setTitle] = useState('')

    const handleSubmit = async (e) => {
        e.preventDefault()
        
        const subreddit = { title }

        addSubreddit(subreddit)
    }

    return (
        <div className='active-form h-1/3'>
            <FormHeader name='Tell us about your subreddit' handleClose={handleClose} />

            <form onSubmit={handleSubmit}>
                <input
                    className='active-form-input focus-item'
                    type='text' 
                    value={title}
                    name='title'
                    placeholder='Subreddit title'
                    onChange={(e) => setTitle(e.target.value)}
                />

                <div className='active-form-btn-container'>
                    <button className='my-5 active-form-cancel-btn focus-item' type='button' onClick={handleClose}>
                        Cancel
                    </button>

                    <button className='my-5 active-form-confirm-btn focus-item' type='submit'>
                        Create Subreddit
                    </button>
                </div>
            </form>
        </div>
    )
}

export default CreateSubredditForm