import request from '@/utils/request'

// 查询游戏类型字段配置列表
export function listConfig(query) {
  return request({
    url: '/account/config/list',
    method: 'get',
    params: query
  })
}
export function labelType(query) {
  return request({
    url: '/account/config/labelType',
    method: 'get',
    params: query
  })
}

// 查询游戏类型字段配置详细
export function getConfig(id) {
  return request({
    url: '/account/config/' + id,
    method: 'get'
  })
}

// 新增游戏类型字段配置
export function addConfig(data) {
  return request({
    url: '/account/config',
    method: 'post',
    data: data
  })
}
// export function addConfigJson(data) {
//   return request({
//     url: '/account/config/addJson',
//     method: 'post',
//     data: data
//   })
// }

// // 修改游戏类型字段配置
export function updateConfig(data) {
  return request({
    url: '/account/config',
    method: 'put',
    data: data
  })
}
// export function updateConfig(data) {
//   return request({
//     url: '/account/config/editJson',
//     method: 'post',
//     data: data
//   })
// }

// 删除游戏类型字段配置
export function delConfig(id) {
  return request({
    url: '/account/config/' + id,
    method: 'delete'
  })
}

// 导出游戏类型字段配置
export function exportConfig(query) {
  return request({
    url: '/account/config/export',
    method: 'get',
    params: query
  })
}
