import request from '@/utils/request'

// 查询游戏账号列表
export function listInfo(query) {
  return request({
    url: '/system/info/list',
    method: 'get',
    params: query
  })
}
// 查询游戏账号列表
export function deleteAllAccount(query) {
  return request({
    url: '/system/info/deleteAllAccount',
    method: 'get',
    params: query,
    timeout: 200000000
  })
}
//账号池
export function poollist(query) {
  return request({
    url: '/system/info/poollist',
    method: 'get',
    params: query
  })
}

//图片
export function getImage(query) {
  return request({
    url: '/system/info/getImage',
    method: 'get',
    params: query
  })
}

//终端账号池
export function cmdPoollist(query) {
  return request({
    url: '/system/info/cmdPoollist',
    method: 'get',
    params: query
  })
}

// 查询游戏账号详细
export function getInfo(id) {
  return request({
    url: '/system/info/' + id,
    method: 'get'
  })
}
export function jymPush(query) {
  return request({
    url: '/system/info/jymPush',
    method: 'get',
    params: query
  })
}

// 新增游戏账号
export function addInfo(data) {
  return request({
    url: '/system/info',
    method: 'post',
    data: data
  })
}

// 修改游戏账号
export function updateInfo(data) {
  return request({
    url: '/system/info',
    method: 'put',
    data: data
  })
}

// 删除游戏账号
export function delInfo(id) {
  return request({
    url: '/system/info/' + id,
    method: 'delete',
    timeout: 200000000
  })
}

export function auditInfo(id, data) {
  return request({
    url: '/system/info/' + id,
    method: 'put',
    data: data
  })
}
export function synchronousAcount(data) {
  return request({
    url: '/system/info/synchronousAcount',
    method: 'post',
    data: data
  })
}

// 导出游戏账号
export function exportInfo(query) {
  return request({
    url: '/system/info/export',
    method: 'get',
    params: query,
    timeout: 200000000
  })
}
//获取所有
export function allSale() {
  return request({
    url: '/system/info/allSale',
    method: 'get'
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: '/system/info/importTemplate',
    method: 'get'
  })
}
// 下载用户导入模板2
export function importTemplateHp() {
  return request({
    url: '/system/info/importTemplateHp',
    method: 'get'
  })
}
// 下载用户导入模板3
export function importTemplateCommon() {
  return request({
    url: '/system/info/importTemplateCommon',
    method: 'get'
  })
}
//供货商列表
export function getSupplyList(roleKey) {
  return request({
    url: '/system/user/getUserByRoleCode/' + roleKey,
    method: 'get'
  })
}
//获取终端供货商
export function getCmdSupplyList() {
  return request({
    url: '/system/info/supplyIdList',
    method: 'get'
  })
}
export function byColums(colum, type) {
  return request({
    url: '/system/info/byColums/' + colum + '/' + type,
    method: 'get'
  })
}
export function bacthAddAccount(query) {
  return request({
    url: '/system/info/bacthAddAccount',
    method: 'get',
    params: query,
    timeout: 2000000000
  })
}
export function delAccount(query) {
  return request({
    url: '/system/info/delAccount',
    method: 'get',
    params: query
  })
}

// 添加为我的账号
export function pushAcount(id) {
  return request({
    url: '/out/outAdd/' + id,
    method: 'get'
  })
}
export function onePushAcount(id) {
  return request({
    url: '/out/outOneKey',
    method: 'get',
    timeout: 200000000000000
  })
}
export function delAcount(id, configId) {
  return request({
    url: '/out/outDel/' + id + '/' + configId,
    method: 'get'
  })
}
export function noAddRetailSuppply() {
  return request({
    url: '/account/rprice/noAddRetailSuppply',
    method: 'get'
  })
}
export function disrank() {
  return request({
    url: '/account/rprice/disrank',
    method: 'get'
  })
}
export function polish() {
  return request({
    url: '/system/info/polish',
    method: 'get'
  })
}
// 查询擦亮按钮的状态
export function ifPolish() {
  return request({
    url: '/system/info/ifPolish',
    method: 'get'
  })
}
export function upAccount(id) {
  return request({
    url: `system/info/upAccount/${id}`,
    method: 'get'
  })
}
export function getGameList(id) {
  return request({
    url: `account/account/getGameType`,
    method: 'get'
  })
}

// 添加账号弹窗，获取自定义字段
export function getAttrByType(type) {
  return request({
    url: `/account/attr/getAttrByType`,
    method: 'get',
    params: { type: type }
  })
}

// 获取推送接口列表
export function getPushList(data) {
  return request({
    url: `/partner/partner/allList`,
    method: 'get',
    params: data
  })
}

// 推送
export function push(data) {
  return request({
    url: `/out/outAdd`,
    method: 'get',
    params: data
  })
}
// 上传图片
export function upLoad(data) {
  return request({
    url: `/upload/image`,
    method: 'post',
    data
  })
}
// 获取接口配置的供货商 config
export function getAllSupliar(data) {
  return request({
    url: `/account/push/getAllSupliar`,
    method: 'get',
    params: data
  })
}
// 编辑接口配置的供货商
/**id  主键id
creates   勾选的供货人ids */
export function updateAupliar(data) {
  return request({
    url: `/account/push/updateAupliar`,
    method: 'post',
    data
  })
}

// 获取该供货商分配的接口渠道 createId
export function selectAllConfigBySup(data) {
  return request({
    url: `/account/push/selectAllConfigBySup`,
    method: 'get',
    params: data
  })
}
// 编辑编辑上货渠道
/**createId  供货商ID
configs  商户渠道ids */
export function editConfigBySup(data) {
  return request({
    url: `/account/push/editConfigBySup`,
    method: 'post',
    data
  })
}

// 
export function getMorePrice(data) {
  return request({
    url: `/account/prcie/list`,
    method: 'get',
    params: data
  })
}
// 查询推送给三方的三方id
export function getOutIds(id) {
  return request({
    url: `/goods/info/outId/${id}`,
    method: 'get'
  })
}
//获取供货客服手机号
export function getSupPhoneNumber(data) {
  return request({
    url: `/system/user/getTel`,
    method: 'get',
    params: data
  })
}

//设置特价账号
export function getSupTePriceAccount(data) {
  return request({
    url: `/goods/info/isOnSpecialOffer`,
    method: 'put',
    data
  })
}

