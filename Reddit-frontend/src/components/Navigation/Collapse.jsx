import menuIcon from '../../assets/menu-icon.svg'

const Collapse = ({ handleCollapse }) => {
    return (
        <button onClick={handleCollapse}>
            <img className='w-5 h-5' src={menuIcon} alt='Collapse' />
        </button> 
    )
}

export default Collapse