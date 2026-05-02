import axios from 'axios'

const request = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000,
  withCredentials: true
})

export const login = (data) => request.post('/auth/login', null, { params: data })
export const logout = () => request.post('/auth/logout')
export const register = (data) => request.post('/auth/register', null, { params: data })
export const getProfile = () => request.get('/auth/profile')
export const updateProfile = (data) => request.put('/auth/profile', null, { params: data })
