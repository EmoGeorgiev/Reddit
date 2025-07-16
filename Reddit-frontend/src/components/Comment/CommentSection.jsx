import { useNavigate, useParams } from 'react-router-dom'
import { useEffect, useState } from 'react'
import { SortOptions } from '../../util/SortOptions'
import { SizeOptions } from '../../util/SizeOptions'
import Post from '../Post/Post'
import MissingContent from '../Common/MissingContent'
import CommentList from './CommentList'
import CreateCommentForm from './CreateCommentForm'
import postService from '../../services/posts'
import commentService from '../../services/comments'
import SortSelect from '../Common/SortSelect'
import CommentSectionSize from './CommentSectionSize'

const CommentSection = () => {
    const [post, setPost] = useState(null)
    const [comments, setComments] = useState([])
    const [isOpen, setIsOpen] = useState(false)
    const [sort, setSort] = useState(SortOptions.Top)
    const [size, setSize] = useState(SizeOptions.Default)
    const [totalElements, setTotalElements] = useState(0)
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
                const newComments = await commentService.getCommentsByPostId(postId, { 'page': 0, size, 'sort': `${sort},desc` })
                setTotalElements(newComments.totalElements)
                setComments(newComments.content)
            } catch (error) {
                console.log(error)
            }
        }

        getComments()
    }, [postId, sort, size])

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
            
            <div className='m-4 flex flex-col space-y-2 text-gray-700'>
                <CommentSectionSize length={comments.length} totalElements={totalElements} setSize={setSize} />
                <SortSelect selected={sort} handleChange={setSort} />
            </div>

            <CreateCommentForm isOpen={isOpen} setIsOpen={setIsOpen} createComment={createComment} postId={postId} parentId={null} />
            <CommentList comments={comments} deleteComment={deleteComment} />
        </div>
    )
}

export default CommentSection