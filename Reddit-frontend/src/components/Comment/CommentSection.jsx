import { useNavigate, useParams } from 'react-router-dom'
import { useEffect, useState } from 'react'
import Post from '../Post/Post'
import MissingContent from '../Common/MissingContent'
import CommentList from './CommentList'
import CreateCommentForm from './CreateCommentForm'
import postService from '../../services/posts'
import commentService from '../../services/comments'

const CommentSection = () => {
    const [post, setPost] = useState(null)
    const [comments, setComments] = useState([])
    const [isOpen, setIsOpen] = useState(false)
    const { name, postId } = useParams()
    const navigate = useNavigate()

    useEffect(() => {
        const getPost = async () => {
            try {
                const newPost = await postService.getPost(postId)
                
                if (newPost.subreddit.title === name) {
                    setPost(newPost)
                }
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
                setComments(newComments.content)
            } catch (error) {
                console.log(error)
            }
        }

        getComments()
    }, [postId])

    const deletePost = async () => {
        try {
            
        } catch (error) {
            console.log(error)
        }
    }


    const createComment = async (comment) => {
        try {
            const newComment = await commentService.addComment(comment)

            setComments([...comments, newComment])
        } catch (error) {
            console.log(error)
        }
    }

    const deleteComment = async (commentId, userId) => {
        try {
            const deletedComment = await commentService.deleteComment(commentId, userId)
            console.log(deletedComment)
            setComments(comments.map(comment => comment.id === commentId ? deletedComment : comment))
        } catch (error) {
            console.log(error)
        }
    }

    if (post === null) {
        return <MissingContent heading='Post not found' 
                                text={`There aren't any posts on ${name} that match your description`}
                                button='Browse other posts'
                                handleClick={() => navigate(`/r/${name}`)} />
    }

    return (
        <div className='w-4/5 h-auto mx-auto'>
            <Post post={post} deletePost={deletePost} />
            <CreateCommentForm isOpen={isOpen} setIsOpen={setIsOpen} createComment={createComment} postId={postId} parentId={null} />
            <CommentList comments={comments} deleteComment={deleteComment} />
        </div>
    )
}

export default CommentSection