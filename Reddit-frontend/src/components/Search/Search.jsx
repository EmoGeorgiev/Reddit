import { useParams } from 'react-router-dom'
import CategoryList from '../Category/CategoryList'
import { Category } from '../../util/Category'

const Search = () => {
    const { query } = useParams()

    const categories = [Category.POSTS, Category.SUBREDDITS, Category.USERS]

    const categoryComponents = {
        [Category.POSTS]: <div>posts {query}</div>,
        [Category.SUBREDDITS]: <div>subreddits {query}</div>,
        [Category.USERS]: <div>users {query}</div>
    }
    
    return (
        <div className='mt-10'>
            <CategoryList defaultCategory={Category.POSTS}
                        categories={categories} 
                        categoryComponents={categoryComponents} />
        </div>
    )
}

export default Search