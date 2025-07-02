import axiosInstance from './axiosInstance'

const baseUrl = '/subreddits'

const getSubredditById = async (id) => {
    const response = await axiosInstance.get(`${baseUrl}/id/${id}`)
    return response.data
}

const getSubredditByTitle = async (title) => {
    const response = await axiosInstance.get(`${baseUrl}/title/${title}`)
    return response.data
}

const getSubredditsWhereTitleContainsWord = async (word) => {
    const response = await axiosInstance.get(`${baseUrl}/search`, {
        params: { word }
    })
    return response.data
}

const getSubredditsByUserId = async (userId) => {
    const response = await axiosInstance.get(`${baseUrl}/users/${userId}`)
    return response.data
}

const getSubredditsByModeratorId = async (moderatorId) => {
    const response = await axiosInstance.get(`${baseUrl}/moderators/${moderatorId}`)
    return response.data
}

const addSubreddit = async (subreddit) => {
    const response = await axiosInstance.post(baseUrl, subreddit)
    return response.data
}

const updateSubredditTitle = async (id, subredditUpdateTitle) => {
    const response = await axiosInstance.put(`${baseUrl}/${id}/title`, subredditUpdateTitle)
    return response.data
}

const addSubredditModerator = async (id, moderatorUpdate) => {
    const response = await axiosInstance.put(`${baseUrl}/${id}/moderators/add`, moderatorUpdate)
    return response.data
}

const removeSubredditModerator = async (id, moderatorUpdate) => {
    const response = await axiosInstance.put(`${baseUrl}/${id}/moderators/remove`, moderatorUpdate)
    return response.data
}

const deleteSubreddit = async (subredditId, moderatorId) => {
    const response = await axiosInstance.delete(`${baseUrl}/${subredditId}`, {
        params: { moderatorId }
    })
    return response.data
}


export default { getSubredditById, getSubredditByTitle, getSubredditsWhereTitleContainsWord, getSubredditsByUserId, getSubredditsByModeratorId, addSubreddit, updateSubredditTitle, addSubredditModerator, removeSubredditModerator, deleteSubreddit }