import request from '@/utils/request'

let phoneApi = {

  // 获取手机列表
  getList(query) {
    return request({
      url: '/account/tel/manage/list',
      method: 'get',
      params:query

    })
  },
  // 获取所有手机列表
  getAllList(query) {
    return request({
      url: '/account/tel/manage/allList',
      method: 'get',
      params:query

    })
  },
  //新增
  addPhone(data) {
    return request({
      url: '/account/tel/manage',
      method: 'post',
      data: data
    })
  },
  //编辑
  editPhone(data) {
    return request({
      url: '/account/tel/manage' ,
      method: 'put',
      data
    })
  }
}
export default phoneApi

