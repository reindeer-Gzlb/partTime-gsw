import request from '@/utils/request'

// 查询游戏账号列表
export function listInfo(query) {
  return request({
    url: '/system/recovery/list',
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

export function byColums(colum,type) {
  return request({
    url: '/system/info/byColums/'+colum+ '/' + type,
    method: 'get'
  })
}


// 编辑游戏账号
export function recoveryEditInfo(data) {
  return request({
    url: '/system/recovery/recoveryEditInfo',
    method: 'put',
    data: data
  })
}
