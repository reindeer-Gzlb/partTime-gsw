import request from '@/utils/request'

// 查询销售伙伴列表
export function listPartner(query) {
  return request({
    url: '/partner/partner/list',
    method: 'get',
    params: query
  })
}
// 查询销售伙伴列表
export function allList(query) {
  return request({
    url: '/partner/partner/allList',
    method: 'get',
    params: query
  })
}

// 查询销售伙伴详细
export function getPartner(id) {
  return request({
    url: '/partner/partner/' + id,
    method: 'get'
  })
}

// 新增销售伙伴
export function addPartner(data) {
  return request({
    url: '/partner/partner',
    method: 'post',
    data: data
  })
}

// 修改销售伙伴
export function updatePartner(data) {
  return request({
    url: '/partner/partner',
    method: 'put',
    data: data
  })
}

// 删除销售伙伴
export function delPartner(id) {
  return request({
    url: '/partner/partner/' + id,
    method: 'delete'
  })
}

// 导出销售伙伴
export function exportPartner(query) {
  return request({
    url: '/partner/partner/export',
    method: 'get',
    params: query
  })
}


// 删除错误的记录 config
export function delInter(query) {
  return request({
    url: '/open/api/delInter',
    method: 'get',
    params: query
  })
}
// 删除推送过的账号 createId  configId saleStatus
export function delAccount(query) {
  return request({
    url: '/open/api/delAccount',
    method: 'get',
    params: query
  })
}
// 手动执行编辑删除任务池
export function execuDel(query) {
  return request({
    url: '/open/api/execuDel',
    method: 'get',
    params: query
  })
}
// 批量下架 createId  config
export function batchShelves(query) {
  return request({
    url: '/open/api/batchShelves',
    method: 'get',
    params: query
  })
}
// 批量编辑 createId  config
export function editBachthApi(query) {
  return request({
    url: '/open/api/editBachthApi',
    method: 'get',
    params: query
  })
}