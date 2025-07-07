import { useLocation } from 'react-router-dom'
import { useState } from 'react'
import userIcon from '../../assets/user-icon.svg'
import Vote from '../Vote/Vote'
import subredditIcon from '../../assets/subreddit-icon.svg'
import CommentCount from '../Comment/CommentCount'

const Post = ({ post }) => {
    const [user, setUser] = useState('user')
    const date = post.created.split('T')[0]
    const location = useLocation()
    
    const showUser = location.pathname.includes(`/r/`)

    return (
        <>    
            <div className='w-full h-auto my-4 hover:bg-gray-100 overflow-hidden rounded-2xl'>
                <div className='mx-4 py-4 h-full flex flex-col space-y-2.5 '>
                    <div className='flex justify-between items-center'>
                        {showUser ? 
                            <div className='flex space-x-1.5 items-center'>
                                <img className='w-10 h-10' src={userIcon} alt='user icon' />
                                <span className='font-semibold'>u/{user}</span>
                            </div> :
                            <div className='flex space-x-1.5 items-center'>
                                <img className='w-10 h-10' src={subredditIcon} alt='subreddit' />
                                <span className='font-semibold'>r/AskReddit</span>
                            </div>}

                        <div className='font-light'>
                            {date}
                        </div>
                    </div>

                    <div className='text-lg font-semibold'>
                        {post.title}
                    </div>

                    <div className='font-light break-words'>
                        {post.text}
                    </div>

                    <div className='flex space-x-4 items-center'>
                        <Vote score={post.score} />
                        <CommentCount count={post.commentCount} />
                    </div>
                </div>
            </div>

            <div className='mt-4 border-t border-gray-300'></div>
        </>
    )
}

export default Post