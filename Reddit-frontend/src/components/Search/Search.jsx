import { useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { Category } from '../../util/Category'
import CategoryList from '../Category/CategoryList'
import MissingContent from '../Common/MissingContent'
import PostList from '../Post/PostList'
import postService from '../../services/posts'
import subredditService from '../../services/subreddits'
import userService from '../../services/users'

const Search = () => {
    const [isEmpty, setIsEmpty] = useState(true)
    const { query } = useParams()
    const navigate = useNavigate()

    const getPostsWhereTitleContainsWord = async (word, pageable) => {
        const postPage = await postService.getPostsWhereTitleContainsWord(word, pageable)
        
        setIsEmpty(postPage.empty)
        
        return postPage
    }

    const getSubredditsWhereTitleContainsWord = async (word, pageable) => {
        const subredditPage = await subredditService.getSubredditsWhereTitleContainsWord(word, pageable)
        
        setIsEmpty(subredditPage.empty)
        
        return subredditPage
    }

    const getUsersWhereUsernameContainsWord = async (word, pageable) => {
        const userPage = await userService.getUsersWhereUsernameContainsWord(word, pageable)
        
        setIsEmpty(userPage.empty)
        
        return userPage
    }

    const categories = [Category.POSTS, Category.SUBREDDITS, Category.USERS]

    const categoryComponents = {
        [Category.POSTS]: <PostList query={query} getPosts={getPostsWhereTitleContainsWord} />,
        [Category.SUBREDDITS]: <div>subreddits {query}</div>,
        [Category.USERS]: <div>users {query}</div>
    }
    
    return (
        <div className='mt-10'>
            <CategoryList defaultCategory={Category.POSTS}
                        categories={categories} 
                        categoryComponents={categoryComponents} />

            {isEmpty && <MissingContent heading={`Hm...we couldn’t find any results for "${query}"`}
                                        text='Double-check your spelling or try different keywords'
                                        button='Browse other subreddits'
                                        handleClick={() => navigate('/')} />}
        </div>
    )
}

export default Search