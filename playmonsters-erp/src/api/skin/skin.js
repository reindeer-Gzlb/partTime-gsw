import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listSkin(query) {
  return request({
    url: '/account/skin/list',
    method: 'get',
    params: query
  })
}
export function classTypeList(query) {
  return request({
    url: '/account/skin/classTypeList',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getSkin(id) {
  return request({
    url: '/account/skin/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addSkin(data) {
  return request({
    url: '/account/skin',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateSkin(data) {
  return request({
    url: '/account/skin',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delSkin(id) {
  return request({
    url: '/account/skin/' + id,
    method: 'delete'
  })
}

// 导出【请填写功能名称】
export function exportSkin(query) {
  return request({
    url: '/account/skin/export',
    method: 'get',
    params: query
  })
}
