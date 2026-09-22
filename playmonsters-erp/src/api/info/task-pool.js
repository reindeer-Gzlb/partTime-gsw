import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listPool(query) {
  return request({
    url: '/task/pool/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getPool(id) {
  return request({
    url: '/task/pool/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addPool(data) {
  return request({
    url: '/task/pool',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updatePool(data) {
  return request({
    url: '/task/pool',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delPool(id) {
  return request({
    url: '/task/pool/' + id,
    method: 'delete'
  })
}