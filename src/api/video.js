import axios from 'axios'

const request = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000,
  withCredentials: true
})

export const getVideos = (params) => request.get('/videos', { params })
export const getVideoDetail = (videoId) => request.get(`/videos/${videoId}`)
export const uploadVideo = (formData) => request.post('/videos/upload', formData, {
  headers: { 'Content-Type': 'multipart/form-data' }
})
