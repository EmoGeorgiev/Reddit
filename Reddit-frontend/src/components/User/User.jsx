import { useParams } from 'react-router-dom'
import { useAuth } from '../Authentication/AuthContext'
import { useEffect, useState } from 'react'
import userService from '../../services/users'
import UserPosts from './UserPosts'
import UserComments from './UserComments'
import UserSaved from './UserSaved'
import UserUpvoted from './UserUpvoted'
import UserDownvoted from './UserDownvoted'

const User = () => {
    const [profile, setProfile] = useState(null)
    const { username } = useParams()
    const { user, isAuthorized } = useAuth()
    
    useEffect(() => {
        getUser()
    }, [])

    const getUser = async () => {
        try {
            const newProfile = await userService.getUserByUsername(username)
            setProfile(newProfile)   
        } catch (error) {
            console.log(error)
        }
    }
 
    if (profile === null) {
        return (
            <div>
                <p>Sorry, nobody on Reddit goes by the name {username}.</p>
                <p>This account may have been banned or the username is incorrect.</p>
            </div>
        )
    }

    return (
        <div>
            <h1>
                u/{profile.username}
            </h1>

            <UserPosts id={profile.id} />
            <UserComments id={profile.id} />
            {user !== null && user.id === profile.id && <UserSaved id={profile.id} />}
            {user !== null && user.id === profile.id && <UserUpvoted id={profile.id} />}
            {user !== null && user.id === profile.id && <UserDownvoted id={profile.id} />}
        </div>
    )
}

export default User