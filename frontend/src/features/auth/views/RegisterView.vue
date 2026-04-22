<template>
  <section class="mx-auto flex min-h-screen max-w-md items-center px-4">
    <div class="w-full rounded-2xl border border-slate-200 bg-white p-8 shadow-lg">
      <h1 class="text-2xl font-bold text-slate-900">Register</h1>
      <p class="mt-1 text-sm text-slate-600">Sign up to manage your app.</p>

      <form class="mt-6 space-y-4" @submit.prevent="onSubmit">
        <div>
          <label class="mb-1 block text-sm font-medium text-slate-700" for="username">Username</label>
          <input id="username" v-model="form.username" type="text" required autocomplete="username"
            class="w-full rounded-lg border border-slate-300 px-3 py-2 outline-none ring-lime-200 focus:ring" />
        </div>

        <div>
          <label class="mb-1 block text-sm font-medium text-slate-700" for="email">Email</label>
          <input id="email" v-model="form.email" type="email" required autocomplete="email"
            class="w-full rounded-lg border border-slate-300 px-3 py-2 outline-none ring-lime-200 focus:ring" />
        </div>

        <div>
          <label class="mb-1 block text-sm font-medium text-slate-700" for="password">Password</label>
          <input id="password" v-model="form.password" type="password" required autocomplete="new-password"
            class="w-full rounded-lg border border-slate-300 px-3 py-2 outline-none ring-lime-200 focus:ring" />
        </div>

        <div>
          <label class="mb-1 block text-sm font-medium text-slate-700" for="confirmPassword">Confirm Password</label>
          <input id="confirmPassword" v-model="form.confirmPassword" type="password" required
            autocomplete="new-password"
            class="w-full rounded-lg border border-slate-300 px-3 py-2 outline-none ring-lime-200 focus:ring" />
        </div>

        <p v-if="error" class="text-sm text-rose-600">{{ error }}</p>

        <button type="submit"
          class="w-full rounded-lg bg-lime-400 px-4 py-2 font-medium text-white hover:bg-lime-600 disabled:cursor-not-allowed disabled:opacity-60"
          :disabled="authStore.isLoading">
          {{ authStore.isLoading ? 'Signing up...' : 'Sign up' }}
        </button>
        <p class="text-center text-sm text-slate-500">
          You have an account?
          <RouterLink to="/login" class="font-medium text-lime-400 hover:underline">
            Sign in
          </RouterLink>
        </p>
      </form>
    </div>
  </section>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth.store'
import { HttpError } from '@/api/axios'

const authStore = useAuthStore()
const router = useRouter()

const form = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
})

const error = ref('')

async function onSubmit() {
  if (authStore.isLoading) return
  error.value = ''

  try {
    if (!validate()) return
    await authStore.register({
      username: form.username,
      email: form.email,
      password: form.password
    })
    await router.push({ name: 'home' })
    form.username = ''
    form.email = ''
    form.password = ''
    form.confirmPassword = ''
  } catch (err) {
    error.value = err instanceof HttpError ? err.message : 'Registration failed. Please try again.'
  }
}
function validate() {
  if (!form.username || !form.email || !form.password) {
    error.value = 'All fields are required'
    return false
  }

  const emailRe = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!emailRe.test(form.email)) {
    error.value = 'Enter a valid email'
    return false
  }

  if (form.password.length < 8) {
    error.value = 'Password must be at least 8 characters'
    return false
  }

  if (form.password !== form.confirmPassword) {
    error.value = 'Passwords do not match'
    return false
  }

  return true
}
</script>
