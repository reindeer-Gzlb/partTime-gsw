import request from '@/utils/request'

// 查询公式配置列表
export function listEquation(query) {
  return request({
    url: '/account/equation/list',
    method: 'get',
    params: query
  })
}

// 查询公式配置详细
export function getEquation(id) {
  return request({
    url: '/account/equation/' + id,
    method: 'get'
  })
}

// 新增公式配置
export function addEquation(data) {
  return request({
    url: '/account/equation',
    method: 'post',
    data: data
  })
}

// 修改公式配置
export function updateEquation(data) {
  return request({
    url: '/account/equation',
    method: 'put',
    data: data
  })
}

// 删除公式配置
export function delEquation(id) {
  return request({
    url: '/account/equation/' + id,
    method: 'delete'
  })
}

// 导出公式配置
export function exportEquation(query) {
  return request({
    url: '/account/equation/export',
    method: 'get',
    params: query
  })
}