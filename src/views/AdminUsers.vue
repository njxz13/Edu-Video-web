<template>
  <div class="admin-users">
    <header>
      <h1>用户管理</h1>
      <nav>
        <router-link to="/admin/dashboard">返回</router-link>
      </nav>
    </header>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>用户名</th>
          <th>邮箱</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.userId">
          <td>{{ user.userId }}</td>
          <td>{{ user.username }}</td>
          <td>{{ user.email }}</td>
          <td>
            <button @click="handleDelete(user.userId)" class="delete-btn">删除</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getUsers, deleteUser } from '../api/admin'

const users = ref([])

const loadUsers = async () => {
  try {
    const res = await getUsers()
    if (res.data.code === 200) {
      users.value = res.data.data
    }
  } catch (err) {
    console.error(err)
  }
}

const handleDelete = async (userId) => {
  if (confirm('确定删除该用户？')) {
    try {
      await deleteUser(userId)
      loadUsers()
    } catch (err) {
      alert('删除失败')
    }
  }
}

onMounted(loadUsers)
</script>

<style scoped>
header { display: flex; justify-content: space-between; align-items: center; padding: 20px; background: #dc3545; color: white; }
nav a { color: white; text-decoration: none; }
table { width: 100%; border-collapse: collapse; margin: 20px; }
th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
.delete-btn { padding: 6px 12px; background: #dc3545; color: white; border: none; border-radius: 4px; cursor: pointer; }
</style>
