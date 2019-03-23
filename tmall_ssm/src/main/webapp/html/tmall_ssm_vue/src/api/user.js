import request from '@/util/request'
import Qs from 'qs'

// 创建订单
export function createOrder (form) {
  return request({
    url: '/createOrder',
    method: 'post',
    data: Qs.stringify({'data': JSON.stringify(form)})
  })
}

// 购买商品
export function buyOneProduct (form) {
  return request({
    url: '/buyOneProduct',
    method: 'post',
    data: Qs.stringify(form)
  })
}

// 获取订单
export function getOrderItem (form) {
  return request({
    url: '/getOrderItem',
    method: 'post',
    data: Qs.stringify({'data': JSON.stringify(form)})
  })
}

// 确认付款更新订单
export function confirmPay (form) {
  return request({
    url: '/confirmPay',
    method: 'post',
    data: Qs.stringify(form)
  })
}

// 获取单个订单
export function getPayedOrder (form) {
  return request({
    url: '/getPayedOrder',
    method: 'post',
    data: Qs.stringify(form)
  })
}

// 获取所有订单
export function getOrders (form) {
  return request({
    url: '/getOrders',
    method: 'post',
    data: Qs.stringify(form)
  })
}
