import { Link } from 'react-router-dom'
import userIcon from '../../assets/user-icon.svg'

const ContentUserHeader = ({user}) => {
    const username = user ? user.username : 'deleted'

    return (
        <Link className={`${!user && 'cursor-default'}`} to={user && `/users/${username}`}>
            <div className='flex space-x-1.5 items-center'>
                <img className='w-10 h-10' src={userIcon} alt='user icon' />
                <span className='font-semibold'>u/{username}</span>
            </div>
        </Link>
    )
}

export default ContentUserHeader