<template>
  <div class="course-detail">
    <header>
      <h1>{{ course?.title }}</h1>
      <nav>
        <router-link to="/home">首页</router-link>
        <span>{{ username }}</span>
      </nav>
    </header>
    <div class="content">
      <div class="video-list">
        <h3>课程视频</h3>
        <div v-for="video in videos" :key="video.videoId" class="video-item">
          <router-link :to="`/course/${course?.id}/video/${video.videoId}`">
            {{ video.title }}
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getCourseDetail } from '../api/course'
import { useUserStore } from '../store/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const course = ref(null)
const videos = ref([])
const username = ref(userStore.username)

onMounted(async () => {
  try {
    const res = await getCourseDetail(route.params.id)
    if (res.data.code === 200) {
      course.value = res.data.course
      videos.value = res.data.videos
    } else if (res.data.code === 401) {
      router.push('/login')
    }
  } catch (err) {
    console.error(err)
  }
})
</script>

<style scoped>
header { display: flex; justify-content: space-between; align-items: center; padding: 20px; background: #007bff; color: white; }
nav a { color: white; margin-right: 15px; text-decoration: none; }
.content { padding: 20px; }
.video-item { padding: 10px; border-bottom: 1px solid #ddd; }
.video-item a { color: #007bff; text-decoration: none; }
</style>
