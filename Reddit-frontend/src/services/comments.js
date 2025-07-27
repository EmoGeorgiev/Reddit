import axiosInstance from "./axiosInstance";

const baseUrl = "/comments";

const getComment = async (id) => {
  const response = await axiosInstance.get(`${baseUrl}/${id}`);
  return response.data;
};

const getCommentsByPostId = async (postId, pageable) => {
  const response = await axiosInstance.get(`${baseUrl}/posts/${postId}`, {
    params: {
      ...pageable,
    },
  });
  return response.data;
};

const getCommentsByUserId = async (userId, pageable) => {
  const response = await axiosInstance.get(`${baseUrl}/users/${userId}`, {
    params: {
      ...pageable,
    },
  });
  return response.data;
};

const addComment = async (comment) => {
  const response = await axiosInstance.post(baseUrl, comment);
  return response.data;
};

const updateComment = async (id, comment) => {
  const response = await axiosInstance.put(`${baseUrl}/${id}`, comment);
  return response.data;
};

const deleteComment = async (commentId, userId) => {
  const response = await axiosInstance.delete(`${baseUrl}/${commentId}`, {
    params: { userId },
  });
  return response.data;
};

export default {
  getComment,
  getCommentsByPostId,
  getCommentsByUserId,
  addComment,
  updateComment,
  deleteComment,
};
