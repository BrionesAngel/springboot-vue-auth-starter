import api from '@/api/axios.ts'
import type { LoginRequest, LoginResponse, RegisterRequest, RegisterResponse } from '../dtos/auth.dtos.ts'

export const authService = {
  login(payload: LoginRequest) {
    return api.post<LoginResponse>('/auth/login', payload)
  },
  register(payload: RegisterRequest) {
    return api.post<RegisterResponse>('/auth/register', payload)
  }
}
