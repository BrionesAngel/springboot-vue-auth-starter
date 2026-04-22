import { publicApi } from '@/api/axios'
import type { LoginRequest, LoginResponse, RegisterRequest, RegisterResponse } from '../dtos/auth.dtos.ts'

export const authService = {
  login(payload: LoginRequest) {
    return publicApi.post<LoginResponse>('/auth/login', payload)
  },
  register(payload: RegisterRequest) {
    return publicApi.post<RegisterResponse>('/auth/register', payload)
  }
}
