import { useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { useAuth } from '../Authentication/AuthContext'
import subredditIcon from '../../assets/subreddit-icon.svg'
import plusIcon from '../../assets/plus-icon.svg'

const Subreddit = () => {
    const [users, setUser] = useState([])
    const { title } = useParams()
    const { user, isAuthenticated } = useAuth()
    const navigate = useNavigate()

    const checkForAuthentication = () => {
        if (!isAuthenticated) {
            navigate('/login')
        }
    }

    const handleCreatePost = () => {
        
    }

    const handleJoin = () => {

    }

    const handleLeave = () => {
        
    }

    return (
        <div className='w-4/5 h-full mx-auto'>
            <div className='mx-4 my-12 py-5 flex justify-between items-center'>
                <div className='flex items-center space-x-2'>
                    <img className='w-20 h-2w-20' src={subredditIcon} alt='subreddit' />
                    <h1 className='text-4xl page-header'>
                        r/{title}
                    </h1>
                </div>

                <div className='flex justify-center space-x-4 items-center'>
                    <button className='w-32 py-2 flex items-center text-gray-800 font-semibold border border-gray-600 hover:border-black rounded-full'
                            onClick={handleCreatePost}>    
                        <img className='w-6 h-6 mx-1.5' src={plusIcon} alt='+' />
                        <span>Create Post</span>
                    </button>

                    <button className='w-24 p-2 bg-gray-800 hover:bg-gray-950 text-gray-200 font-semibold rounded-full'
                            onClick={handleJoin}>
                        Join
                    </button>

                    <button className='w-24 p-2 text-gray-800 font-semibold border border-gray-600 hover:border-black rounded-full' 
                            onClick={handleLeave}>
                        Joined
                    </button>
                </div>
            </div>

            <div className='border-t border-gray-300'>

            </div>
        </div>
    )
}

export default Subreddit