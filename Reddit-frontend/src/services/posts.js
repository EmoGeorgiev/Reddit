import axiosInstance from './axiosInstance'

const baseUrl = '/posts'

const getPost = async (id) => {
    const response = await axiosInstance.get(`${baseUrl}/${id}`)
    return response.data
}

const getPosts = async (page) => {
    const response = await axiosInstance.get(baseUrl, {
        params: {
            page
        }
    })
    return response.data
}

const getPostsWhereTitleContainsWord = async (title) => {
    const response = await axiosInstance.get(`${baseUrl}/search`, {
        params: { 
            title,
            page
        }
    })
    return response.data
}

const getPostsByUserSubscriptions = async (userId, page) => {
    const response = await axiosInstance.get(`${baseUrl}/subscriptions/${userId}`, {
        params: {
            page
        }
    })
    return response.data
}

const getPostsByUserId = async (userId, page) => {
    const response = await axiosInstance.get(`${baseUrl}/users/${userId}`, {
        params: {
            page
        }
    })
    return response.data
}

const getPostsBySubredditId = async (subredditId, page) => {
    const response = await axiosInstance.get(`${baseUrl}/subreddits/${subredditId}`, {
        params: {
            page
        }
    })
    return response.data
}

const addPost = async (post) => {
    const response = await axiosInstance.post(baseUrl, post)
    return response.data
}

const updatePost = async (id, post) => {
    const response = await axiosInstance.put(`${baseUrl}/${id}`, post)
    return response.data
}

const deletePost = async (postId, userId) => {
    const response = await axiosInstance.delete(`${baseUrl}/${postId}`, {
        params: { userId }
    })
    return response.data
}

export default { getPost, getPosts, getPostsWhereTitleContainsWord, getPostsByUserSubscriptions, getPostsByUserId, getPostsBySubredditId, addPost, updatePost, deletePost }