import request from '@/utils/request'

// 查询联调记录列表
export function listHistory(query) {
  return request({
    url: '/account/importHistory/list',
    method: 'get',
    params: query
  })
}

// 查询联调记录详细
export function getHistory(id) {
  return request({
    url: '/account/importHistory/' + id,
    method: 'get'
  })
}

// 新增联调记录
export function addHistory(data) {
  return request({
    url: '/account/importHistory',
    method: 'post',
    data: data
  })
}

// 修改联调记录
export function updateHistory(data) {
  return request({
    url: '/account/importHistory',
    method: 'put',
    data: data
  })
}

// 删除联调记录
export function delHistory(id) {
  return request({
    url: '/account/importHistory/' + id,
    method: 'delete'
  })
}

// 导出联调记录
export function exportHistory(query) {
  return request({
    url: '/account/importHistory/export',
    method: 'get',
    params: query
  })
}