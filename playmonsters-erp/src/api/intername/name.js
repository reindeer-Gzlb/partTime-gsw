import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listName(query) {
  return request({
    url: '/inter/name/list',
    method: 'get',
    params: query
  })
}
export function allListName() {
  return request({
    url: '/inter/name/allList',
    method: 'get'
  })
}

// 查询【请填写功能名称】详细
export function getName(id) {
  return request({
    url: '/inter/name/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addName(data) {
  return request({
    url: '/inter/name',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateName(data) {
  return request({
    url: '/inter/name',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delName(id) {
  return request({
    url: '/inter/name/' + id,
    method: 'delete'
  })
}

// 导出【请填写功能名称】
export function exportName(query) {
  return request({
    url: '/account/name/export',
    method: 'get',
    params: query
  })
}
