import axiosInstance from './axiosInstance'

const baseUrl = '/votes'

const getVoteByContentAndUser = async (contentId, userId) => {
    const response = await axiosInstance.get(`${baseUrl}`, {
        params: {
            contentId,
            userId
        }
    })
    response.data
}

const getUpVotedByUserId = async (userId, pageable) => {
    const response = await axiosInstance.get(`${baseUrl}/up-voted/users/${userId}`, {
        params: {
            ...pageable
        }
    })
    return response.data
}

const getDownVotedByUserId = async (userId, pageable) => {
    const response = await axiosInstance.get(`${baseUrl}/down-voted/users/${userId}`, {
        params: {
            ...pageable
        }
    })
    return response.data
}

const toggleVote = async (vote) => {
    const response = await axiosInstance.post(baseUrl, vote)
    return response.data
}

export default { getVoteByContentAndUser, getUpVotedByUserId, getDownVotedByUserId, toggleVote }