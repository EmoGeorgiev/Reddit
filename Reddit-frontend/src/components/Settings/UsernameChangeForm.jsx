import { useState } from 'react'
import { useFormErrors } from '../../hooks/useFormErrors'
import FormHeader from '../Common/FormHeader'
import FormErrorMessage from '../Common/FormErrorMessage'

const UsernameChangeForm = ({ usernameChange, handleClose }) => {
    const [username, setUsername] = useState('')
    const { errors, setBackendErrors } = useFormErrors()

    const handleUsernameChange = async (e) => {
        e.preventDefault()

        try {
            await usernameChange(username)

            setUsername('')
        } catch (error) {
            setBackendErrors(error)
        }
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

                <FormErrorMessage>
                    {errors.username}
                </FormErrorMessage>

                <div className='active-form-btn-container'>
                    <button className='active-form-cancel-btn focus-item' type='button' onClick={handleClose}>
                        Cancel
                    </button>

                    <button className='active-form-confirm-btn focus-item' type='submit'>
                        Save
                    </button>
                </div>
            </form>
        </div>
    )
}

export default UsernameChangeForm