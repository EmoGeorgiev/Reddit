
const Navbar = ({ handleCollapse }) => {
    return (
        <nav className='w-full top-0 fixed h-16  bg-white border border-gray-300 flex justify-around items-center'>
            
            <button onClick={handleCollapse}>
                Collapse
            </button> 
            
            <h2>Reddit</h2>

            <p>Search</p>

            <p>Options</p>
        </nav>
    )
}

export default Navbar