import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listPush(query) {
  return request({
    url: '/account/push/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getPush(id) {
  return request({
    url: '/account/push/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addPush(data) {
  return request({
    url: '/account/push',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updatePush(data) {
  return request({
    url: '/account/push',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delPush(id) {
  return request({
    url: '/account/push/' + id,
    method: 'delete'
  })
}

// 导出【请填写功能名称】
export function exportPush(query) {
  return request({
    url: '/account/push/export',
    method: 'get',
    params: query
  })
}




// 查询重推列表
export function repushList(query) {
  return request({
    url: '/account/up/list',
    method: 'get',
    params: query
  })
}

// 新增【请填写功能名称】
export function addRepush(data) {
  return request({
    url: '/account/up',
    method: 'post',
    data: data
  })
}

// 编辑
export function editRepush(data) {
  return request({
    url: '/account/up',
    method: 'put',
    data: data
  })
}
// 删除
export function delRepush(ids) {
  return request({
    url: `/account/up/${ids}`,
    method: 'delete'
  })
}