import axiosInstance from './axiosInstance'

const baseUrl = '/saved'

const getSavedContentByUserId = async (userId) => {
    const response = await axiosInstance.get(`${baseUrl}/${userId}`)
    return response.data
}

const toggleSavedContent = async (savedContent) => {
    const response = await axiosInstance.post(baseUrl, savedContent)
    return response.data
}

export default { getSavedContentByUserId, toggleSavedContent }