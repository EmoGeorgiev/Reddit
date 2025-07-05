import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import authenticationService from '../../services/authentication'

const SignUpForm = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const navigate = useNavigate()

    const handleSignUp = async (e) => {
        e.preventDefault()

        const credentials = { username, password }

        try {
            const user = await authenticationService.signup(credentials)

            if (user !== null) {
                setUsername('')
                setPassword('')
                
                navigate('/login')
            }
        } catch (error) {
            console.log(error)
        }   
    }

    return (
        <div>
            <button className='background-btn background-blur' onClick={() => navigate('/')}></button>
            
            <div className='active-form h-3/5 flex flex-col items-center'>
                <h1 className='m-10 text-3xl font-bold'>
                    Sign Up
                </h1>

                <form onSubmit={handleSignUp}>
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
                            Sign Up
                        </button>
                    </div>
                </form>
                
                <p className='m-3 font-medium'>
                    Already a redditor? <Link to='/login' className='text-blue-400 focus-item'>Log In</Link>
                </p>
            </div>
        </div>
    )

} 

export default SignUpForm