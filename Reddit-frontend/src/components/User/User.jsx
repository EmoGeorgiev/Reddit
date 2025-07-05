import { useNavigate, useParams } from 'react-router-dom'
import { useAuth } from '../Authentication/AuthContext'
import { useEffect, useState } from 'react'
import { Category } from '../../util/Category'
import userService from '../../services/users'
import MissingContent from './MissingContent'
import UserPosts from './UserPosts'
import UserComments from './UserComments'
import UserSaved from './UserSaved'
import UserUpvoted from './UserUpvoted'
import UserDownvoted from './UserDownvoted'
import userIcon from '../../assets/user-icon.svg'
import CategoryList from '../Category/CategoryList'

const User = () => {
    const [profile, setProfile] = useState(null)
    const { username } = useParams()
    const { user } = useAuth()
    const navigate = useNavigate()
    
    const main = [Category.COMMENTS, Category.POSTS]
    const extra = [Category.UPVOTED, Category.DOWNVOTED, Category.SAVED]
    const categories = user?.id === profile?.id ? [...main, ...extra] : main

    const categoryComponents = {
        [Category.COMMENTS]: <UserComments id={profile?.id} />,
        [Category.POSTS]: <UserPosts id={profile?.id} />,
        [Category.UPVOTED]: <UserUpvoted id={profile?.id} />,
        [Category.DOWNVOTED]: <UserDownvoted id={profile?.id} />,
        [Category.SAVED]: <UserSaved id={profile?.id} />
    }
    
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
 
    if (profile === null) {
        return (
            <MissingContent heading={`Sorry, nobody on Reddit goes by the name "${username}".`}
                            text='This account may have been deleted or the username is incorrect.'
                            button='View Other Communities'
                            handleClick={() => navigate('/')} />
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

            <CategoryList defaultCategory={Category.COMMENTS} 
                        categories={categories} 
                        categoryComponents={categoryComponents} />
        </div>
    )
}

export default User