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

// 添加购物车
export function addCart (form) {
  return request({
    url: '/addCart',
    method: 'post',
    data: Qs.stringify(form)
  })
}

// 获取订单
export function getOrderItem (oiidList) {
  return request({
    url: '/getOrderItem',
    method: 'post',
    data: Qs.stringify({'oiidList': JSON.stringify(oiidList)})
  })
}

// 确认付款更新订单
export function confirmPay (oidList) {
  return request({
    url: '/confirmPay',
    method: 'post',
    data: Qs.stringify({oidList: JSON.stringify(oidList)})
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

// 催卖家发货
export function askDelivery (form) {
  return request({
    url: '/askDelivery',
    method: 'post',
    data: Qs.stringify(form)
  })
}

// 确认收货
export function confirmGot (form) {
  return request({
    url: '/confirmGot',
    method: 'post',
    data: Qs.stringify(form)
  })
}

// 最终确认付款
export function finalConfirmPay (form) {
  return request({
    url: '/finalConfirmPay',
    method: 'post',
    data: Qs.stringify(form)
  })
}

// 评价
export function review (form) {
  return request({
    url: '/review',
    method: 'post',
    data: Qs.stringify(form)
  })
}

// 删除交易订单
export function deleteOrder (form) {
  return request({
    url: '/deleteOrder',
    method: 'post',
    data: Qs.stringify(form)
  })
}

// 添加购物车信息
export function addToCart (form) {
  return request({
    url: '/addToCart',
    method: 'post',
    data: Qs.stringify(form)
  })
}

// 获取购物车信息
export function getCartsData (form) {
  return request({
    url: '/getCartsData',
    method: 'post',
    data: Qs.stringify(form)
  })
}
// 删除购物车中的商品
export function deleteOrderItem (form) {
  return request({
    url: '/deleteOrderItem',
    method: 'post',
    data: Qs.stringify(form)
  })
}
