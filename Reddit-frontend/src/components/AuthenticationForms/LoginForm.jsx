import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../Authentication/AuthContext'
import authenticationService from '../../services/authentication'

const LoginForm = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const navigate = useNavigate()
    const { login } = useAuth()

    const handleLogin = async (e) => {
        e.preventDefault()

        const credentials = { username, password }

        try {
            const data = await authenticationService.login(credentials)

            login(data)

            setUsername('')
            setPassword('')

            navigate('/')
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <div>
            <button className='background-btn background-blur' onClick={() => navigate('/')}></button>
            
            <div className='active-form h-3/5 flex flex-col items-center'>
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
        </div>
    )

} 

export default LoginForm