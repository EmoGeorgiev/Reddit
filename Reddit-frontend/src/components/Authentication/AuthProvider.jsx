import { useState, useEffect } from 'react'
import { AuthContext } from './AuthContext'

const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null)
    const [isAuthenticated, setIsAuthenticated] = useState(false)
    const [isLoading, setIsLoading] = useState(true)

    useEffect(() => {
        loadUser()
    }, [])

    const login = (data) => {
        const localStorageUser = {
            'username': data.username,
            'id': data.id 
        }


        window.localStorage.setItem('loggedUser', JSON.stringify(localStorageUser))
        window.localStorage.setItem('token', data.token)

        setUser(localStorageUser)
        setIsAuthenticated(true)
    }

    const logout = () => {
        localStorage.removeItem('loggedUser')
        localStorage.removeItem('token')

        setUser(null)
        setIsAuthenticated(false)
    }

    const loadUser = () => {
        const localStorageUser = JSON.parse(localStorage.getItem('loggedUser'))

        if (localStorageUser !== null) {
            setUser(localStorageUser)
            setIsAuthenticated(true)
        }
        setIsLoading(false)
    }
    
    return (
        <AuthContext.Provider value={{ login, logout, user, isAuthenticated, isLoading }}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthProvider