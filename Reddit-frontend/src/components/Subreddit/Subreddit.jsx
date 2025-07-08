import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { useAuth } from '../Authentication/AuthContext'
import subredditService from '../../services/subreddits'
import postService from '../../services/posts'
import MissingContent from '../Common/MissingContent'
import SubredditPanel from './SubredditPanel'
import PostList from '../Post/PostList'

const Subreddit = () => {
    const [subreddit, setSubreddit] = useState(null)
    const [users, setUsers] = useState(new Set())
    const [moderators, setModerators] = useState(new Set())
    const { name } = useParams()
    const { user } = useAuth()
    const navigate = useNavigate()

    useEffect(() => {
        const getSubreddit = async () => {
            try {
                const newSubreddit = await subredditService.getSubredditByTitle(name)

                setSubreddit(newSubreddit)
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

    const getPosts = async (id, page) => {
        const pagePost = await postService.getPostsBySubredditId(id, page)
        return pagePost
    }

    if (subreddit === null) {
        return <MissingContent heading='Subreddit not found' 
                                text={`There aren't any subreddits on Reddit with the name "${name}". Double-check the subreddit name or try searching for similar subreddits`}
                                button='Browse other subreddits'
                                handleClick={() => navigate('/')} />
    }

    return (
        <div className='w-4/5 h-full mx-auto'>
            <SubredditPanel title={subreddit.title}
                            isMember={users.has(user)}
                            isModerator={true}
                            leaveSubreddit={leaveSubreddit} 
                            joinSubreddit={joinSubreddit} />

            <div className='border-t border-gray-300'>
                <PostList query={subreddit.id} getPosts={getPosts} />
            </div>
        </div>
    )
}

export default Subreddit