import request from './index'

export const getVideos = (params) => request.get('/videos', { params })
export const getVideoDetail = (videoId) => request.get(`/videos/${videoId}`)
export const uploadVideo = (formData) => request.post('/videos/upload', formData, {
  headers: { 'Content-Type': 'multipart/form-data' }
})
