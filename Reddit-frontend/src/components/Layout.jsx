import { useState } from 'react'

import Navbar from './Navigation/Navbar'
import Sidebar from './Navigation/Sidebar'
import LoginForm from './Authentication/LoginForm'
import SignUpForm from './Authentication/SignUpForm'

const Layout = () => {
    const [collapsed, setCollapsed] = useState(false)

    return (
        <div className='h-screen'>
            <Navbar handleCollapse={() => setCollapsed(!collapsed)} />
            
            <div className='pt-14 h-screen flex'>
                <Sidebar collapsed={collapsed} />
                
                <div className='w-full overflow-auto'>
                    <LoginForm />
                    <SignUpForm />
                </div>
            </div>
        </div>
    )
}

export default Layout