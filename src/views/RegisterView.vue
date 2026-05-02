<template>
  <div class="login-container">
    <div class="login-box">
      <h2>注册账号</h2>
      <form @submit.prevent="handleRegister">
        <div class="form-group">
          <label>用户名</label>
          <input v-model="username" type="text" required />
        </div>
        <div class="form-group">
          <label>密码</label>
          <input v-model="password" type="password" required />
        </div>
        <div class="form-group">
          <label>邮箱</label>
          <input v-model="email" type="email" required />
        </div>
        <button type="submit">注册</button>
        <p class="link">已有账号？<router-link to="/login">立即登录</router-link></p>
        <p v-if="error" class="error">{{ error }}</p>
        <p v-if="success" class="success">{{ success }}</p>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '../api/auth'

const username = ref('')
const password = ref('')
const email = ref('')
const error = ref('')
const success = ref('')
const router = useRouter()

const handleRegister = async () => {
  try {
    const res = await register({ username: username.value, password: password.value, email: email.value })
    if (res.data.code === 200) {
      success.value = '注册成功，即将跳转登录...'
      setTimeout(() => router.push('/login'), 1500)
    } else {
      error.value = res.data.message
    }
  } catch (err) {
    error.value = '注册失败'
  }
}
</script>

<style scoped>
.login-container { display: flex; justify-content: center; align-items: center; min-height: 100vh; background: #f5f5f5; }
.login-box { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); width: 400px; }
.form-group { margin-bottom: 20px; }
.form-group label { display: block; margin-bottom: 5px; }
.form-group input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
button { width: 100%; padding: 12px; background: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; }
.success { color: green; margin-top: 10px; }
.error { color: red; margin-top: 10px; }
.link { margin-top: 15px; text-align: center; }
</style>
