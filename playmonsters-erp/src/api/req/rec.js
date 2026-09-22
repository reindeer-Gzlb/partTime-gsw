import request from '@/utils/request'

// 查询api请求记录列表
export function listRec(query) {
  return request({
    url: '/account/req/rec/list',
    method: 'get',
    params: query
  })
}

// 查询api请求记录详细
export function getRec(id) {
  return request({
    url: '/account/req/rec/' + id,
    method: 'get'
  })
}

// 新增api请求记录
export function addRec(data) {
  return request({
    url: '/account/req/rec',
    method: 'post',
    data: data
  })
}

// 修改api请求记录
export function updateRec(data) {
  return request({
    url: '/account/req/rec',
    method: 'put',
    data: data
  })
}

// 删除api请求记录
export function delRec(id) {
  return request({
    url: '/account/req/rec/' + id,
    method: 'delete'
  })
}

// 导出api请求记录
export function exportRec(query) {
  return request({
    url: '/account/req/rec/export',
    method: 'get',
    params: query
  })
}