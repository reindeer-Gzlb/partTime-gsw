import request from '@/utils/request'

// 查询导入记录列表
export function listHistory(query) {
  return request({
    url: '/account/uplodHistory/list',
    method: 'get',
    params: query
  })
}

// 查询导入记录详细
export function getHistory(id) {
  return request({
    url: '/account/uplodHistory/' + id,
    method: 'get'
  })
}

// 新增导入记录
export function addHistory(data) {
  return request({
    url: '/account/uplodHistory',
    method: 'post',
    data: data
  })
}

// 修改导入记录
export function updateHistory(data) {
  return request({
    url: '/account/uplodHistory',
    method: 'put',
    data: data
  })
}

// 删除导入记录
export function delHistory(id) {
  return request({
    url: '/account/uplodHistory/' + id,
    method: 'delete'
  })
}

// 导出导入记录
export function exportHistory(query) {
  return request({
    url: '/account/uplodHistory/export',
    method: 'get',
    params: query
  })
}