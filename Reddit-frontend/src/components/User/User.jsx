import { useNavigate, useParams } from 'react-router-dom'
import { useAuth } from '../Authentication/AuthContext'
import { useEffect, useState } from 'react'
import userService from '../../services/users'
import UserPosts from './UserPosts'
import UserComments from './UserComments'
import UserSaved from './UserSaved'
import UserUpvoted from './UserUpvoted'
import UserDownvoted from './UserDownvoted'
import UserCategory from './UserCategory'
import userIcon from '../../assets/user-icon.svg'

const Category = Object.freeze({
    COMMENTS: 'COMMENTS',
    POSTS: 'POSTS',
    UPVOTED: 'UPVOTED',
    DOWNVOTED: 'DOWNVOTED',
    SAVED: 'SAVED'
})

const User = () => {
    const [currentCategory, setCurrentCategory] = useState(Category.COMMENTS)
    const [profile, setProfile] = useState(null)
    const { username } = useParams()
    const { user } = useAuth()
    const navigate = useNavigate()
    
    useEffect(() => {
        const getUser = async () => {
            try {
                const newProfile = await userService.getUserByUsername(username)
                setProfile(newProfile)   
            } catch (error) {
                console.log(error)
            }
        }

        getUser()
    }, [username])

    const handleCategoryChange = (category) => {
        setCurrentCategory(category)
    }
 
    if (profile === null) {
        return (
            <div className='h-full flex flex-col justify-center items-center space-y-3'>
                <p className='page-header font-semibold'>
                    Sorry, nobody on Reddit goes by the name "{username}".
                </p>
                <p className='text-lg text-center font-light'>
                    This account may have been deleted or the username is incorrect.
                </p>
                <button className='p-2 text-sm text-gray-200 font-semibold bg-blue-800 hover:bg-blue-900 rounded-full'
                        onClick={() => navigate('/')}>
                    View Other Communities
                </button>
            </div>
        )
    }

    return (
        <div>
            <div className='flex justify-center space-x-3 items-center'>
                <img src={userIcon} alt='icon' />
                <h1 className='my-10 page-header'>
                    u/{profile.username}
                </h1>
            </div>

            <div className='mx-32'>
                <div className='flex justify-center'>
                    <UserCategory name='Comments' 
                                category={Category.COMMENTS} 
                                currentCategory={currentCategory} 
                                changeCategory={handleCategoryChange} />

                    <UserCategory name='Posts' 
                                category={Category.POSTS} 
                                currentCategory={currentCategory} 
                                changeCategory={handleCategoryChange} />

                    {user?.id === profile.id && 
                        <UserCategory name='Upvoted' 
                                    category={Category.UPVOTED} 
                                    currentCategory={currentCategory} 
                                    changeCategory={handleCategoryChange} />
                    }

                    {user?.id === profile.id && 
                        <UserCategory name='Downvoted' 
                                    category={Category.DOWNVOTED} 
                                    currentCategory={currentCategory} 
                                    changeCategory={handleCategoryChange} />
                    }
                    
                    {user?.id === profile.id && 
                        <UserCategory name='Saved' 
                                    category={Category.SAVED} 
                                    currentCategory={currentCategory} 
                                    changeCategory={handleCategoryChange} />
                    }
                </div>

                <div>
                    {currentCategory === Category.COMMENTS && <UserComments id={profile.id} />}
                    {currentCategory === Category.POSTS && <UserPosts id={profile.id} />}
                    {currentCategory === Category.UPVOTED && <UserUpvoted id={profile.id} />}
                    {currentCategory === Category.DOWNVOTED && <UserDownvoted id={profile.id} />}
                    {currentCategory === Category.SAVED && <UserSaved id={profile.id} />} 
                </div>
            </div>
        </div>
    )
}

export default User