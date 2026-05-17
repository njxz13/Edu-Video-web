<template>
  <div class="upload">
    <header>
      <h1>上传视频</h1>
      <nav>
        <router-link to="/home">首页</router-link>
      </nav>
    </header>
    <div class="upload-form">
      <form @submit.prevent="handleUpload">
        <div class="form-group">
          <label>标题</label>
          <input v-model="title" type="text" required />
        </div>
        <div class="form-group">
          <label>描述</label>
          <textarea v-model="description" required></textarea>
        </div>
        <div class="form-group">
          <label>分类ID</label>
          <input v-model="categoryId" type="number" required />
        </div>
        <div class="form-group">
          <label>标签</label>
          <input v-model="tags" type="text" placeholder="用逗号分隔" />
        </div>
        <div class="form-group">
          <label>视频文件</label>
          <input @change="handleVideoChange" type="file" accept="video/*" required />
          <p v-if="fileError" class="field-error">{{ fileError }}</p>
        </div>
        <div class="form-group">
          <label>封面图（可选）</label>
          <input @change="handleCoverChange" type="file" accept="image/*" />
        </div>
        <button type="submit" :disabled="uploading">{{ uploading ? '上传中...' : '上传' }}</button>
        <p v-if="error" class="error">{{ error }}</p>
        <p v-if="success" class="success">{{ success }}</p>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { uploadVideo } from '../api/video'

const title = ref('')
const description = ref('')
const categoryId = ref('')
const tags = ref('')
const videoFile = ref(null)
const coverFile = ref(null)
const error = ref('')
const success = ref('')
const uploading = ref(false)
const fileError = ref('')
const router = useRouter()

const handleVideoChange = (e) => {
  const file = e.target.files[0]
  fileError.value = ''
  if (!file) return
  const maxSize = 500 * 1024 * 1024
  if (file.size > maxSize) {
    fileError.value = '视频文件大小不能超过 500MB'
    videoFile.value = null
    return
  }
  videoFile.value = file
}

const handleCoverChange = (e) => {
  const file = e.target.files[0]
  if (!file) return
  const maxSize = 10 * 1024 * 1024
  if (file.size > maxSize) {
    fileError.value = '封面图片大小不能超过 10MB'
    coverFile.value = null
    return
  }
  coverFile.value = file
}

const handleUpload = async () => {
  if (!videoFile.value) {
    error.value = '请选择视频文件'
    return
  }

  const formData = new FormData()
  formData.append('title', title.value)
  formData.append('description', description.value)
  formData.append('category', categoryId.value)
  formData.append('tags', tags.value)
  formData.append('videoFile', videoFile.value)
  if (coverFile.value) formData.append('coverFile', coverFile.value)

  uploading.value = true
  error.value = ''
  try {
    const res = await uploadVideo(formData)
    if (res.data.code === 200) {
      success.value = '上传成功！'
      setTimeout(() => router.push(`/video/${res.data.data.videoId}`), 1500)
    } else {
      error.value = res.data.message
    }
  } catch (err) {
    error.value = '上传失败'
  } finally {
    uploading.value = false
  }
}
</script>

<style scoped>
header { display: flex; justify-content: space-between; padding: 20px; background: #007bff; color: white; }
nav a { color: white; text-decoration: none; }
.upload-form { max-width: 600px; margin: 20px auto; padding: 20px; }
.form-group { margin-bottom: 15px; }
.form-group label { display: block; margin-bottom: 5px; }
.form-group input, .form-group textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
textarea { height: 100px; }
button { padding: 12px 30px; background: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; }
button:disabled { opacity: 0.5; cursor: not-allowed; }
.error { color: red; margin-top: 10px; }
.success { color: green; margin-top: 10px; }
.field-error { color: red; font-size: 12px; margin-top: 3px; }
</style>
