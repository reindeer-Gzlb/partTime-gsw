import request from '@/utils/request'

// 查询操作历史记录列表
export function listHistory(query) {
  return request({
    url: '/account/history/list',
    method: 'get',
    params: query
  })
}

// 查询操作历史记录详细
export function getHistory(id) {
  return request({
    url: '/account/history/' + id,
    method: 'get'
  })
}

// 导出操作历史记录
export function exportHistory(query) {
  return request({
    url: '/account/history/export',
    method: 'get',
    params: query
  })
}


// 查询操作历史状态
export function selectOperationStatus() {
  return request({
    url: '/account/history/selectOperationStatus',
    method: 'get',
  })
}
