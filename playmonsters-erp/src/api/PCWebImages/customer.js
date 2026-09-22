import request from '@/utils/request'

let userApi = {

  // 获取客户列表
  getList(query) {
    return request({
      url: '/account/zeuser/list',
      method: 'get',
      params:query
    })
  },
   // 修改
   editUser(data) {
    return request({
      url: '/account/zeuser',
      method: 'put',
      data: data
    })
  },

}
export default userApi

