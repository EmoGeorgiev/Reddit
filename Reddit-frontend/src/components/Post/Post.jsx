import { useLocation, useNavigate } from 'react-router-dom'
import Vote from '../Common/Vote'
import Save from '../Common/Save'
import CommentCount from '../Comment/CommentCount'
import ContentDelete from '../Content/ContentDelete'
import ContentDate from '../Content/ContentDate'
import ContentText from '../Content/ContentText'
import ContentButtonPanel from '../Content/ContentButtonPanel'
import ContentUserHeader from '../Content/ContentUserHeader'
import ContentTitle from '../Content/ContentTitle'
import ContentHeader from '../Content/ContentHeader'
import ContentSubredditHeader from '../Content/ContentSubredditHeader'
import ContentEdit from '../Content/ContentEdit'

const Post = ({ post, deletePost }) => {
    const location = useLocation()
    const navigate = useNavigate()
    
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
        <div className='w-full h-auto my-4 hover:bg-gray-100 overflow-hidden border border-gray-300 rounded-4xl cursor-pointer'
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
                    <ContentEdit creatorId={post.user.id} handleEdit={null} />
                    <ContentDelete creatorId={post.user.id} subreddit={post.subreddit} handleDelete={handleDelete} />
                </ContentButtonPanel>
            </div>
        </div>
    )
}

export default Post