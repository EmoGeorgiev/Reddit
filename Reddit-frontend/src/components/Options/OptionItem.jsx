import arrowIcon from '../../assets/expand-arrow-icon.svg'

const OptionItem = ({ name, handleClick }) => {
    return (
        <div onClick={handleClick} className='w-full p-2 mx-2 flex justify-between items-center text-gray-700 cursor-pointer group'>
            <span>
                {name}
            </span>
            
            <span className='w-8 h-8 flex justify-center items-center font-extrabold group-hover:bg-gray-300 rounded-full'>
                <img className='expand-arrow' src={arrowIcon} alt='arrow' />
            </span>
        </div>
    )
}

export default OptionItem