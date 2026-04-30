<template>
  <div class="flex flex-row gap-4 pt-4-2 justify-center items-center">
    HOME

    <button type="button"
      class="rounded-lg border border-blue-300 px-4 py-2 text-sm font-medium text-blue-700 cursor-pointer hover:bg-blue-100 mr-2"
      @click="testApi">
      Test API
    </button>

    <p v-if="loading" class="text-gray-600 mt-2">Loading...</p>
    <p v-if="apiResponse" class="text-green-600 mt-2"> {{ apiResponse }}</p>
    <p v-if="error" class="text-red-600 mt-2"> {{ error }}</p>

    <button type="button"
      class="rounded-lg border border-rose-300 px-4 py-2 text-sm font-medium text-rose-700 cursor-pointer hover:bg-rose-100"
      @click="onLogout">
      Logout
    </button>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from './features/auth/stores/auth.store'
import { privateApi } from '@/api/axios'

const authStore = useAuthStore()
const router = useRouter()

const apiResponse = ref<string | null>(null)
const error = ref<string | null>(null)
const loading = ref(false)

function onLogout() {
  authStore.logout()
  router.push({ name: 'login' })
}
const testApi = async () => {
  loading.value = true
  apiResponse.value = null
  error.value = null

  try {
    const response = await privateApi.get('/api/test')
    apiResponse.value = response.data
  } catch (err: any) {
    error.value = err.message || 'Request Error'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped></style>
