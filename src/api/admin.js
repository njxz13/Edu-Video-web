import request from './index'

export const adminLogin = (data) => request.post('/admin/login', new URLSearchParams(data))
export const getDashboard = () => request.get('/admin/dashboard')
export const getUsers = () => request.get('/admin/users')
export const deleteUser = (userId) => request.delete(`/admin/user/${userId}`)
export const getVideos = () => request.get('/admin/videos')
export const updateVideoStatus = (videoId, status) => 
  request.put(`/admin/video/${videoId}/status`, new URLSearchParams({ status }))
export const deleteVideo = (videoId) => request.delete(`/admin/video/${videoId}`)
export const adminLogout = () => request.post('/admin/logout')
