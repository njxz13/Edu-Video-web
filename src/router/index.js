import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import HomeView from '../views/HomeView.vue'

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: LoginView },
  { path: '/register', component: RegisterView },
  { path: '/home', component: HomeView },
  { path: '/videos', component: () => import('../views/VideoListView.vue') },
  { path: '/course/:id', component: () => import('../views/CourseDetail.vue') },
  { path: '/video/:videoId', component: () => import('../views/VideoDetail.vue') },
  { path: '/upload', component: () => import('../views/UploadView.vue') },
  { path: '/profile', component: () => import('../views/ProfileView.vue') },
  { path: '/profile/edit', component: () => import('../views/EditProfileView.vue') },
  { path: '/admin/login', component: () => import('../views/AdminLoginView.vue') },
  { path: '/admin/dashboard', component: () => import('../views/AdminDashboard.vue') },
  { path: '/admin/users', component: () => import('../views/AdminUsers.vue') },
  { path: '/admin/videos', component: () => import('../views/AdminVideos.vue') }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
