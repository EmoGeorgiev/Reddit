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
            <h1>
                Log In
            </h1>

            <form onSubmit={handleLogin}>
                <div>
                    <input 
                        type='text'
                        value={username}
                        name='username'
                        placeholder='Username'
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <input 
                        type='text'
                        value={password}
                        name='password'
                        placeholder='Password'
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>

                <button>
                    Log in
                </button>
            </form>

            <p>
                New to Reddit? <Link to=''>Sign Up</Link>
            </p>
        </div>
    )

} 

export default LoginForm