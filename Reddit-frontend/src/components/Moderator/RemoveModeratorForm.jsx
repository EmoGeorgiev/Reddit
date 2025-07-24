import { useState } from 'react'
import { useFormErrors } from '../../hooks/useFormErrors'
import FormHeader from '../Common/FormHeader'
import FormErrorMessage from '../Common/FormErrorMessage'

const RemoveModeratorForm = ({ removeModerator, handleClose }) => {
    const [username, setUsername] = useState('')
    const { errors, setBackendErrors } = useFormErrors()

    const handleRemovingModerator = async (e) => {
        e.preventDefault()

        try {
            await removeModerator(username)

            setUsername('')
        } catch (error) {
            setBackendErrors(error)
        }
    }

    return (
        <div className='active-form h-1/3'>
            <FormHeader name='Remove moderator' handleClose={handleClose} />

            <form onSubmit={handleRemovingModerator}>
                <input
                    className='active-form-input focus-item'
                    value={username}
                    name='removeModerator'
                    placeholder='Enter Username'
                    onChange={(e) => setUsername(e.target.value)}
                />

                <FormErrorMessage>
                    {errors.message}
                </FormErrorMessage>

                <div className='active-form-btn-container'>
                    <button className='my-5 active-form-cancel-btn focus-item' type='button' onClick={handleClose}>
                        Cancel
                    </button>

                    <button className='my-5 bg-red-700 hover:bg-red-800 active-form-confirm-btn focus-item' type='submit'>
                        Remove
                    </button>
                </div>
            </form>
        </div>
    )
}

export default RemoveModeratorForm