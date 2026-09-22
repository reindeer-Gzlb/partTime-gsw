import request from '@/utils/request'

// 查询账号校验列表
export function listAccount(query) {
  return request({
    url: '/check/account/list',
    method: 'get',
    params: query
  })
}

// 查询账号校验详细
export function getAccount(id) {
  return request({
    url: '/check/account/' + id,
    method: 'get'
  })
}

// 新增账号校验
export function addAccount(data) {
  return request({
    url: '/check/account',
    method: 'post',
    data: data
  })
}

// 修改账号校验
export function updateAccount(data) {
  return request({
    url: '/check/account',
    method: 'put',
    data: data
  })
}

// 删除账号校验
export function delAccount(id) {
  return request({
    url: '/check/account/' + id,
    method: 'delete'
  })
}

// 导出账号校验
export function exportAccount(query) {
  return request({
    url: '/account/account/export',
    method: 'get',
    params: query
  })
}

export function checkAccount() {
  return request({
    url:'/system/user/getUserByRoleCode/salesCustomer',
  })
}