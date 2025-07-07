import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { useAuth } from '../Authentication/AuthContext'
import subredditService from '../../services/subreddits'
import MissingContent from '../Common/MissingContent'
import SubredditPanel from './SubredditPanel'

const Subreddit = () => {
    const [subredditId, setSubredditId] = useState(null)
    const [title, setTitle] = useState('')
    const [users, setUsers] = useState(new Set())
    const [moderators, setModerators] = useState(new Set())
    const { name } = useParams()
    const { user } = useAuth()
    const navigate = useNavigate()

    useEffect(() => {
        const getSubreddit = async () => {
            try {
                const subreddit = await subredditService.getSubredditByTitle(name)

                setSubredditId(subreddit.id)
                setTitle(subreddit.title)
                setUsers(new Set(subreddit.userIds))
                setModerators(new Set(subreddit.moderatorIds))
            } catch (error) {
                console.log(error)
            }
        }

        getSubreddit()
    }, [name])

    const joinSubreddit = () => {

    }

    const leaveSubreddit = () => {
        
    }

    if (subredditId === null) {
        return <MissingContent heading='Subreddit not found' 
                                text={`There aren't any subreddits on Reddit with the name "${name}". Double-check the subreddit name or try searching for similar subreddits`}
                                button='Browse other subreddits'
                                handleClick={() => navigate('/')} />
    }

    return (
        <div className='w-4/5 h-full mx-auto'>
            <SubredditPanel title={title}
                            isMember={users.has(user.id)}
                            leaveSubreddit={leaveSubreddit} 
                            joinSubreddit={joinSubreddit} />

            <div className='border-t border-gray-300'>

            </div>
        </div>
    )
}

export default Subreddit