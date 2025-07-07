import { useNavigate, useParams } from 'react-router-dom'
import { useAuth } from '../Authentication/AuthContext'
import { useEffect, useState } from 'react'
import MissingContent from '../Common/MissingContent'
import subredditService from '../../services/subreddits'
import postService from '../../services/posts'

const CreatePostForm = () => {
    const [subreddit, setSubreddit] = useState(null)
    const [title, setTitle] = useState('')
    const [description, setDescription] = useState('')
    const { name } = useParams()
    const { user } = useAuth()
    const navigate = useNavigate()

    useEffect(() => {
        const getSubredditByTitle = async () => {
            try {
                const newSubreddit = await subredditService.getSubredditByTitle(name)

                setSubreddit(newSubreddit)
            } catch (error) {
                console.log(error)
            }
        }

        getSubredditByTitle()
    }, [name])

    const createPost = (e) => {
        e.preventDefault()


    }

    if (subreddit === null) {
        return <MissingContent heading='Subreddit not found' 
                                text={`There aren't any subreddits on Reddit with the name "${name}". Double-check the subreddit name or try searching for similar subreddits`}
                                button='Browse other subreddits'
                                handleClick={() => navigate('/')} />
    }

    return (
        <div className='w-2/3 h-1/2 my-8 mx-auto flex flex-col items-center'>
            <h1 className='my-4 text-2xl active-form-heading'>
                Create Post
            </h1>    


            <form className='w-3/4 h-3/4 flex flex-col justify-around' onSubmit={createPost}>
                <input 
                    className='p-4 border border-gray-400 rounded-3xl focus:outline-none focus:border-2 focus:border-blue-400'
                    type='text'
                    value={title}
                    name='title'
                    placeholder='Title'
                    onChange={(e) => setTitle(e.target.value)} 
                />

                <input 
                    className='h-1/2 p-4 border border-gray-400 rounded-3xl focus:outline-none focus:border-2 focus:border-blue-400'
                    type='text'
                    value={description}
                    name='description'
                    placeholder='Description (optional)'
                    onChange={(e) => setDescription(e.target.value)} 
                />

                <div className='flex justify-end'>
                    <button className='active-form-cancel-btn'>
                        Cancel
                    </button>

                    <button className='active-form-confirm-btn'>
                        Post
                    </button>
                </div>
            </form>
        </div>
    )
}

export default CreatePostForm