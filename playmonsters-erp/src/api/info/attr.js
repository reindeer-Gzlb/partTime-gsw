import request from '@/utils/request'

// 查询游戏属性列表
export function listAttr(query) {
  return request({
    url: '/account/attr/list',
    method: 'get',
    params: query
  })
}

// 查询游戏属性详细
export function getAttr(id) {
  return request({
    url: '/account/attr/' + id,
    method: 'get'
  })
}

// 新增游戏属性
export function addAttr(data) {
  return request({
    url: '/account/attr',
    method: 'post',
    data: data
  })
}

// 修改游戏属性
export function updateAttr(data) {
  return request({
    url: '/account/attr',
    method: 'put',
    data: data
  })
}

// 删除游戏属性
export function delAttr(id) {
  return request({
    url: '/account/attr/' + id,
    method: 'delete'
  })
}

// 导出游戏属性
export function exportAttr(query) {
  return request({
    url: '/account/attr/export',
    method: 'get',
    params: query
  })
}
