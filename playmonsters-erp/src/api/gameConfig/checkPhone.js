import request from '@/utils/request'

let checkPhoneApi = {
  // 
  getList(data) {
    return request({
      url: '/account/unbind/list',
      method: 'get',
      params:data
    })
  },
  // xinzeng
  add(data) {
    return request({
      url: '/account/unbind',
      method: 'post',
      data
    })
  },
  // 
  edit(data) {
    return request({
      url: '/account/unbind',
      method: 'put',
      data
    })
  },
  // 删除
  del(ids) {
    return request({
      url: `/account/unbind/${ids}`,
      method: 'delete'
    })
  },

}
export default checkPhoneApi

