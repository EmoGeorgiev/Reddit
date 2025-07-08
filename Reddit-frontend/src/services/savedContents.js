import axiosInstance from './axiosInstance'

const baseUrl = '/saved'

const getSavedContentByUserId = async (userId, pageable) => {
    const response = await axiosInstance.get(`${baseUrl}/${userId}`, {
        params: {
            ...pageable
        }
    })
    return response.data
}

const toggleSavedContent = async (savedContent) => {
    const response = await axiosInstance.post(baseUrl, savedContent)
    return response.data
}

export default { getSavedContentByUserId, toggleSavedContent }