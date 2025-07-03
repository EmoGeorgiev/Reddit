import { useParams } from 'react-router-dom'

const Subreddit = () => {
    const { title } = useParams()
    
    return (
        <div>
            Subreddit, {title}
        </div>
    )
}

export default Subreddit