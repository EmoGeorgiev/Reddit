import { useState } from 'react'

import Navbar from './Navbar'
import Sidebar from './SideBar'
import LoginForm from './LoginForm'
import SignUpForm from './SignUpForm'

const Layout = () => {
    const [collapsed, setCollapsed] = useState(false)

    return (
        <div className='h-screen'>
            <Navbar handleCollapse={() => setCollapsed(!collapsed)} />
            
            <div className='pt-16 h-screen flex'>
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