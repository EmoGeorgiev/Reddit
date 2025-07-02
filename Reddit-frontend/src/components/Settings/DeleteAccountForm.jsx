import { useState } from 'react'

const DeleteAccountForm = ({ deleteAccount, handleClose }) => {
   const [password, setPassword] = useState('')
   
    const handleDeleteAccount = (e) => {
        e.preventDefault()

        if (window.confirm('Are you sure you want to delete your account?')) {
            deleteAccount(password)
        }

        setPassword('')
        handleClose()
    }

    return (
        <div className='active-form'>
            <div className='active-form-header'>
                <h1 className='active-form-heading'>Delete account</h1>

                <button className='form-close-btn' onClick={handleClose}>
                    X
                </button>
            </div>
            
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