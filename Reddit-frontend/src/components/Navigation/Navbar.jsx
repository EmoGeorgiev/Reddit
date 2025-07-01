import Collapse from './Collapse'
import Options from './Options'
import Search from './Search'

const Navbar = ({ handleCollapse }) => {
    const isAuthenticated = true
    
    return (
        <nav className='w-full top-0 fixed h-16 bg-white border border-gray-300 flex items-center'>
            <div className='ml-4 flex-1 flex justify-start space-x-4'>
                {isAuthenticated && <Collapse handleCollapse={handleCollapse} />}
                <h2>Reddit</h2>
            </div>

            <div className='flex-1 flex justify-center'>
                <Search />
            </div>

            <div className='mr-4 flex-1 flex justify-end'>
                {!isAuthenticated && <button className='w-16 p-2 auth-btn'>Log In</button>}
                {isAuthenticated && <Options />}
            </div>
        </nav>
    )
}

export default Navbar