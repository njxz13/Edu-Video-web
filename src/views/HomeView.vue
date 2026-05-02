<template>
  <div class="home">
    <header>
      <h1>教育视频网站</h1>
      <nav>
        <span>{{ username }}</span>
        <button @click="handleLogout">退出</button>
      </nav>
    </header>
    <div class="course-list">
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
const username = ref('')
const router = useRouter()
const userStore = useUserStore()

onMounted(async () => {
  try {
    const res = await getCourses()
    if (res.data.code === 200) {
      courses.value = res.data.data
      username.value = res.data.username || userStore.username
    } else if (res.data.code === 401) {
      router.push('/login')
    }
  } catch (err) {
    console.error(err)
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
.course-list { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 20px; padding: 20px; }
.course-card { border: 1px solid #ddd; padding: 20px; border-radius: 8px; }
button { padding: 8px 16px; background: white; color: #007bff; border: none; border-radius: 4px; cursor: pointer; }
</style>
