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

const getSubredditsWhereTitleContainsWord = async (word, pageable) => {
    const response = await axiosInstance.get(`${baseUrl}/search`, {
        params: {
            word,
            ...pageable
        }
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

const addSubreddit = async (subreddit, creatorId) => {
    const response = await axiosInstance.post(baseUrl, subreddit, {
        params: { creatorId }
    })
    return response.data
}

const updateSubredditTitle = async (id, subredditUpdateTitle) => {
    const response = await axiosInstance.put(`${baseUrl}/${id}/title`, subredditUpdateTitle)
    return response.data
}

const addSubredditToUserSubscriptions = async (title, userId) => {
    const response = await axiosInstance.put(`${baseUrl}/${title}/users/add`, null, {
        params: {
            userId
        }
    })
    return response.data    
}

const removeSubredditFromUserSubscriptions = async (title, userId) => {
    const response = await axiosInstance.put(`${baseUrl}/${title}/users/remove`, null, {
        params: {
            userId
        }
    })
    return response.data
}

const deleteSubreddit = async (subredditTitle, moderatorId) => {
    const response = await axiosInstance.delete(`${baseUrl}/${subredditTitle}`, {
        params: { moderatorId }
    })
    return response.data
}


export default { getSubredditById, getSubredditByTitle, getSubredditsWhereTitleContainsWord, getSubredditsByUserId, getSubredditsByModeratorId, addSubreddit, updateSubredditTitle, addSubredditToUserSubscriptions, removeSubredditFromUserSubscriptions, deleteSubreddit }