import { useState } from 'react'
import closeIcon from '../../assets/close-icon.svg'

const UsernameChangeForm = ({ usernameChange, handleClose }) => {
    const [username, setUsername] = useState('')

    const handleUsernameChange = (e) => {
        e.preventDefault()

        usernameChange(username)

        setUsername('')
    }

    return (
        <div className='active-form h-1/3'>
            <div className='active-form-header'>
                <h1 className='active-form-heading'>Username</h1>

                <button onClick={handleClose}>
                    <img className='close-btn' src={closeIcon} alt='close' />
                </button>
            </div>

            <form onSubmit={handleUsernameChange}>
                <input 
                    className='active-form-input focus-item'
                    type='text'
                    value={username}
                    name='username'
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