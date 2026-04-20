import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import { authService } from '../services/auth.service.ts'
import type { LoginRequest, RegisterRequest } from '../dtos/auth.dtos.ts'

const TOKEN_KEY = 'token'

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string | null>(null)
  const isLoading = ref(false)
  const isAuthenticated = computed(() => Boolean(token.value))

  function initializeFromStorage() {
    token.value = localStorage.getItem(TOKEN_KEY)
  }

  async function login(credentials: LoginRequest) {
    isLoading.value = true
    try {
      const { data } = await authService.login(credentials)
      token.value = data.token
      localStorage.setItem(TOKEN_KEY, data.token)
    } finally {
      isLoading.value = false
    }
  }

  async function register(credentials: RegisterRequest) {
    isLoading.value = true
    try {
      const { data } = await authService.register(credentials)
      token.value = data.token
      localStorage.setItem(TOKEN_KEY, data.token)
    } finally {
      isLoading.value = false
    }
  }

  function logout() {
    token.value = null
    localStorage.removeItem(TOKEN_KEY)
  }

  return {
    token,
    isLoading,
    isAuthenticated,
    initializeFromStorage,
    login,
    register,
    logout,
  }
})
