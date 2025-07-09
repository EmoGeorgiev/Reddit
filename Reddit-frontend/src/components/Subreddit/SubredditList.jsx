import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import Pagination from '../Common/Pagination'
import subredditIcon from '../../assets/subreddit-icon.svg'

const SubredditList = ({ query, getSubreddits }) => {
    const [subreddits, setSubreddits] = useState([])
    const [page, setPage] = useState(0)
    const [isFirst, setIsFirst] = useState(true)
    const [isLast, setIsLast] = useState(true)

    useEffect(() => {
        const getSubredditPage = async () => {
            try {
                const newSubreddits = await getSubreddits(query, { page })
                
                setSubreddits(newSubreddits.content)
                setIsFirst(newSubreddits.first)
                setIsLast(newSubreddits.last)
            } catch (error) {
                console.log(error)
            }
        }

        getSubredditPage()
    }, [query, page])

    const handlePageChange = (change) => {
        setPage(page + change)
    }

    return (
        <div className='my-4'>
            <ul>
                {subreddits.map(subreddit => <li className='border-b border-gray-300' key={subreddit.id}>
                    <Link to={`/r/${subreddit.title}`}>
                        <div className='p-4 hover:bg-gray-100 rounded-2xl overflow-clip'>
                            <div className='flex items-center space-x-2'>
                                <img className='w-12 h-12' src={subredditIcon} alt='subreddit' />
                                <div>
                                    <h2 className='font-semibold'>
                                        r/{subreddit.title}
                                    </h2>
                                    <div className='font-light'>
                                        {subreddit.userCount} {subreddit.userCount === 1 ? 'member' : 'members'}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </Link>
                </li>)}
            </ul>

            <Pagination handlePageChange={handlePageChange} isFirst={isFirst} isLast={isLast} />
        </div>
    )
}

export default SubredditList