import request from '@/util/request'
import Qs from 'qs'

// 推荐搜索
export function getRecommendSearch () {
  return request({
    url: '/getRecommendSearch',
    method: 'get'
  })
}

// 竖 菜单 获取商品 subtitle
export function getCategorys (id) {
  return request({
    url: `/getCategorys/cid=${id}`,
    method: 'get'
  })
}

export function getProductsByCategory () {
  return request({
    url: '/getProductsByCategory',
    method: 'get'
  })
}

// searchBykeywords
export function searchByKeywords (form) {
  return request({
    url: '/searchByKeywords',
    method: 'post',
    data: Qs.stringify(form)
  })
}

// searchByCategory
export function searchByCategory (form) {
  return request({
    url: '/searchByCategory',
    method: 'post',
    data: Qs.stringify(form)
  })
}

// getProduct
export function getProduct (pid) {
  return request({
    url: '/getProduct',
    method: 'post',
    data: Qs.stringify({pid})
  })
}

// getReview
export function getReviews (form) {
  return request({
    url: '/getReviews',
    method: 'post',
    data: Qs.stringify(form)
  })
}
