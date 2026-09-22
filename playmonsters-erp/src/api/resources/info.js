import request from '@/utils/request'

// 查询游戏账号列表
export function listInfo(query) {
  return request({
    url: '/system/info/toBeRecycledList',
    method: 'get',
    params: query
  })
}

// 查询游戏账号详细
export function getInfo(id) {
  return request({
    url: '/system/recovery/' + id,
    method: 'get'
  })
}

// 审核包赔游戏账号
export function auditInfo(data) {
  return request({
    url: '/system/info/auditInfo',
    method: 'put',
    data: data
  })
}

// 修改游戏账号
export function updateInfo(data) {
  return request({
    url: '/system/info/recoveryInfo',
    method: 'put',
    data: data
  })
}

// 导出游戏账号
export function exportInfo(query) {
  return request({
    url: '/system/info/export',
    method: 'get',
    params: query
  })
}


// 申请回收游戏账号游戏账号
export function updateToBeRecycled(id) {
  return request({
    url: '/system/info/updateToBeRecycled/' + id,
    method: 'put'
  })
}
