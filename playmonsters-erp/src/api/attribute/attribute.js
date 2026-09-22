import request from '@/utils/request'

// 查询游戏配置列表
export function listAttribute(query) {
  return request({
    url: '/account/attribute/list',
    method: 'get',
    params: query
  })
}

//查询游戏配置列表
export function listTree(query) {
  return request({
    url: '/account/attribute/listTree',
    method: 'get',
    params: query
  })
}
export function selectEquation(query) {
  return request({
    url: '/account/attribute/selectEquation',
    method: 'get',
    params: query
  })
}

// 查询游戏配置详细
export function getAttribute(id) {
  return request({
    url: '/account/attribute/' + id,
    method: 'get'
  })
}

// 新增游戏配置
export function addAttribute(data) {
  return request({
    url: '/account/attribute',
    method: 'post',
    data: data
  })
}

// 修改游戏配置
export function updateAttribute(data) {
  return request({
    url: '/account/attribute',
    method: 'put',
    data: data
  })
}

// 删除游戏配置
export function delAttribute(id) {
  return request({
    url: '/account/attribute/' + id,
    method: 'delete'
  })
}

// 导出游戏配置
export function exportAttribute(query) {
  return request({
    url: '/account/attribute/export',
    method: 'get',
    params: query
  })
}