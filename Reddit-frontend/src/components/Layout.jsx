import { Route, Routes } from 'react-router-dom'
import { useState } from 'react'
import ProtectedRoute from './Authentication/ProtectedRoute'
import Navbar from './Navigation/Navbar'
import Sidebar from './Navigation/Sidebar'
import LoginForm from './AuthenticationForms/LoginForm'
import SignUpForm from './AuthenticationForms/SignUpForm'
import Settings from './Settings/Settings'
import Subreddit from './Subreddit/Subreddit'

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
                        <Route path='/r/:title' element={<Subreddit />} />
                        <Route path='/settings' element={<ProtectedRoute><Settings /></ProtectedRoute>} />
                    </Routes>
                </div>
            </div>
        </div>
    )
}

export default Layout