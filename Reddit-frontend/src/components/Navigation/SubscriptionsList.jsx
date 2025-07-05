import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { useAuth } from '../Authentication/AuthContext'
import subredditService from '../../services/subreddits'
import arrowIcon from '../../assets/expand-arrow-icon.svg'
import subredditIcon from '../../assets/subreddit-icon.svg'

const SubscriptionsList = ({ isOpen, handleOpen }) => {
    const [subscriptions, setSubscriptions] = useState([])
    const { user } = useAuth()

    useEffect(() => {
        getSubscriptions()
    }, [])

    const getSubscriptions = async () => {
        try {
            const subreddits = await subredditService.getSubredditsByUserId(user.id)

            setSubscriptions(subreddits)
        } catch (error) {
            console.log(error)
        }
    }
    
    return (
        <div className='w-56 mt-4 ml-4 border-t border-gray-300'>
            <div className='mt-4 px-4 py-3 flex justify-between items-center font-semibold hover:bg-gray-200 rounded-2xl cursor-pointer' onClick={handleOpen}>
                <h1>
                    Subscriptions
                </h1>
                <img className={`expand-arrow ${isOpen ? '-rotate-90' : 'rotate-90'}`} src={arrowIcon} alt='arrow' />
            </div>

            <div className={`${isOpen ? 'opacity-100 visible' : 'opacity-0 invisible'} duration-400`}>
                <ul>
                    {subscriptions.map(subreddit => 
                            <li key={subreddit.id}>
                                <Link to={`/r/${subreddit.title}`} className='w-56 px-4 py-2 flex items-center font-light hover:bg-gray-200 rounded-2xl overflow-clip'>
                                    <img className='w-8 h-8' src={subredditIcon} alt='subreddit' />
                                    <div className='mx-2'>
                                        r/{subreddit.title}
                                    </div>
                                </Link>
                            </li>
                    )}
                </ul>
            </div>
        </div>
    )
}

export default SubscriptionsList
