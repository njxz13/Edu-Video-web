import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const username = ref('')
  const userId = ref(null)
  const isLoggedIn = ref(false)

  const setUser = (data) => {
    username.value = data.username
    userId.value = data.userId
    isLoggedIn.value = true
  }

  const clearUser = () => {
    username.value = ''
    userId.value = null
    isLoggedIn.value = false
  }

  return { username, userId, isLoggedIn, setUser, clearUser }
})
