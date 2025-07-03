import axiosInstance from './axiosInstance'

const baseUrl = '/votes'

const getUpVotedByUserId = async (userId) => {
    const response = await axiosInstance.get(`${baseUrl}/up-voted/users/${userId}`)
    return response.data
}

const getDownVotedByUserId = async (userId) => {
    const response = await axiosInstance.get(`${baseUrl}/down-voted/users/${userId}`)
    return response.data
}

const toggleVote = async (vote) => {
    const response = await axiosInstance.post(baseUrl, vote)
    return response.data
}

export default { getUpVotedByUserId, getDownVotedByUserId, toggleVote }