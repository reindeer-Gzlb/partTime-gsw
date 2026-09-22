import request from '@/utils/request'

// 查询推送记录列表
export function listRec(query) {
  return request({
    url: '/account/rec/list',
    method: 'get',
    params: query
  })
}

// 查询推送记录详细
export function getRec(id) {
  return request({
    url: '/account/rec/' + id,
    method: 'get'
  })
}

// 新增推送记录
export function addRec(data) {
  return request({
    url: '/account/rec',
    method: 'post',
    data: data
  })
}

// 修改推送记录
export function updateRec(data) {
  return request({
    url: '/account/rec',
    method: 'put',
    data: data
  })
}

// 删除推送记录
export function delRec(saleCode) {
  return request({
    url: '/account/rec/' + saleCode,
    method: 'delete'
  })
}

// 删除推送记录-新
export function delRecNew(saleCode) {
  return request({
    url: '/account/rec/delAllOut?saleCode=' + saleCode,
    method: 'get'
  })
}

// 导出推送记录
export function exportRec(query) {
  return request({
    url: '/account/rec/export',
    method: 'get',
    params: query
  })
}
