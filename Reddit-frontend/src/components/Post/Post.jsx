import { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { useAuth } from '../Authentication/AuthContext'
import userIcon from '../../assets/user-icon.svg'
import Vote from '../Common/Vote'
import Save from '../Common/Save'
import CommentCount from '../Comment/CommentCount'
import DeleteContent from '../Common/DeleteContent'
import userService from '../../services/users'
import subredditIcon from '../../assets/subreddit-icon.svg'

const Post = ({ post, deletePost }) => {
    const [moderators, setModerators] = useState(new Set())
    const { user } = useAuth()
    const date = post.created.split('T')[0]
    const location = useLocation()
    const navigate = useNavigate()
    
    useEffect(() => {
        const getModerators = async () => {
            try {
                const newModerators = await userService.getModeratorsBySubredditTitle(post.subreddit.title)
                
                setModerators(new Set(newModerators.map(moderator => moderator.id)))
            } catch (error) {
                console.log(error)
            }
        }

        getModerators()
    }, [])
    
    const showUser = () => {
        const subredditPath = `/r/${post.subreddit.title}`.toLowerCase()
        const currentPath = location.pathname.toLowerCase()

        return currentPath.includes(subredditPath)
    }

    const handleDelete = () => {
        if (window.confirm('Are you sure you want to delete this post?')) {
            deletePost(post.id)
        }
    }

    const handleRedirect = () => {
        navigate(`/r/${post.subreddit.title}/comments/${post.id}`)
    }

    return (
        <>    
            <div className='w-full h-auto my-4 hover:bg-gray-100 overflow-hidden rounded-2xl cursor-pointer'
                onClick={handleRedirect}>
                <div className='mx-4 py-4 h-full flex flex-col space-y-2.5 '>
                    <div className='flex justify-between items-center'>
                        {showUser() ? 
                            <div className='flex space-x-1.5 items-center'>
                                <img className='w-10 h-10' src={userIcon} alt='user icon' />
                                <span className='font-semibold'>u/{post.user.username}</span>
                            </div> :
                            <div className='flex space-x-1.5 items-center'>
                                <img className='w-10 h-10' src={subredditIcon} alt='subreddit' />
                                <span className='font-semibold'>r/{post.subreddit.title}</span>
                            </div>}

                        <div className='font-light'>
                            {date}
                        </div>
                    </div>
                    
                    <div className='text-lg font-semibold'>
                        {post.title}
                    </div>

                    <div className='font-light break-words'>
                        {post.description}
                    </div>

                    <div className='flex space-x-4 items-center'>
                        <Vote contentId={post.id} contentScore={post.score} />
                        <CommentCount count={post.commentCount} handleClick={handleRedirect} />
                        <Save contentId={post.id} />
                        {(user?.id === post.user.id || moderators.has(user?.id)) && <DeleteContent handleDelete={handleDelete} />}
                    </div>
                </div>
            </div>

            <div className='mt-4 border-t border-gray-300'></div>
        </>
    )
}

export default Post