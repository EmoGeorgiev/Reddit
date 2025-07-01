import { useState } from 'react'

const ProfileMenu = () => {
    const [isOpen, setIsOpen] = useState(false)
    
    return (
        <div className='relative'>
            <button onClick={() => setIsOpen(!isOpen)}>
                ProfileMenu
            </button>

            {/* This button is used to close the Profile menu by clicking anywhere  */}
            {isOpen && 
                <button className='fixed top-0 right-0 bottom-0 left-0' 
                        onClick={() => setIsOpen(false)}>
                </button>}
            
            <div className={`${isOpen ? 'opacity-100 visible' : 'opacity-0 invisible'} absolute -right-5 w-48 mt-5 bg-white border border-gray-300 rounded-2xl overflow-hidden shadow-2xl duration-200`}>
                <ul>
                    <li className='px-4 py-3 hover:bg-gray-200'>
                        <a href=''>View Profile</a>
                    </li>
                    <li className='px-4 py-3 hover:bg-gray-200'>
                        <a href=''>Settings</a>
                    </li>
                    <li className='px-4 py-3 hover:bg-gray-200'>
                        <a href=''>Log Out</a>
                    </li>
                </ul>
            </div>
        </div>
    )
}

export default ProfileMenu