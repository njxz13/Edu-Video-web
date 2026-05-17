<template>
  <div class="video-detail">
    <header>
      <h1>{{ video?.title || '加载中...' }}</h1>
      <nav>
        <router-link to="/home">首页</router-link>
      </nav>
    </header>
    <div class="content" v-if="!loading">
      <video :src="video?.videoUrl" controls autoplay></video>
      <div class="info">
        <p>{{ video?.description }}</p>
        <p>播放次数: {{ video?.viewCount }}</p>
      </div>
      <div class="hot-videos">
        <h3>热门推荐</h3>
        <div v-for="hot in hotVideos" :key="hot.videoId" class="hot-item">
          <router-link :to="`/video/${hot.videoId}`">{{ hot.title }}</router-link>
        </div>
      </div>
    </div>
    <div v-else class="loading">加载中...</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getVideoDetail } from '../api/video'

const route = useRoute()
const video = ref(null)
const hotVideos = ref([])
const loading = ref(true)

onMounted(async () => {
  try {
    const res = await getVideoDetail(route.params.videoId)
    if (res.data.code === 200) {
      video.value = res.data.data.video
      hotVideos.value = res.data.data.hotVideos || []
    }
  } catch (err) {
    console.error(err)
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
header { display: flex; justify-content: space-between; padding: 20px; background: #007bff; color: white; }
nav a { color: white; margin-right: 15px; text-decoration: none; }
.content { padding: 20px; }
video { width: 100%; max-width: 800px; }
.hot-videos { margin-top: 30px; }
.hot-item { padding: 8px; border-bottom: 1px solid #ddd; }
.loading { text-align: center; padding: 40px; font-size: 18px; color: #666; }
</style>
