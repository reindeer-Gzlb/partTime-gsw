import request from '@/utils/request'

// 查询黑名单列表列表
export function listBlack(query) {
  return request({
    url: '/black/blackList',
    method: 'get',
    params: query
  })
}

// 查询黑名单列表详细
export function gameType() {
  return request({
    url: '/black/gameType',
    method: 'get'
  })
}

// 新增黑名单列表
export function addBlack(data) {
  return request({
    url: '/account/black',
    method: 'post',
    data: data
  })
}

// 添加黑名单
export function addBlackAccount(data) {
  return request({
    url: '/black/addBlack',
    method: 'post',
    data
  })
}

// 修改黑名单列表
export function updateBlack(data) {
  return request({
    url: '/account/black',
    method: 'put',
    data: data
  })
}

// 删除黑名单列表
export function delBlack(id) {
  return request({
    url: '/account/black/' + id,
    method: 'delete'
  })
}

// 导出黑名单列表
export function exportBlack(query) {
  return request({
    url: '/account/black/export',
    method: 'get',
    params: query
  })
}
