import { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { useAuth } from '../Authentication/AuthContext'
import Vote from '../Common/Vote'
import Save from '../Common/Save'
import CommentCount from '../Comment/CommentCount'
import DeleteContent from '../Content/DeleteContent'
import ContentDate from '../Content/ContentDate'
import ContentText from '../Content/ContentText'
import ContentButtonPanel from '../Content/ContentButtonPanel'
import ContentUserHeader from '../Content/ContentUserHeader'
import ContentTitle from '../Content/ContentTitle'
import userService from '../../services/users'
import ContentHeader from '../Content/ContentHeader'
import ContentSubredditHeader from '../Content/ContentSubredditHeader'

const Post = ({ post, deletePost }) => {
    const [moderators, setModerators] = useState(new Set())
    const { user } = useAuth()
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
                <div className='mx-4 py-4 h-full flex flex-col space-y-2.5'>
                    <ContentHeader>
                        {showUser() ? 
                            <ContentUserHeader user={post.user} /> :
                            <ContentSubredditHeader subreddit={post.subreddit} />}
                        <ContentDate created={post.created} />
                    </ContentHeader>

                    <ContentTitle title={post.title} />

                    <ContentText text={post.description} />

                    <ContentButtonPanel>
                        <Vote contentId={post.id} contentScore={post.score} />
                        <CommentCount count={post.commentCount} handleClick={handleRedirect} />
                        <Save contentId={post.id} />
                        <DeleteContent contentId={post.id} subreddit={post.subreddit} handleDelete={handleDelete} />
                    </ContentButtonPanel>
                </div>
            </div>

            <div className='mt-4 border-t border-gray-300'></div>
        </>
    )
}

export default Post