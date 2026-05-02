<template>
  <div class="admin-videos">
    <header>
      <h1>视频管理</h1>
      <nav>
        <router-link to="/admin/dashboard">返回</router-link>
      </nav>
    </header>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>标题</th>
          <th>上传者</th>
          <th>状态</th>
          <th>播放量</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="video in videos" :key="video.videoId">
          <td>{{ video.videoId }}</td>
          <td>{{ video.title }}</td>
          <td>{{ video.uploaderName }}</td>
          <td>{{ video.status === 1 ? '上架' : '下架' }}</td>
          <td>{{ video.viewCount }}</td>
          <td>
            <button @click="toggleStatus(video)" class="status-btn">
              {{ video.status === 1 ? '下架' : '上架' }}
            </button>
            <button @click="handleDelete(video.videoId)" class="delete-btn">删除</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getVideos, updateVideoStatus, deleteVideo } from '../api/admin'

const videos = ref([])

const loadVideos = async () => {
  try {
    const res = await getVideos()
    if (res.data.code === 200) {
      videos.value = res.data.data
    }
  } catch (err) {
    console.error(err)
  }
}

const toggleStatus = async (video) => {
  const newStatus = video.status === 1 ? 0 : 1
  try {
    await updateVideoStatus(video.videoId, newStatus)
    loadVideos()
  } catch (err) {
    alert('操作失败')
  }
}

const handleDelete = async (videoId) => {
  if (confirm('确定删除该视频？')) {
    try {
      await deleteVideo(videoId)
      loadVideos()
    } catch (err) {
      alert('删除失败')
    }
  }
}

onMounted(loadVideos)
</script>

<style scoped>
header { display: flex; justify-content: space-between; align-items: center; padding: 20px; background: #dc3545; color: white; }
nav a { color: white; text-decoration: none; }
table { width: 100%; border-collapse: collapse; margin: 20px; }
th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
.status-btn { padding: 6px 12px; background: #ffc107; color: black; border: none; border-radius: 4px; cursor: pointer; margin-right: 5px; }
.delete-btn { padding: 6px 12px; background: #dc3545; color: white; border: none; border-radius: 4px; cursor: pointer; }
</style>
