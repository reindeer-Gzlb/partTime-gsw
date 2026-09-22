import request from '@/utils/request'

// 查询供货商授权表列表
export function listAuth(query) {
  return request({
    url: '/account/auth/list',
    method: 'get',
    params: query
  })
}
export function getSupeAuthByUserId(query) {
  return request({
    url: '/account/auth/getSupeAuthByUserId',
    method: 'get',
    params: query
  })
}

// 查询供货商授权表详细
export function getAuth(id) {
  return request({
    url: '/account/auth/' + id,
    method: 'get'
  })
}

// 新增供货商授权表
export function addAuth(data) {
  return request({
    url: '/account/auth',
    method: 'post',
    data: data
  })
}

// 修改供货商授权表
export function updateAuth(data) {
  return request({
    url: '/account/auth',
    method: 'put',
    data: data
  })
}

// 删除供货商授权表
export function delAuth(id) {
  return request({
    url: '/account/auth/' + id,
    method: 'delete'
  })
}

// 导出供货商授权表
export function exportAuth(query) {
  return request({
    url: '/account/auth/export',
    method: 'get',
    params: query
  })
}