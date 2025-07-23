import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import FormHeader from '../Common/FormHeader'
import authenticationService from '../../services/authentication'
import { useFormErrors } from '../../hooks/useFormErrors'
import FormErrorMessage from '../Common/FormErrorMessage'

const SignUpForm = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const { errors, setBackendErrors } = useFormErrors()
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
            setBackendErrors(error)
        }   
    }

    const handleClose = () => {
        navigate('/')
    }

    return (
        <div>
            <button className='background-btn background-blur' onClick={handleClose}></button>
            
            <div className='active-form h-3/5'>
                <FormHeader name='' handleClose={handleClose} />

                <div className='flex flex-col items-center'>
                    <h1 className='m-6 text-3xl font-bold'>
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

                            <FormErrorMessage>
                                {errors.username}
                            </FormErrorMessage>
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

                            <FormErrorMessage>
                                {errors.password}
                                {errors.message}
                            </FormErrorMessage>
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
        </div>
    )

} 

export default SignUpForm