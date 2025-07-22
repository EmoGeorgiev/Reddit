import { Route, Routes } from 'react-router-dom'
import { useState } from 'react'
import ProtectedRoute from './Authentication/ProtectedRoute'
import Navbar from './Navigation/Navbar'
import Sidebar from './Navigation/Sidebar'
import LoginForm from './AuthenticationForms/LoginForm'
import SignUpForm from './AuthenticationForms/SignUpForm'
import Settings from './Settings/Settings'
import Subreddit from './Subreddit/Subreddit'
import AllFeed from './Feed/AllFeed'
import HomeFeed from './Feed/HomeFeed'
import User from './User/User'
import Search from './Search/Search'
import CreatePostForm from './Post/CreatePostForm'
import CommentSection from './Comment/CommentSection'
import ModeratorPage from './Moderator/ModeratorPage'

const Layout = () => {
    const [collapsed, setCollapsed] = useState(false)

    return (
        <div className='h-screen'>
            <Navbar handleCollapse={() => setCollapsed(!collapsed)} />
            
            <div className='pt-14 h-screen flex'>
                <Sidebar collapsed={collapsed} />
                
                <div className='flex-1 overflow-auto'>
                    <Routes>
                        <Route path='/login' element={<LoginForm />} />
                        <Route path='/signup' element={<SignUpForm />} />
                        <Route path='/' element={<AllFeed />} />
                        <Route path='/home' element={<ProtectedRoute><HomeFeed /></ProtectedRoute>} />
                        <Route path='/search/:query' element={<Search />} />
                        <Route path='/r/:name' element={<Subreddit />} />
                        <Route path='/r/:name/submit' element={<CreatePostForm />} />
                        <Route path='/r/:name/comments/:postId' element={<CommentSection />} />
                        <Route path='/r/:name/moderators' element={<ProtectedRoute><ModeratorPage /></ProtectedRoute>} />
                        <Route path='/users/:username' element={<User />} />
                        <Route path='/settings' element={<ProtectedRoute><Settings /></ProtectedRoute>} />
                    </Routes>
                </div>
            </div>
        </div>
    )
}

export default Layout