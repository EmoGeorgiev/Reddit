import { useState } from 'react'
import { useAuth } from './Authentication/AuthContext'

import Navbar from './Navigation/Navbar'
import Sidebar from './Navigation/Sidebar'
import LoginForm from './AuthenticationForms/LoginForm'
import SignUpForm from './AuthenticationForms/SignUpForm'
import { Route, Routes, useLocation } from 'react-router-dom'

const Layout = () => {
    const [collapsed, setCollapsed] = useState(false)
    const location = useLocation()
    //const { isAuthenticated } = useAuth()
    const isAuthenticated = true

    const hideLayout = location.pathname === '/login' || location.pathname === '/signup'

    return (
        <div className='h-screen'>
            {!hideLayout && <Navbar handleCollapse={() => setCollapsed(!collapsed)} isAuthenticated={isAuthenticated} />}
            
            <div className='pt-14 h-screen flex'>
                {!hideLayout && <Sidebar collapsed={collapsed} isAuthenticated={isAuthenticated} />}
                
                <div className='w-full overflow-auto'>
                    <Routes>
                        <Route path='/login' element={<LoginForm />} />
                        <Route path='/signup' element={<SignUpForm />} />
                    </Routes>
                </div>
            </div>
        </div>
    )
}

export default Layout