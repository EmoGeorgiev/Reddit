import axiosInstance from "./axiosInstance";

const baseUrl = "/users";

const getUserById = async (id) => {
  const response = await axiosInstance.get(`${baseUrl}/id/${id}`);
  return response.data;
};

const getUserByUsername = async (username) => {
  const response = await axiosInstance.get(`${baseUrl}/username/${username}`);
  return response.data;
};

const getUsers = async (pageable) => {
  const response = await axiosInstance.get(baseUrl, {
    params: {
      ...pageable,
    },
  });
  return response.data;
};

const getUsersBySubredditTitle = async (title) => {
  const response = await axiosInstance.get(`${baseUrl}/subreddits/${title}`);
  return response.data;
};

const getModeratorsBySubredditTitle = async (title) => {
  const response = await axiosInstance.get(
    `${baseUrl}/subreddits/${title}/moderators`
  );
  return response.data;
};

const getUsersWhereUsernameContainsWord = async (word, pageable) => {
  const response = await axiosInstance.get(`${baseUrl}/search`, {
    params: {
      word,
      ...pageable,
    },
  });
  return response.data;
};

const updateUsername = async (id, user) => {
  const response = await axiosInstance.put(`${baseUrl}/${id}/username`, user);
  return response.data;
};

const updatePassword = async (id, updatePassword) => {
  const response = await axiosInstance.put(
    `${baseUrl}/${id}/password`,
    updatePassword
  );
  return response.data;
};

const addSubredditModerator = async (subredditTitle, moderatorUpdate) => {
  const response = await axiosInstance.put(
    `${baseUrl}/${subredditTitle}/moderators/add`,
    moderatorUpdate
  );
  return response.data;
};

const removeSubredditModerator = async (subredditTitle, moderatorUpdate) => {
  const response = await axiosInstance.put(
    `${baseUrl}/${subredditTitle}/moderators/remove`,
    moderatorUpdate
  );
  return response.data;
};

const deleteUser = async (id, password) => {
  const response = await axiosInstance.delete(`${baseUrl}/${id}`, {
    params: { password },
  });
  return response.data;
};

export default {
  getUserById,
  getUserByUsername,
  getUsers,
  getUsersBySubredditTitle,
  getModeratorsBySubredditTitle,
  getUsersWhereUsernameContainsWord,
  updateUsername,
  updatePassword,
  addSubredditModerator,
  removeSubredditModerator,
  deleteUser,
};
