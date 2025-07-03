import { useState } from 'react'
import { Link } from 'react-router-dom'
import { useAuth } from '../Authentication/AuthContext'
import SubscriptionsList from './SubscriptionsList'

const Sidebar = ({ collapsed }) => {
    const [isOpen, setIsOpen] = useState(true)
    const { isAuthenticated } = useAuth()
    
    const handleOpen = () => {
        setIsOpen(!isOpen)
    }

    if (!isAuthenticated) {
        return <></>
    }

    return (
        <div className={`h-full ${collapsed ? 'w-0' : 'w-68'} ${isOpen ? 'overflow-y-auto' : 'overflow-y-hidden'} duration-700 border border-gray-300`}>
           {!collapsed && 
                <div className='mt-4'>    
                    <Link to='/' className='w-56 ml-4 px-4 py-3 block font-semibold hover:bg-gray-200 rounded-2xl'>All</Link>
                    <Link to='/' className='w-56 ml-4 px-4 py-3 block font-semibold hover:bg-gray-200 rounded-2xl'>Home</Link>

                    <div className='my-4 mx-6 border border-gray-200'></div>

                    <SubscriptionsList isOpen={isOpen} handleOpen={handleOpen} />    
                </div>} 
        </div>
    )
}

export default Sidebar