import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { useAuth } from '../../hooks/useAuth'
import subredditService from '../../services/subreddits'
import arrowIcon from '../../assets/expand-arrow-icon.svg'
import subredditIcon from '../../assets/subreddit-icon.svg'
import CreateSubredditForm from '../Subreddit/CreateSubredditForm'

const SubscriptionsList = ({ isOpen, handleOpen }) => {
    const [subscriptions, setSubscriptions] = useState([])
    const [isFormOpen, setIsFormOpen] = useState(false)
    const { user } = useAuth()

    useEffect(() => {
        const getSubscriptions = async () => {
            try {
                const subreddits = await subredditService.getSubredditsByUserId(user.id)

                setSubscriptions(subreddits)
            } catch (error) {
                console.log(error)
            }
        }

        getSubscriptions()
    }, [])

    const addSubreddit = async (subreddit) => {
        try {
            const newSubreddit = await subredditService.addSubreddit(subreddit, user.id)
            
            const sortedSubreddits = [...subscriptions, newSubreddit].sort((a, b) => a.title.localeCompare(b.title))
            setSubscriptions(sortedSubreddits)

            closeForm()
        } catch (error) {
            throw error
        }
    }

    const closeForm = () => {
        setIsFormOpen(false)
    }
    
    return (
        <div className='w-56 mt-4 ml-4 border-t border-gray-300'>
            <div className='mt-4 px-4 py-3 flex justify-between items-center font-semibold hover:bg-gray-200 rounded-2xl cursor-pointer' onClick={handleOpen}>
                <h1>
                    Subscriptions
                </h1>
                
                <img className={`expand-arrow ${isOpen ? '-rotate-90' : 'rotate-90'}`} src={arrowIcon} alt='arrow' />
            </div>

            <div>
                {isFormOpen && <button className='background-btn background-blur' onClick={closeForm}></button>}

                <div className={`${isFormOpen ? 'opacity-100 visible' : 'opacity-0 invisible'} duration-300`}>
                    <CreateSubredditForm addSubreddit={addSubreddit} handleClose={closeForm} />
                </div>
            </div>

            <div className={`${isOpen ? 'opacity-100 visible' : 'opacity-0 invisible'} duration-400`}>
                <button className='w-56 px-4 py-2 group flex items-center space-x-2 hover:bg-gray-200 rounded-2xl'
                        onClick={() => setIsFormOpen(true)}>
                    <svg className='w-8 h-8' viewBox='0 0 24 24' fill='none' xmlns='http://www.w3.org/2000/svg'>
                        <path d='M12 6V18' stroke='currentColor'/>
                        <path d='M6 12H18' stroke='currentColor'/>
                    </svg>
                    <span className='font-light'>Create a subreddit</span>
                </button>

                <ul>
                    {subscriptions.map(subreddit => 
                            <li key={subreddit.id}>
                                <Link to={`/r/${subreddit.title}`} className='w-56 px-4 py-2 flex items-center space-x-2 hover:bg-gray-100 rounded-2xl overflow-clip'>
                                    <img className='w-8 h-8' src={subredditIcon} alt='subreddit' />
                                    <h2 className='font-light'>
                                        r/{subreddit.title}
                                    </h2>
                                </Link>
                            </li>)}
                </ul>
            </div>
        </div>
    )
}

export default SubscriptionsList
