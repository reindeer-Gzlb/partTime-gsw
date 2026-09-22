import request from '@/utils/request'

let supplyConfigApi = {
  // 所有用户列表
  getAllUserList(data) {
    return request({
      url: '/system/user/allList',
      method: 'get',
      params:data
    })
  },
  // 列表
  getList(data) {
    return request({
      url: '/supply/info/list',
      method: 'get',
      params:data
    })
  },
  getDetail(id) {
    return request({
      url: `/supply/info/${id}`,
      method: 'get'
    })
  },
  // xinzeng
  add(data) {
    return request({
      url: `/supply/info`,
      method: 'post',
      data
    })
  },
  // bianji
  edit(data) {
    return request({
      url: '/supply/info',
      method: 'put',
      data
    })
  },
  // 删除
  del(ids) {
    return request({
      url: `/supply/info/${ids}`,
      method: 'delete'
    })
  },

  // 获取手机号校验的所有错误列表
  getErrList(data) {
    return request({
      url: `/account/err/info/list`,
      method: 'get',
      params:data
    })
  },
  // 删除错误信息
  delErr(ids) {
    return request({
      url: `/account/err/info/${ids}`,
      method: 'delete'
    })
  },

}
export default supplyConfigApi

