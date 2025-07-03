import { useState } from 'react'
import { useAuth } from '../Authentication/AuthContext'
import { Link } from 'react-router-dom'
import userIcon from '../../assets/user-icon.svg'
import settingsIcon from '../../assets/settings-icon.svg'
import logoutIcon from '../../assets/logout-icon.svg'
import ProfileMenuField from './ProfileMenuField'

const ProfileMenu = () => {
    const [isOpen, setIsOpen] = useState(false)
    const { logout, user } = useAuth()
    
    const handleClose = () => {
        setIsOpen(false)
    }

    const handleLogOut = () => {
        logout()

        handleClose()
    }

    return (
        <div className='relative'>
            <button onClick={() => setIsOpen(!isOpen)}>
                <img src={userIcon} alt={`u/${user.username}`} />
            </button>

            {isOpen && <button className='fixed top-0 right-0 bottom-0 left-0' onClick={handleClose}></button>}
            
            <div className={`${isOpen ? 'opacity-100 visible' : 'opacity-0 invisible'} absolute -right-5 w-48 bg-white border border-gray-300 rounded-2xl overflow-hidden shadow-2xl duration-200`}>
               <ProfileMenuField path='/' icon={userIcon} alt='user' handleClose={handleClose}>
                    <div>
                        View Profile
                    </div>
                    <div className='text-sm font-light'>
                        u/{user.username}
                    </div>
                </ProfileMenuField>

                <ProfileMenuField path='/settings' icon={settingsIcon} alt='settings' handleClose={handleClose}>
                    <div>
                        Settings
                    </div>
                </ProfileMenuField>

                <ProfileMenuField path='/' icon={logoutIcon} alt='logout' handleClose={handleLogOut}>
                    <div>
                        Log Out
                    </div>
                </ProfileMenuField>
            </div>
        </div>
    )
}

export default ProfileMenu