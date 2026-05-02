<template>
  <div class="profile">
    <header>
      <h1>个人信息</h1>
      <nav>
        <router-link to="/home">首页</router-link>
        <router-link to="/profile/edit">编辑</router-link>
      </nav>
    </header>
    <div class="profile-info" v-if="user">
      <p><strong>用户名:</strong> {{ user.username }}</p>
      <p><strong>邮箱:</strong> {{ user.email }}</p>
      <p><strong>注册时间:</strong> {{ user.createTime }}</p>
    </div>
    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getProfile } from '../api/auth'

const user = ref(null)
const error = ref('')
const router = useRouter()

onMounted(async () => {
  try {
    const res = await getProfile()
    if (res.data.code === 200) {
      user.value = res.data.data
    } else if (res.data.code === 401) {
      router.push('/login')
    } else {
      error.value = res.data.message
    }
  } catch (err) {
    error.value = '获取信息失败'
  }
})
</script>

<style scoped>
header { display: flex; justify-content: space-between; padding: 20px; background: #007bff; color: white; }
nav a { color: white; margin-left: 15px; text-decoration: none; }
.profile-info { max-width: 600px; margin: 20px auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; }
.profile-info p { margin: 10px 0; }
.error { color: red; text-align: center; }
</style>
