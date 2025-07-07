import voteIcon from '../../assets/vote-icon.svg'

const Vote = ({ score }) => {
    return (
        <div className='p-1 flex justify-center space-x-1.5 bg-gray-200 rounded-full'>
            <img className='w-6 h-6 hover:bg-red-600 hover:rounded-full cursor-pointer' src={voteIcon} alt='vote' />
            <span className='font-semibold'>{score}</span>
            <img className='w-6 h-6 hover:bg-blue-600 hover:rounded-full cursor-pointer rotate-180' src={voteIcon} alt='vote' />
        </div>
    )
}

export default Vote