<template>
  <div class="home">
    <header>
      <h1>教育视频网站</h1>
      <nav>
        <router-link to="/videos">视频列表</router-link>
        <span>{{ userStore.username }}</span>
        <button @click="handleLogout">退出</button>
      </nav>
    </header>
    <div v-if="loading" class="loading">加载中...</div>
    <div v-else class="course-list">
      <div v-for="course in courses" :key="course.id" class="course-card">
        <h3>{{ course.title }}</h3>
        <p>{{ course.description }}</p>
        <router-link :to="`/course/${course.id}`">查看详情</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getCourses } from '../api/course'
import { logout } from '../api/auth'
import { useUserStore } from '../store/user'

const courses = ref([])
const loading = ref(true)
const router = useRouter()
const userStore = useUserStore()

onMounted(async () => {
  try {
    const res = await getCourses()
    if (res.data.code === 200) {
      courses.value = res.data.data.courses
    } else if (res.data.code === 401) {
      router.push('/login')
    }
  } catch (err) {
    console.error(err)
  } finally {
    loading.value = false
  }
})

const handleLogout = async () => {
  await logout()
  userStore.clearUser()
  router.push('/login')
}
</script>

<style scoped>
header { display: flex; justify-content: space-between; align-items: center; padding: 20px; background: #007bff; color: white; }
nav a { color: white; margin-right: 15px; text-decoration: none; }
.course-list { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 20px; padding: 20px; }
.course-card { border: 1px solid #ddd; padding: 20px; border-radius: 8px; }
button { padding: 8px 16px; background: white; color: #007bff; border: none; border-radius: 4px; cursor: pointer; }
.loading { text-align: center; padding: 40px; font-size: 18px; color: #666; }
</style>
