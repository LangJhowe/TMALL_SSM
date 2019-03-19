import request from '@/util/request'
import Qs from 'qs'

// login
export function loginByUser (form) {
  // console.log()
  return request({
    url: `/loginByUser`,
    method: 'post',
    data: Qs.stringify(form)
    // data: form

  })
}

// registry
export function registry (form) {
  return request({
    url: '/registry',
    method: 'post',
    data: Qs.stringify(form)
  })
}
