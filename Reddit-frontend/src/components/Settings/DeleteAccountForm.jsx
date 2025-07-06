import { useState } from 'react'
import FormHeader from '../Common/FormHeader'

const DeleteAccountForm = ({ deleteAccount, handleClose }) => {
   const [password, setPassword] = useState('')
   
    const handleDeleteAccount = (e) => {
        e.preventDefault()

        if (window.confirm('Are you sure you want to delete your account?')) {
            deleteAccount(password)
        }

        setPassword('')
    }

    return (
        <div className='active-form'>
            <FormHeader name='Delete account' handleClose={handleClose} />
            
            <p className='mx-5 my-3 font-thin'>
                Once you delete your account, your profile and username are permanently removed from Reddit and your posts and comments are disassociated (not deleted) from your account unless you delete them beforehand.
            </p>

            <form onSubmit={handleDeleteAccount}>
                <input 
                    className='active-form-input focus-item'
                    type='password'
                    value={password}
                    name='password'
                    placeholder='Password'
                    onChange={(e) => setPassword(e.target.value)}
                />

                <div className='active-form-btn-container'>
                    <button className='my-2 active-form-cancel-btn focus-item' type='button' onClick={handleClose}>
                        Cancel
                    </button>

                    <button className='my-2 bg-red-700 hover:bg-red-800 active-form-confirm-btn focus-item' type='submit'>
                        Delete
                    </button>
                </div>
            </form>
        </div>
    )
}

export default DeleteAccountForm