import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import Vote from '../Common/Vote'
import Save from '../Common/Save'
import CommentCount from '../Comment/CommentCount'
import ContentDelete from '../Content/ContentDelete'
import ContentText from '../Content/ContentText'
import ContentButtonPanel from '../Content/ContentButtonPanel'
import ContentTitle from '../Content/ContentTitle'
import ContentHeader from '../Content/ContentHeader'
import ContentEdit from '../Content/ContentEdit'
import UpdateTextForm from '../Common/UpdateTextForm'
import postService from '../../services/posts'

const Post = ({ post, deletePost }) => {
    const [postState, setPostState] = useState(post)
    const [isEdited, setIsEdited] = useState(false)
    const navigate = useNavigate()

    const updatePost = async (description) => {
        try {
            const newPost = {
                ...postState,
                description
            }

            const updatedPost = await postService.updatePost(postState.id, newPost)
            setPostState(updatedPost)
        } catch (error) {
            console.log(error)
        }
    }
    
    const handleDelete = () => {
        if (window.confirm('Are you sure you want to delete this post?')) {
            deletePost(postState.id)
        }
    }

    const redirect = () => {
        navigate(`/r/${post.subreddit.title}/comments/${postState.id}`)
    }

    const handleRedirect = (e) => {
        const tag = e.target.tagName.toLowerCase()
        
        if (['button', 'img', 'span'].includes(tag)) {
            return
        }

        redirect()
    }

    return ( 
        <div className='w-full h-auto my-4 hover:bg-gray-100 overflow-hidden border border-gray-300 rounded-4xl cursor-pointer'
            onClick={handleRedirect}>
            <div className='mx-4 py-4 h-full flex flex-col space-y-2.5'>
                <ContentHeader user={postState.user} 
                                subreddit={postState.subreddit} 
                                created={postState.created} />

                <ContentTitle title={postState.title} />
                
                {isEdited && <UpdateTextForm handleClose={() => setIsEdited(false)} 
                                originalText={postState.description || ''} 
                                updateContent={updatePost} />}

                <ContentText text={postState.description} />

                <ContentButtonPanel>
                    <Vote contentId={postState.id} contentScore={postState.score} />
                    <CommentCount count={postState.commentCount} handleClick={redirect} />
                    <Save contentId={postState.id} />
                    <ContentEdit creatorId={postState.user.id} handleEdit={() => setIsEdited(true)} />
                    <ContentDelete creatorId={postState.user.id} 
                                    subreddit={postState.subreddit} 
                                    handleDelete={handleDelete} />
                </ContentButtonPanel>
            </div>
        </div>
    )
}

export default Post