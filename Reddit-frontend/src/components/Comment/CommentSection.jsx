import { useNavigate, useParams } from 'react-router-dom'
import { useEffect, useState } from 'react'
import Post from '../Post/Post'
import MissingContent from '../Common/MissingContent'
import CommentList from './CommentList'
import postService from '../../services/posts'
import commentService from '../../services/comments'

const CommentSection = () => {
    const [post, setPost] = useState(null)
    const [comments, setComments] = useState([])
    const { name, postId } = useParams()
    const navigate = useNavigate()

    useEffect(() => {
        const getPost = async () => {
            try {
                const newPost = await postService.getPost(postId)
                
                setPost(newPost)
            } catch (error) {
                console.log(error)
            }
        }

        getPost()
    }, [postId])

    useEffect(() => {
        const getComments = async () => {
            try {
                const newComments = await commentService.getCommentsByPostId(postId, { 'page': 0 })

                console.log(newComments.content)

                setComments(newComments.content)
            } catch (error) {
                console.log(error)
            }
        }

        getComments()
    }, [postId])

    if (post === null) {
        return <MissingContent heading='Post not found' 
                                text={`There aren't any posts on ${name} that match your description`}
                                button='Browse other posts'
                                handleClick={() => navigate(`/r/${name}`)} />
    }

    return (
        <div className='w-4/5 mx-auto'>
            <Post post={post} deletePost={null} />

            <CommentList comments={comments} />
        </div>
    )
}

export default CommentSection