import request from '@/utils/request'

// 查询折扣率列表
export function listRateof(query) {
  return request({
    url: '/account/rateof/list',
    method: 'get',
    params: query
  })
}

// 查询折扣率详细
export function getRateof(id) {
  return request({
    url: '/account/rateof/' + id,
    method: 'get'
  })
}

// 新增折扣率
export function addRateof(data) {
  return request({
    url: '/account/rateof',
    method: 'post',
    data: data
  })
}

// 修改折扣率
export function updateRateof(data) {
  return request({
    url: '/account/rateof',
    method: 'put',
    data: data
  })
}

// 删除折扣率
export function delRateof(id) {
  return request({
    url: '/account/rateof/' + id,
    method: 'delete'
  })
}

// 导出折扣率
export function exportRateof(query) {
  return request({
    url: '/account/rateof/export',
    method: 'get',
    params: query
  })
}