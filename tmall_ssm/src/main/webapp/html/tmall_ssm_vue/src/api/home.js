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
export function searchByKeywords (keyword) {
  return request({
    url: '/searchByKeywords',
    method: 'post',
    data: Qs.stringify({keyword: keyword})
  })
}
