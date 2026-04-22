<template>
  <div>
    HOME
    <button type="button"
      class="rounded-lg border border-rose-300 px-4 py-2 text-sm font-medium text-rose-700 cursor-pointer hover:bg-rose-100"
      @click="onLogout">
      Logout
    </button>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';
import { useAuthStore } from './features/auth/stores/auth.store';

const authStore = useAuthStore()
const router = useRouter()
function onLogout() {
  authStore.logout()
  router.push({ name: 'login' })
}
</script>

<style scoped></style>
