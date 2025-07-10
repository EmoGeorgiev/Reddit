import { useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { Category } from '../../util/Category'
import CategoryList from '../Category/CategoryList'
import MissingContent from '../Common/MissingContent'
import PostPage from '../Post/PostPage'
import postService from '../../services/posts'
import subredditService from '../../services/subreddits'
import userService from '../../services/users'
import SubredditList from '../Subreddit/SubredditList'
import UserList from '../User/UserList'

const Search = () => {
    const [isEmpty, setIsEmpty] = useState(true)
    const { query } = useParams()
    const navigate = useNavigate()

    const getPostsWhereTitleContainsWord = async (word, pageable) => {
        try {
            const postPage = await postService.getPostsWhereTitleContainsWord(word, pageable)
            
            setIsEmpty(postPage.empty)
            
            return postPage
        } catch (error) {
            console.log(error)
        }
    }

    const getSubredditsWhereTitleContainsWord = async (word, pageable) => {
        try {
            const subredditPage = await subredditService.getSubredditsWhereTitleContainsWord(word, pageable)
            
            setIsEmpty(subredditPage.empty)
            
            return subredditPage
        } catch (error) {
            console.log(error)
        }
    }

    const getUsersWhereUsernameContainsWord = async (word, pageable) => {
        try {
            const userPage = await userService.getUsersWhereUsernameContainsWord(word, pageable)
            
            setIsEmpty(userPage.empty)
            
            return userPage
        } catch (error) {
            console.log(error)
        }
    }

    const categories = [Category.POSTS, Category.SUBREDDITS, Category.USERS]

    const categoryComponents = {
        [Category.POSTS]: <PostPage query={query} getPosts={getPostsWhereTitleContainsWord} />,
        [Category.SUBREDDITS]: <SubredditList query={query} getSubreddits={getSubredditsWhereTitleContainsWord} />,
        [Category.USERS]: <UserList query={query} getUsers={getUsersWhereUsernameContainsWord} />
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