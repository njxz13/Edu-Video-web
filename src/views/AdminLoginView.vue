<template>
  <div class="login-container">
    <div class="login-box">
      <h2>管理员登录</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label>用户名</label>
          <input v-model="username" type="text" required />
        </div>
        <div class="form-group">
          <label>密码</label>
          <input v-model="password" type="password" required />
        </div>
        <button type="submit">登录</button>
        <p v-if="error" class="error">{{ error }}</p>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { adminLogin } from '../api/admin'
import { useUserStore } from '../store/user'

const username = ref('')
const password = ref('')
const error = ref('')
const router = useRouter()
const userStore = useUserStore()

const handleLogin = async () => {
  try {
    const res = await adminLogin({ username: username.value, password: password.value })
    if (res.data.code === 200) {
      userStore.setUser({ username: res.data.data.username, userId: 0 })
      router.push('/admin/dashboard')
    } else {
      error.value = res.data.message
    }
  } catch (err) {
    error.value = '登录失败'
  }
}
</script>

<style scoped>
.login-container { display: flex; justify-content: center; align-items: center; min-height: 100vh; background: #f5f5f5; }
.login-box { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); width: 400px; }
.form-group { margin-bottom: 20px; }
.form-group label { display: block; margin-bottom: 5px; }
.form-group input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
button { width: 100%; padding: 12px; background: #dc3545; color: white; border: none; border-radius: 4px; cursor: pointer; }
.error { color: red; margin-top: 10px; }
</style>
