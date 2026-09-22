import request from '@/utils/request'

// 查询批量加价列表
export function listPrice(query) {
  return request({
    url: '/account/price/list',
    method: 'get',
    params: query
  })
}

// 查询批量加价详细
export function getPrice(id) {
  return request({
    url: '/account/price/' + id,
    method: 'get'
  })
}

// 新增批量加价
export function addPrice(data) {
  return request({
    url: '/account/price',
    method: 'post',
    data: data,
    timeout: 200000
  })
}

// 修改批量加价
export function updatePrice(data) {
  return request({
    url: '/account/price',
    method: 'put',
    data: data,
    timeout: 200000
  })
}

// 删除批量加价
export function delPrice(id) {
  return request({
    url: '/account/price/' + id,
    method: 'delete'
  })
}

// 导出批量加价
export function exportPrice(query) {
  return request({
    url: '/account/price/export',
    method: 'get',
    params: query
  })
}
