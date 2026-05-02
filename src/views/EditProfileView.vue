<template>
  <div class="edit-profile">
    <header>
      <h1>编辑个人信息</h1>
      <nav>
        <router-link to="/profile">返回</router-link>
      </nav>
    </header>
    <div class="form-container" v-if="user">
      <form @submit.prevent="handleUpdate">
        <div class="form-group">
          <label>用户名</label>
          <input v-model="username" type="text" required />
        </div>
        <div class="form-group">
          <label>邮箱</label>
          <input v-model="email" type="email" required />
        </div>
        <div class="form-group">
          <label>头像URL（可选）</label>
          <input v-model="avatar" type="text" />
        </div>
        <button type="submit">保存</button>
        <p v-if="error" class="error">{{ error }}</p>
        <p v-if="success" class="success">{{ success }}</p>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getProfile, updateProfile } from '../api/auth'

const username = ref('')
const email = ref('')
const avatar = ref('')
const user = ref(null)
const error = ref('')
const success = ref('')
const router = useRouter()

onMounted(async () => {
  try {
    const res = await getProfile()
    if (res.data.code === 200) {
      user.value = res.data.data
      username.value = user.value.username
      email.value = user.value.email
      avatar.value = user.value.avatar || ''
    }
  } catch (err) {
    error.value = '获取信息失败'
  }
})

const handleUpdate = async () => {
  try {
    const res = await updateProfile({
      username: username.value,
      email: email.value,
      avatar: avatar.value
    })
    if (res.data.code === 200) {
      success.value = '更新成功！'
      setTimeout(() => router.push('/profile'), 1500)
    } else {
      error.value = res.data.message
    }
  } catch (err) {
    error.value = '更新失败'
  }
}
</script>

<style scoped>
header { display: flex; justify-content: space-between; padding: 20px; background: #007bff; color: white; }
nav a { color: white; text-decoration: none; }
.form-container { max-width: 600px; margin: 20px auto; padding: 20px; }
.form-group { margin-bottom: 15px; }
.form-group label { display: block; margin-bottom: 5px; }
.form-group input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
button { padding: 12px 30px; background: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; }
.error { color: red; margin-top: 10px; }
.success { color: green; margin-top: 10px; }
</style>
