import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import HomeView from '../views/HomeView.vue'

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: LoginView, meta: { guest: true } },
  { path: '/register', component: RegisterView, meta: { guest: true } },
  { path: '/home', component: HomeView, meta: { requiresAuth: true } },
  { path: '/videos', component: () => import('../views/VideoListView.vue') },
  { path: '/course/:id', component: () => import('../views/CourseDetail.vue'), meta: { requiresAuth: true } },
  { path: '/course/:id/video/:videoId', component: () => import('../views/VideoDetail.vue'), meta: { requiresAuth: true } },
  { path: '/video/:videoId', component: () => import('../views/VideoDetail.vue') },
  { path: '/upload', component: () => import('../views/UploadView.vue'), meta: { requiresAuth: true } },
  { path: '/profile', component: () => import('../views/ProfileView.vue'), meta: { requiresAuth: true } },
  { path: '/profile/edit', component: () => import('../views/EditProfileView.vue'), meta: { requiresAuth: true } },
  { path: '/admin/login', component: () => import('../views/AdminLoginView.vue'), meta: { guest: true } },
  { path: '/admin/dashboard', component: () => import('../views/AdminDashboard.vue'), meta: { requiresAuth: true } },
  { path: '/admin/users', component: () => import('../views/AdminUsers.vue'), meta: { requiresAuth: true } },
  { path: '/admin/videos', component: () => import('../views/AdminVideos.vue'), meta: { requiresAuth: true } }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const isGuestPage = to.matched.some(record => record.meta.guest)
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)

  if (requiresAuth && !userStore.isLoggedIn) {
    next('/login')
  } else if (isGuestPage && userStore.isLoggedIn) {
    next('/home')
  } else {
    next()
  }
})

export default router
