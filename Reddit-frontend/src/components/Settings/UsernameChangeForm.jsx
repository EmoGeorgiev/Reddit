import { useState } from 'react'
import FormHeader from '../Common/FormHeader'

const UsernameChangeForm = ({ usernameChange, handleClose }) => {
    const [username, setUsername] = useState('')

    const handleUsernameChange = (e) => {
        e.preventDefault()

        usernameChange(username)

        setUsername('')
    }

    return (
        <div className='active-form h-1/3'>
            <FormHeader name='Username' handleClose={handleClose} />

            <form onSubmit={handleUsernameChange}>
                <input 
                    className='active-form-input focus-item'
                    type='text'
                    value={username}
                    name='newUsername'
                    placeholder='New username'
                    onChange={(e) => setUsername(e.target.value)}
                />

                <div className='active-form-btn-container'>
                    <button className='my-5 active-form-cancel-btn focus-item' type='button' onClick={handleClose}>
                        Cancel
                    </button>

                    <button className='my-5 active-form-confirm-btn focus-item' type='submit'>
                        Save
                    </button>
                </div>
            </form>
        </div>
    )
}

export default UsernameChangeForm