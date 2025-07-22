import { useNavigate } from 'react-router-dom'
import plusIcon from '../../assets/plus-icon.svg'

const SubredditButtonPanel = ({ title, isMember, isModerator, leaveSubreddit, joinSubreddit }) => {
    const navigate = useNavigate()

    return (
        <div className='flex justify-center space-x-4 items-center'>
            <button className='w-32 py-2 flex items-center text-gray-800 font-semibold border border-gray-600 hover:border-black rounded-full'
                    onClick={() => navigate(`/r/${title}/submit`)}>    
                <img className='w-6 h-6 mx-1.5' src={plusIcon} alt='+' />
                <span>Create Post</span>
            </button>

            {isMember ? 
                <button className='w-24 p-2 text-gray-800 font-semibold border border-gray-600 hover:border-black rounded-full' 
                onClick={leaveSubreddit}>
                    Joined
                </button>  :
                <button className='w-24 p-2 bg-gray-800 hover:bg-black text-gray-200 font-semibold rounded-full'
                onClick={joinSubreddit}>
                    Join
                </button>}
            
            {isModerator && 
                <button className='w-24 p-2 bg-blue-800 hover:bg-blue-900 text-gray-200 font-semibold rounded-full' 
                        onClick={() => navigate(`/r/${title}/moderators`)}>
                    Mod Tools
                </button>}
        </div>
    )
}

export default SubredditButtonPanel