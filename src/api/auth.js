import request from './index'

export const login = (data) => request.post('/auth/login', new URLSearchParams(data))
export const logout = () => request.post('/auth/logout')
export const register = (data) => request.post('/auth/register', new URLSearchParams(data))
export const getProfile = () => request.get('/auth/profile')
export const updateProfile = (data) => request.put('/auth/profile', new URLSearchParams(data))
