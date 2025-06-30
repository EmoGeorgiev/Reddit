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
        <div>
            <h1>
                Sign Up
            </h1>

            <form onSubmit={handleSignUp}>
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
                    Sign Up
                </button>
            </form>

            <p>
                Already a redditor? <Link to='/'>Log In</Link>
            </p>
        </div>
    )

} 

export default SignUpForm