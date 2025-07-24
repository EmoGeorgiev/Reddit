import EmptyContent from './EmptyContent'

const MissingContent = ({ heading, text, button, handleClick }) => {
    return (
        <div className='h-full flex flex-col justify-center items-center space-y-3'>
            <EmptyContent text={heading} />

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