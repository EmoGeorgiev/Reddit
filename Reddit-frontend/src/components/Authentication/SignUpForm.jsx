import { useState } from 'react'
import { Link } from 'react-router-dom'

const SignUpForm = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    const handleSignUp = (e) => {
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
                    <button className='auth-btn focus-item'>
                        Sign Up
                    </button>
                </div>
            </form>
            
            <p className='m-3 font-medium'>
                Already a redditor? <Link to='/' className='text-blue-400 focus-item'>Log In</Link>
            </p>
        </div>
    )

} 

export default SignUpForm