import { useState } from 'react'
import { ActiveFormOption } from '../../util/ActiveFormOption'
import { useAuth } from '../Authentication/AuthContext'
import SettingsCategory from './SettingsCategory'
import SettingsField from './SettingsField'
import UsernameChangeForm from './UsernameChangeForm'
import PasswordChangeForm from './PasswordChangeForm'
import DeleteAccountForm from './DeleteAccountForm'
import userService from '../../services/users'

const Settings = () => {
    const [activeForm, setActiveForm] = useState(null)
    const { user, updateUser, logout } = useAuth()

    const usernameChange = async (username) => {
        try {
            const newUser = await userService.updateUsername(user.id, { 'id': user.id, username })

            updateUser(newUser)

            closeActiveForm()
        } catch (error) {
            console.log(error)
        }
    }
    
    const passwordChange = async (oldPassword, newPassword) => {
        try {
            await userService.updatePassword(user.id, { oldPassword, newPassword })

            closeActiveForm()
        } catch (error) {
            console.log(error)
        }
    }
    
    const deleteAccount = async (password) => {
        try {
            await userService.deleteUser(user.id, password)
                
            closeActiveForm()

            logout()
        } catch (error) {
            console.log(error)
        }
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

                <div className={`${activeForm === ActiveFormOption.CHANGE_USERNAME ? 'opacity-100 visible' : 'opacity-0 invisible'} duration-300`}>
                    <UsernameChangeForm usernameChange={usernameChange} handleClose={closeActiveForm} />
                </div>
                <div className={`${activeForm === ActiveFormOption.CHANGE_PASSWORD ? 'opacity-100 visible' : 'opacity-0 invisible'} duration-300`}>
                    <PasswordChangeForm passwordChange={passwordChange} handleClose={closeActiveForm} />
                </div>
                <div className={`${activeForm === ActiveFormOption.DELETE_ACCOUNT ? 'opacity-100 visible' : 'opacity-0 invisible'} duration-300`}>
                    <DeleteAccountForm deleteAccount={deleteAccount} handleClose={closeActiveForm} />
                </div>
            </div>

            <div className='mt-10 mx-5'>
                <SettingsCategory name='General'>
                    <SettingsField name='Username' handleClick={() => openActiveForm(ActiveFormOption.CHANGE_USERNAME)} />
                    <SettingsField name='Password' handleClick={() => openActiveForm(ActiveFormOption.CHANGE_PASSWORD)} />
                </SettingsCategory>
                        
                <SettingsCategory name='Advanced'>
                    <SettingsField name='Delete account' handleClick={() => openActiveForm(ActiveFormOption.DELETE_ACCOUNT)} />
                </SettingsCategory>
            </div>
        </div>
    )
}

export default Settings