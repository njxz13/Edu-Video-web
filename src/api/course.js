import request from './index'

export const getCourses = () => request.get('/courses')
export const getCourseDetail = (id) => request.get(`/courses/${id}`)
export const getVideoPlay = (courseId, videoId) => request.get(`/courses/${courseId}/video/${videoId}`)
