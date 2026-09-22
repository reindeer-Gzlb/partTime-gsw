import request from '@/utils/request'

// 查询分销加价列表
export function listPrice(query) {
  return request({
    url: '/account/rprice/list',
    method: 'get',
    params: query
  })
}

// 查询分销加价详细
export function getPrice(id) {
  return request({
    url: '/account/rprice/' + id,
    method: 'get'
  })
}

// 新增分销加价
export function addPrice(data) {
  return request({
    url: '/account/rprice',
    method: 'post',
    data: data
  })
}

// 修改分销加价
export function updatePrice(data) {
  return request({
    url: '/account/rprice',
    method: 'put',
    data: data
  })
}

// 删除分销加价
export function delPrice(id) {
  return request({
    url: '/account/rprice/' + id,
    method: 'delete'
  })
}

// 导出分销加价
export function exportPrice(query) {
  return request({
    url: '/account/rprice/export',
    method: 'get',
    params: query
  })
}
// 查询供货配置
export function getConfig() {
  return request({
    url: 'account/rprice/show',
    method: 'get'
  })
}
// 查询供货商的商品数量
export function typeCount(data) {
  return request({
    url: 'goods/info/countForSupply',
    method: 'get',
    params:data
  })
}