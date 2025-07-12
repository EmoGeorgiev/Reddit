import { useEffect, useState } from 'react'
import { useAuth } from '../Authentication/AuthContext'
import deleteIcon from '../../assets/delete-icon.svg'
import userService from '../../services/users'

const DeleteContent = ({ contentId, subreddit, handleDelete }) => {
    const [moderators, setModerators] = useState(new Set())
    const { user } = useAuth()

    useEffect(() => {
        const getModerators = async () => {
            try {
                const newModerators = await userService.getModeratorsBySubredditTitle(subreddit.title)
            
                setModerators(new Set(newModerators.map(moderator => moderator.id)))
            } catch (error) {
                console.log(error)
            }
        }

        getModerators()
    }, [])

    if (user?.id !== contentId && !moderators.has(user?.id)) {
        return <></>
    }

    return (
        <button className='px-1.5 py-1 flex items-center bg-gray-200 hover:bg-gray-300 rounded-full'
                onClick={handleDelete}>
            <img className='w-6 h-6' src={deleteIcon} alt='delete' />
            <span className='font-semibold'>Delete</span>
        </button>
    )
}

export default DeleteContent