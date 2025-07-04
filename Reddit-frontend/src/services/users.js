import axiosInstance from './axiosInstance'

const baseUrl = '/users'

const getUserById = async (id) => {
    const response = await axiosInstance.get(`${baseUrl}/id/${id}`)
    return response.data
}

const getUserByUsername = async (username) => {
    const response = await axiosInstance.get(`${baseUrl}/username/${username}`)
    return response.data
}

const getUsers = async () => {
    const response = await axiosInstance.get(baseUrl)
    return response.data
}

const getUsersWhereUsernameContainsWord = async (word) => {
    const response = await axiosInstance.get(`${baseUrl}/search`, {
        params: { word }
    })
    return response.data
}

const updateUsername = async (id, user) => {
    const response = await axiosInstance.put(`${baseUrl}/${id}/username`, user)
    return response.data
}

const updatePassword = async (id, updatePassword) => {
    const response = await axiosInstance.put(`${baseUrl}/${id}/password`, updatePassword)
    return response.data
}

const deleteUser = async (id, password) => {
    const response = await axiosInstance.delete(`${baseUrl}/${id}`, {
        params: { password }
    })
    return response.data
}


export default { getUserById, getUserByUsername, getUsers, getUsersWhereUsernameContainsWord, updateUsername, updatePassword, deleteUser }