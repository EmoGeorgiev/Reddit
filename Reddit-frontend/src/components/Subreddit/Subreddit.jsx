import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { useAuth } from '../Authentication/AuthContext'
import subredditService from '../../services/subreddits'
import userService from '../../services/users'
import postService from '../../services/posts'
import MissingContent from '../Common/MissingContent'
import SubredditPanel from './SubredditPanel'
import PostPage from '../Post/PostPage'

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

    useEffect(() => {
        const getUsersAndModerators = async () => {
            try {
                const [users, moderators] = await Promise.all([
                    userService.getUsersBySubredditTitle(name),
                    userService.getModeratorsBySubredditTitle(name),
                ])

                const userSet = new Set(users.map(u => u.id))
                const moderatorSet = new Set(moderators.map(m => m.id))

                setUsers(userSet)
                setModerators(moderatorSet)
            } catch (error) {
                console.log(error)
            }
        }

        getUsersAndModerators()
    }, [name])

    const joinSubreddit = async () => {
        try {
            await subredditService.addSubredditToUserSubscriptions(subreddit.title, user.id)

            setUsers(new Set(users).add(user.id))
        } catch (error) {
            console.log(error)
        }
    }

    const leaveSubreddit = async () => {
        try {
            await subredditService.removeSubredditFromUserSubscriptions(subreddit.title, user.id)

            setUsers(new Set([...users].filter(id => id != user.id)))
            setModerators(new Set([...users].filter(id => id != user.id)))
        } catch (error) {
            console.log(error)
        }
    }

    const getPosts = async (id, pageable) => {
        const postPage = await postService.getPostsBySubredditId(id, pageable)
        return postPage
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
                            isMember={users.has(user?.id)}
                            isModerator={moderators.has(user?.id)}
                            leaveSubreddit={leaveSubreddit} 
                            joinSubreddit={joinSubreddit} />

            

            <div className='border-t border-gray-300'>
                <PostPage query={subreddit.id} getPosts={getPosts} />
            </div>
        </div>
    )
}

export default Subreddit