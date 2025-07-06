import { useState } from 'react'
import closeIcon from '../../assets/close-icon.svg'

const CreateSubredditForm = ({ handleClose }) => {
    const [title, setTitle] = useState('')

    const handleOnSubmit = () => {
        
    }

    return (
        <div className='active-form h-1/3'>
            <div className='active-form-header'>
                <h1 className='active-form-heading'>
                    Tell us about your subreddit
                </h1>

                <button onClick={handleClose}>
                    <img className='close-btn' src={closeIcon} alt='close' />
                </button>
            </div>

            <form onSubmit={handleOnSubmit}>
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