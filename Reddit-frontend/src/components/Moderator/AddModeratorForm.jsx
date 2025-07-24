import { useState } from 'react'
import { useFormErrors } from '../../hooks/useFormErrors'
import FormHeader from '../Common/FormHeader'
import FormErrorMessage from '../Common/FormErrorMessage'

const AddModeratorForm = ({ addModerator, handleClose }) => {
    const [username, setUsername] = useState('')
    const { errors, setBackendErrors } = useFormErrors()

    const handleAddingModerator = async (e) => {
        e.preventDefault()
        
        try {
            await addModerator(username)

            setUsername('')
        } catch (error) {
            setBackendErrors(error)
            console.log(error)
        }
    }

    return (
        <div className='active-form h-1/3'>
            <FormHeader name='Add moderator' handleClose={handleClose} />

            <form onSubmit={handleAddingModerator}>
                <input
                    className='active-form-input focus-item'
                    value={username}
                    name='addModerator'
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

                    <button className='my-5 active-form-confirm-btn focus-item' type='submit'>
                        Add
                    </button>
                </div>
            </form>
        </div>
    )
}

export default AddModeratorForm