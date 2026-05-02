import axios from 'axios'

const request = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000,
  withCredentials: true
})

export const getCourses = () => request.get('/courses')
export const getCourseDetail = (id) => request.get(`/courses/${id}`)
export const getVideoPlay = (courseId, videoId) => request.get(`/courses/${courseId}/video/${videoId}`)
