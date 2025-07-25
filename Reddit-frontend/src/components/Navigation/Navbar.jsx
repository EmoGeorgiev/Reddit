import { useNavigate } from 'react-router-dom'
import { useAuth } from '../../hooks/useAuth'
import { Link } from 'react-router-dom'
import ProfileMenu from '../Profile/ProfileMenu'
import SearchBar from '../Search/SearchBar'
import menuIcon from '../../assets/menu-icon.svg'
import redditIcon from '../../assets/reddit-logo-icon.svg'

const Navbar = ({ handleCollapse }) => {
    const navigate = useNavigate()
    const { isAuthenticated } = useAuth()

    return (
        <nav className='w-full top-0 fixed h-14 border border-gray-300 flex items-center'>
            <div className='mx-5 flex-1 flex justify-start items-center space-x-4 '>
                {isAuthenticated && 
                    <button onClick={handleCollapse}>
                        <img className='w-5 h-5' src={menuIcon} alt='Collapse' />
                    </button> }
                <Link to='/'>
                    <img className='w-24 h-24' src={redditIcon} alt='reddit' />
                </Link>
                
            </div>

            <div className='flex-1'>
                <SearchBar />
            </div>

            <div className='mx-5 flex-1 flex justify-end '>
                {!isAuthenticated && 
                    <button className='w-16 p-1.5 auth-btn focus-item' 
                            onClick={() => navigate('/login')}>
                        Log In
                    </button>}
                {isAuthenticated && <ProfileMenu />}
            </div>
        </nav>
    )
}

export default Navbar