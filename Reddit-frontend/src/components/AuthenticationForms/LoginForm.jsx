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
        <div className='mt-16 flex flex-col items-center'>
            <h1 className='m-10 text-3xl font-bold'>
                Log In
            </h1>

            <form onSubmit={handleLogin}>
                <div className='m-6'>
                    <input 
                        className='auth-input focus-item'
                        type='text'
                        value={username}
                        name='username'
                        placeholder='Username'
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div className='m-6'>
                    <input 
                        className='auth-input focus-item'
                        type='password'
                        value={password}
                        name='password'
                        placeholder='Password'
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <div className='m-6'>
                    <button className='auth-btn focus-item' type='submit'>
                        Log in
                    </button>
                </div>
            </form>
            
            <p className='m-3 font-medium'>
                New to Reddit? <Link to='/signup' className='text-blue-400 focus-item'>Sign Up</Link>
            </p>
        </div>
    )

} 

export default LoginForm