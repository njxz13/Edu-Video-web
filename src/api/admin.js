import axios from 'axios'

const request = axios.create({
  baseURL: 'http://localhost:8080/api/admin',
  timeout: 10000,
  withCredentials: true
})

export const adminLogin = (data) => request.post('/login', null, { params: data })
export const getDashboard = () => request.get('/dashboard')
export const getUsers = () => request.get('/users')
export const deleteUser = (userId) => request.delete(`/user/${userId}`)
export const getVideos = () => request.get('/videos')
export const updateVideoStatus = (videoId, status) => 
  request.put(`/video/${videoId}/status`, null, { params: { status } })
export const deleteVideo = (videoId) => request.delete(`/video/${videoId}`)
export const adminLogout = () => request.post('/logout')
