import { useState } from 'react'
import SettingsCategory from './SettingsCategory'
import SettingsField from './SettingsField'
import UsernameChangeForm from './UsernameChangeForm'
import PasswordChangeForm from './PasswordChangeForm'
import DeleteAccountForm from './DeleteAccountForm'

const ActiveFormOptions = Object.freeze({
    CHANGE_USERNAME: 'Username Change',
    CHANGE_PASSWORD: 'Password Change',
    DELETE_ACCOUNT: 'Delete Account'
})

const Settings = () => {
    const [activeForm, setActiveForm] = useState(null)

    const usernameChange = (username) => {
        closeActiveForm()
    }
    
    const passwordChange = (password) => {
        closeActiveForm()
    }
    
    const deleteAccount = () => {
        closeActiveForm()
    }

    const openActiveForm = (newActiveForm) => {
        setActiveForm(newActiveForm)
    }

    const closeActiveForm = () => {
        setActiveForm(null)
    }

    return (
        <div className='w-1/2 mt-10 mx-auto'>
            <h1 className='page-header'>Settings</h1>
            
            <div>
                {activeForm !== null && <button className='background-btn background-blur' onClick={closeActiveForm}></button>}

                <div className={`${activeForm === ActiveFormOptions.CHANGE_USERNAME ? 'opacity-100 visible' : 'opacity-0 invisible'} duration-300`}>
                    <UsernameChangeForm usernameChange={usernameChange} handleClose={closeActiveForm} />
                </div>
                <div className={`${activeForm === ActiveFormOptions.CHANGE_PASSWORD ? 'opacity-100 visible' : 'opacity-0 invisible'} duration-300`}>
                    <PasswordChangeForm passwordChange={passwordChange} handleClose={closeActiveForm} />
                </div>
                <div className={`${activeForm === ActiveFormOptions.DELETE_ACCOUNT ? 'opacity-100 visible' : 'opacity-0 invisible'} duration-300`}>
                    <DeleteAccountForm deleteAccount={deleteAccount} handleClose={closeActiveForm} />
                </div>
            </div>

            <div className='mt-10 mx-5'>
                <SettingsCategory name='General'>
                    <SettingsField name='Username' handleClick={() => openActiveForm(ActiveFormOptions.CHANGE_USERNAME)} />
                    <SettingsField name='Password' handleClick={() => openActiveForm(ActiveFormOptions.CHANGE_PASSWORD)} />
                </SettingsCategory>
                        
                <SettingsCategory name='Advanced'>
                    <SettingsField name='Delete account' handleClick={() => openActiveForm(ActiveFormOptions.DELETE_ACCOUNT)} />
                </SettingsCategory>
            </div>
        </div>
    )
}

export default Settings