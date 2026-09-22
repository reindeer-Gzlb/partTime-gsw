import request from '@/utils/request'

// 客服 提号
export function tihao(data) {
  return request({
    url: '/account/order/rec/tihao',
    method: 'post',
    data
  })
}

// 完成订单  查询订单详情回显 goodsId
export function byGoodsId(id) {
  return request({
    url: `/account/order/rec/${id}`,
    method: 'get'
  })
}
// 完成订单  编辑订单
export function finishOrder(data) {
  return request({
    url: '/account/order/rec/finishOrder',
    method: 'post',
    data
  })
}
// 订单转售后
export function afterSale(data) {
  return request({
    url: '/account/order/rec/afterSale',
    method: 'post',
    data
  })
}
// 取消订单  商品重新上架
export function cancelOrder(data) {
  return request({
    url: '/need/myOrder/cancel',
    method: 'post',
    data
  })
}

// 查询销售订单记录列表
export function listRec(query) {
  return request({
    url: '/account/order/rec/list',
    method: 'get',
    params: query
  })
}
export function createList(query) {
  return request({
    url: '/account/order/rec/createList',
    method: 'get',
    params: query
  })
}

// 查询销售订单记录详细
export function getRec(id) {
  return request({
    url: '/account/order/rec/' + id,
    method: 'get'
  })
}

// 新增销售订单记录
export function addRec(data) {
  return request({
    url: '/account/order/rec',
    method: 'post',
    data: data
  })
}

// 修改销售订单记录
export function updateRec(data) {
  return request({
    url: '/account/order/rec',
    method: 'put',
    data: data
  })
}

// 删除销售订单记录
export function delRec(id) {
  return request({
    url: '/account/order/rec/' + id,
    method: 'delete'
  })
}

// 导出销售订单记录
export function exportRec(query) {
  return request({
    url: '/account/order/rec/export',
    method: 'get',
    params: query
  })
}