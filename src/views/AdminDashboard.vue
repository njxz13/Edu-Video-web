<template>
  <div class="admin-dashboard">
    <header>
      <h1>管理后台</h1>
      <nav>
        <router-link to="/admin/users">用户管理</router-link>
        <router-link to="/admin/videos">视频管理</router-link>
        <button @click="handleLogout">退出</button>
      </nav>
    </header>
    <div class="stats">
      <div class="stat-card">
        <h3>用户总数</h3>
        <p>{{ userCount }}</p>
      </div>
      <div class="stat-card">
        <h3>视频总数</h3>
        <p>{{ videoCount }}</p>
      </div>
    </div>
    <div class="section">
      <h2>热门视频</h2>
      <div v-for="video in hotVideos" :key="video.videoId" class="video-item">
        {{ video.title }} - 播放: {{ video.viewCount }}
      </div>
    </div>
    <div class="section">
      <h2>最新用户</h2>
      <div v-for="user in recentUsers" :key="user.userId" class="user-item">
        {{ user.username }} - {{ user.email }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getDashboard, adminLogout } from '../api/admin'

const userCount = ref(0)
const videoCount = ref(0)
const hotVideos = ref([])
const recentUsers = ref([])
const router = useRouter()

onMounted(async () => {
  try {
    const res = await getDashboard()
    if (res.data.code === 200) {
      userCount.value = res.data.userCount
      videoCount.value = res.data.videoCount
      hotVideos.value = res.data.hotVideos || []
      recentUsers.value = res.data.recentUsers || []
    }
  } catch (err) {
    console.error(err)
  }
})

const handleLogout = async () => {
  await adminLogout()
  router.push('/admin/login')
}
</script>

<style scoped>
header { display: flex; justify-content: space-between; align-items: center; padding: 20px; background: #dc3545; color: white; }
nav a { color: white; margin-right: 15px; text-decoration: none; }
nav button { padding: 8px 16px; background: white; color: #dc3545; border: none; border-radius: 4px; cursor: pointer; }
.stats { display: flex; gap: 20px; padding: 20px; }
.stat-card { flex: 1; padding: 20px; background: #f8f9fa; border-radius: 8px; text-align: center; }
.section { padding: 20px; }
.video-item, .user-item { padding: 10px; border-bottom: 1px solid #ddd; }
</style>
