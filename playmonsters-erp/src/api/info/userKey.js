import request from '@/utils/request'

// 查询密钥列表
export function listKey(query) {
  return request({
    url: '/account/key/list',
    method: 'get',
    params: query
  })
}

// 查询密钥详细
export function getKey(id) {
  return request({
    url: '/account/key/' + id,
    method: 'get'
  })
}

// 新增密钥
export function addKey(data) {
  return request({
    url: '/account/key',
    method: 'post',
    data: data
  })
}

// 修改密钥
export function updateKey(data) {
  return request({
    url: '/account/key',
    method: 'put',
    data: data
  })
}

// 删除密钥
export function delKey(id) {
  return request({
    url: '/account/key/' + id,
    method: 'delete'
  })
}

// 导出密钥
export function exportKey(query) {
  return request({
    url: '/account/key/export',
    method: 'get',
    params: query
  })
}