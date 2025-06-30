import { useState } from 'react'
import { Link } from 'react-router-dom'

const LoginForm = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    const handleLogin = (e) => {
        e.preventDefault()

        try {
            const credentials = { username, password }


            setUsername('')
            setPassword('')
        } catch (error) {
            
        }
    }

    return (
        <div>
            <h1 className='m-10 text-center text-3xl font-bold'>
                Log In
            </h1>

            <div className='flex flex-col items-center'>
                <form onSubmit={handleLogin}>
                    <div className='m-6'>
                        <input 
                            className='w-80 p-3 font-medium bg-gray-200 hover:bg-gray-300 focus:outline-none focus:border-2 focus:border-blue-400 rounded-2xl'
                            type='text'
                            value={username}
                            name='username'
                            placeholder='Username'
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </div>
                    <div className='m-6'>
                        <input 
                            className='w-80 p-3 font-medium bg-gray-200 hover:bg-gray-300 focus:outline-none focus:border-2 focus:border-blue-400 rounded-2xl'
                            type='password'
                            value={password}
                            name='password'
                            placeholder='Password'
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>

                   <div className='m-6'>
                        <button className='w-80 p-2.5 text-gray-100 font-medium bg-orange-600 hover:bg-orange-700 focus:outline-none focus:border-2 focus:border-blue-400 rounded-3xl'>
                            Log in
                        </button>
                   </div>
                </form>
                
                <p className='m-3 font-medium'>
                    New to Reddit? <Link to='' className='underline'>Sign Up</Link>
                </p>
            </div>

        </div>
    )

} 

export default LoginForm