import axios from 'axios'

const service = axios.create({
  baseURL: 'http://127.0.0.1:8080/tmall_ssm',
  time: 5000 // request timeout
})

service.interceptors.request.use(config => {
  return config
}, error => {
  Promise.reject(error)
})

service.interceptors.response.use(
  response => {
    return response
  },
  error => {
    return Promise.reject(error)
  }
)

export default service
