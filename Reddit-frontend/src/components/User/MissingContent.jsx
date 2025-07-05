import thinkingIcon from '../../assets/thinking-icon.svg'

const MissingContent = ({ heading, text, button, handleClick }) => {
    return (
        <div className='h-full flex flex-col justify-center items-center space-y-3'>
            <img src={thinkingIcon} alt='missing' />
            
            <h1 className='page-header font-semibold'>
                {heading}
            </h1>

            <p className='text-lg text-center font-light'>
                {text}
            </p>

            <button className='p-2 text-sm text-gray-200 font-semibold bg-blue-800 hover:bg-blue-900 rounded-full'
                    onClick={handleClick}>
                {button}
            </button>
        </div>
    )
}

export default MissingContent