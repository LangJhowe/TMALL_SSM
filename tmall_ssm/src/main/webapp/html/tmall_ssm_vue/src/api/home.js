import request from '@/util/request'

// 推荐搜索
export function getRecommendSearch () {
  return request({
    url: '/getRecommendSearch',
    method: 'get'
  })
}
