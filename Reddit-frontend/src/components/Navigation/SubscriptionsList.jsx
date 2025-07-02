import { useState } from 'react'

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
        <div>
            <div className='w-56 ml-4 px-4 py-3 font-semibold hover:bg-gray-200 rounded-2xl cursor-pointer' 
                 onClick={handleOpen}>
                 {isOpen ? 'Subscriptions <<' : 'Subscriptions >>'}
            </div>
            <div className={`${isOpen ? 'opacity-100 visible' : 'opacity-0 invisible'} duration-400`}>
                <div>
                    <ul>
                        {subscriptions.map(subreddit => 
                                <li className='w-48 ml-8 px-4 py-3 font-light hover:bg-gray-200 rounded-2xl overflow-clip' 
                                    key={subreddit.id}>
                                    <a href=''>r/{subreddit.name}</a>
                                </li>)}
                    </ul>
                </div>
            </div>
        </div>
    )
}

export default SubscriptionsList
