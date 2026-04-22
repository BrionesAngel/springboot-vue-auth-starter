import axios from 'axios'

export class HttpError extends Error {
  status: number
  constructor(message: string, status: number) {
    super(message)
    this.name = 'HttpError'
    this.status = status
  }
}

const baseConfig = {
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json'
  }
}

const publicApi = axios.create(baseConfig)
const privateApi = axios.create(baseConfig)

privateApi.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

const handleError = (error: any) => {
  const status = error.response?.status
  const message =
    error.response?.data?.message ??
    `Request failed with status ${status}`

  return Promise.reject(new HttpError(message, status))
}

publicApi.interceptors.response.use(res => res, handleError)
privateApi.interceptors.response.use(res => res, handleError)

export { publicApi, privateApi }
