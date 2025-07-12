import subredditIcon from '../../assets/subreddit-icon.svg'

const ContentSubredditHeader = ({ subreddit }) => {
    return (
        <div className='flex space-x-1.5 items-center'>
            <img className='w-10 h-10' src={subredditIcon} alt='subreddit' />
            <span className='font-semibold'>r/{subreddit.title}</span>
        </div>
    )
}

export default ContentSubredditHeader