import axiosInstance from "./axiosInstance";

const baseUrl = "/saved";

const getSavedByContentAndUser = async (contentId, userId) => {
  const response = await axiosInstance.get(baseUrl, {
    params: {
      contentId,
      userId,
    },
  });
  return response.data;
};

const getSavedContentByUserId = async (userId, pageable) => {
  const response = await axiosInstance.get(`${baseUrl}/users/${userId}`, {
    params: {
      ...pageable,
    },
  });
  return response.data;
};

const toggleSavedContent = async (savedContent) => {
  const response = await axiosInstance.post(baseUrl, savedContent);
  return response.data;
};

export default {
  getSavedByContentAndUser,
  getSavedContentByUserId,
  toggleSavedContent,
};
