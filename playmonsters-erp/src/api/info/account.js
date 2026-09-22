import request from '@/utils/request'

// 查询零售号列表
export function listAccount(query) {
  return request({
    url: '/account/account/list',
    method: 'get',
    params: query
  })
}

// 查询零售号详细
export function getAccount(id) {
  return request({
    url: '/account/account/' + id,
    method: 'get'
  })
}

// 新增零售号
export function addAccount(data) {
  return request({
    url: '/account/account',
    method: 'post',
    data: data
  })
}

// 修改零售号
export function updateAccount(data) {
  return request({
    url: '/account/account',
    method: 'put',
    data: data
  })
}

// 删除零售号
export function delAccount(id) {
  return request({
    url: '/account/account/' + id,
    method: 'delete'
  })
}
// 添加为我的账号
export function addMyAcount(id) {
  return request({
    url: '/account/account/addMyAcount/' + id,
    method: 'get'
  })
}

export function addCmdAccount(id) {
  return request({
    url: '/system/info/addCmdAccount/' + id,
    method: 'get',
    timeout: 200000200
  })
}

export function bacthAddPrice(data) {
  return request({
    url: '/account/account/bacthAddPrice',
    method: 'get',
    params: data,
    timeout: 200000
  })
}
export function bacthAddPriceDetails(data) {
  return request({
    url: '/account/account/bacthAddPriceDetails',
    method: 'get',
    params: data,
    timeout: 200000
  })
}

// 导出零售号
export function exportAccount(query) {
  return request({
    url: '/account/account/export',
    method: 'get',
    params: query
  })
}
