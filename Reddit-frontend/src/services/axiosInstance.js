import axios from 'axios'

const baseUrl = 'http://localhost:8080/api'

const axiosInstance = axios.create({
    baseURL: baseUrl
})

axiosInstance.interceptors.request.use((request) => {
    const token = localStorage.getItem('token')
    if (token && request.method && request.method.toUpperCase() !== 'GET') {
        request.headers['Authorization'] = `Bearer ${token}`
    }
    return request
}, (error) => {
    return Promise.reject(error)
})

axiosInstance.interceptors.response.use((response) => {
    return response
}, (error) => {
    if (error.response && error.response.status === 401) {
        localStorage.removeItem('token')
        localStorage.removeItem('loggedUser')
        window.location.reload()
    }
    return Promise.reject(error)
})

export default axiosInstance