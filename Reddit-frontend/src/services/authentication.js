import axios from 'axios'

const baseUrl = 'http://localhost:8080/api'

const login = async (credentials) => {
    const headers = {
        headers: {
            'Authorization': 'Basic ' + btoa(`${credentials.username}:${credentials.password}`)
        }        
    }

    const response = await axios.post(baseUrl + '/login', null, headers)
    return response.data
}

const signup = async (credentials) => {
    const response = await axios.post(baseUrl + '/signup', credentials)
    return response.data
}

export default { login, signup }