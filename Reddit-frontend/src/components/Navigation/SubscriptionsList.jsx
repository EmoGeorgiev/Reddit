import { useState } from 'react'
import { Link } from 'react-router-dom'
import arrowIcon from '../../assets/expand-arrow-icon.svg'
import subredditIcon from '../../assets/subreddit-icon.svg'
 
const subreddits = [
    { 'id' : 1, 'name' : 'AskMen'},
    { 'id' : 2, 'name' : 'AskReddit'},
    { 'id' : 3, 'name' : 'Barca'},
    { 'id' : 4, 'name' : 'Bulgaria'},
    { 'id' : 5, 'name' : 'Linux'},
    { 'id' : 6, 'name' : 'Nba'},
    { 'id' : 7, 'name' : 'Soccer'},
    { 'id' : 8, 'name' : 'StarWars'},
    { 'id' : 9, 'name' : 'AskMen'},
    { 'id' : 10, 'name' : 'AskReddit'},
    { 'id' : 11, 'name' : 'Barca'},
    { 'id' : 12, 'name' : 'Bulgaria'},
    { 'id' : 13, 'name' : 'Linux'},
    { 'id' : 14, 'name' : 'Nba'},
    { 'id' : 15, 'name' : 'Soccer'},
    { 'id' : 16, 'name' : 'StarWars'}
]

const SubscriptionsList = ({ isOpen, handleOpen }) => {
    const [subscriptions, setSubscriptions] = useState(subreddits)
    
    return (
        <div className='w-56 ml-4'>
            <div className='px-4 py-3 flex justify-between items-center font-semibold hover:bg-gray-200 rounded-2xl cursor-pointer' onClick={handleOpen}>
                <div>
                    Subscriptions
                </div>
                <img className={`expand-arrow ${isOpen ? '-rotate-90' : 'rotate-90'}`} src={arrowIcon} alt='arrow' />
            </div>

            <div className={`${isOpen ? 'opacity-100 visible' : 'opacity-0 invisible'} duration-400`}>
                <ul>
                    {subscriptions.map(subreddit => 
                            <li key={subreddit.id}>
                                <Link to={`/r/${subreddit.name}`} className='w-56 px-4 py-2 flex items-center font-light hover:bg-gray-200 rounded-2xl overflow-clip'>
                                    <img className='w-8 h-8' src={subredditIcon} alt='subreddit' />
                                    <div className='mx-2'>
                                        r/{subreddit.name}
                                    </div>
                                </Link>
                            </li>)}
                </ul>
            </div>
        </div>
    )
}

export default SubscriptionsList
