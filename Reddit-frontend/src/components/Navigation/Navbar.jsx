import { useNavigate } from 'react-router-dom'
import Collapse from './Collapse'
import ProfileMenu from './ProfileMenu'
import Search from './Search'

const Navbar = ({ handleCollapse, isAuthenticated }) => {
    const navigate = useNavigate()

    return (
        <nav className='w-full top-0 fixed h-14 border border-gray-300 flex items-center'>
            <div className='pl-10 flex-1 flex justify-start space-x-4'>
                {isAuthenticated && <Collapse handleCollapse={handleCollapse} />}
                <h2>Reddit</h2>
            </div>

            <div className='flex-1'>
                <Search />
            </div>

            <div className='pr-10 flex-1 flex justify-end'>
                {!isAuthenticated && 
                    <button className='w-16 p-2 auth-btn focus:border focus-item' 
                            onClick={() => navigate('/login')}>
                        Log In
                    </button>}
                {isAuthenticated && <ProfileMenu />}
            </div>
        </nav>
    )
}

export default Navbar