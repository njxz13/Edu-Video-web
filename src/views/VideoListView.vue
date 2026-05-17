<template>
  <div class="video-list">
    <header>
      <h1>视频列表</h1>
      <nav>
        <router-link to="/home">首页</router-link>
        <router-link to="/upload">上传视频</router-link>
      </nav>
    </header>
    <div class="filters">
      <input v-model="keyword" placeholder="搜索视频..." @input="debouncedSearch" />
      <select v-model="categoryId" @change="searchVideos">
        <option value="">全部分类</option>
        <option value="1">前端开发</option>
        <option value="2">后端开发</option>
        <option value="3">移动开发</option>
      </select>
    </div>
    <div v-if="loading" class="loading">加载中...</div>
    <div v-else class="videos">
      <div v-for="video in videos" :key="video.videoId" class="video-card">
        <img v-if="video.coverUrl" :src="video.coverUrl" class="cover" />
        <div class="info">
          <h3><router-link :to="`/video/${video.videoId}`">{{ video.title }}</router-link></h3>
          <p>{{ video.description }}</p>
          <p>播放: {{ video.viewCount }} | 点赞: {{ video.likeCount }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getVideos } from '../api/video'

const videos = ref([])
const keyword = ref('')
const categoryId = ref('')
const loading = ref(true)

let debounceTimer = null
const debouncedSearch = () => {
  clearTimeout(debounceTimer)
  debounceTimer = setTimeout(searchVideos, 300)
}

const searchVideos = async () => {
  try {
    const res = await getVideos({ 
      keyword: keyword.value || undefined, 
      categoryId: categoryId.value || undefined 
    })
    if (res.data.code === 200) {
      videos.value = res.data.data
    }
  } catch (err) {
    console.error(err)
  }
}

onMounted(async () => {
  await searchVideos()
  loading.value = false
})
</script>

<style scoped>
header { display: flex; justify-content: space-between; align-items: center; padding: 20px; background: #007bff; color: white; }
nav a { color: white; margin-left: 15px; text-decoration: none; }
.filters { padding: 20px; background: #f8f9fa; display: flex; gap: 15px; }
.filters input, .filters select { padding: 8px; border: 1px solid #ddd; border-radius: 4px; }
.videos { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 20px; padding: 20px; }
.video-card { border: 1px solid #ddd; border-radius: 8px; overflow: hidden; }
.cover { width: 100%; height: 180px; object-fit: cover; }
.info { padding: 15px; }
.info a { color: #007bff; text-decoration: none; }
.loading { text-align: center; padding: 40px; font-size: 18px; color: #666; }
</style>
