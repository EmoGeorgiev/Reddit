import { useState } from 'react'
import { useFormErrors } from '../../hooks/useFormErrors'
import FormHeader from '../Common/FormHeader'
import FormErrorMessage from '../Common/FormErrorMessage'

const PasswordChangeForm = ({ passwordChange, handleClose }) => {
    const [oldPassword, setOldPassword] = useState('')
    const [newPassword, setNewPassword] = useState('')
    const { errors, setBackendErrors } = useFormErrors()

    const handlePasswordChange = async (e) => {
        e.preventDefault()

        try {
            await passwordChange(oldPassword, newPassword)

            setOldPassword('')
            setNewPassword('')
        } catch (error) {
            console.log(error)
            setBackendErrors(error)
        }
    }

    return (
        <div className='active-form'>
            <FormHeader name='Password' handleClose={handleClose} />

            <form  onSubmit={handlePasswordChange}>
                <input 
                    className='active-form-input focus-item'
                    type='password'
                    value={oldPassword}
                    name='oldPassword'
                    placeholder='Old password'
                    onChange={(e) => setOldPassword(e.target.value)}
                />

                <FormErrorMessage>
                    {errors.oldPassword}
                </FormErrorMessage>

                <input 
                    className='active-form-input focus-item'
                    type='password'
                    value={newPassword}
                    name='newPassword'
                    placeholder='New password'
                    onChange={(e) => setNewPassword(e.target.value)}
                />

                <FormErrorMessage>
                    {errors.newPassword || errors.message}
                </FormErrorMessage>

                <div className='active-form-btn-container'>
                    <button className='my-10 active-form-cancel-btn focus-item' type='button' onClick={handleClose}>
                        Cancel
                    </button>
                    
                    <button className='my-10 active-form-confirm-btn focus-item' type='submit'>
                        Save
                    </button>
                </div>
            </form>
        </div>
    )
}

export default PasswordChangeForm