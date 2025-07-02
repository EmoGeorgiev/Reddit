import axiosInstance from './axiosInstance'

const baseUrl = '/votes'

const toggleVote = async (vote) => {
    const response = await axiosInstance.post(baseUrl, vote)
    return response.data
}

export default { toggleVote }