import request from '@/utils/request'

let orderApi = {
  // 订单列表
  getList(query) {
    return request({
      url: '/account/order/list',
      method: 'get',
      params: query
    })
  },
  // 换绑信息
  getDetail(id) {
    return request({
      url: `/account/order/${id}`,
      method: 'get'
    })
  },
  // 换绑信息2
  getDetail2(id) {
    return request({
      url: `/account/order/account/${id}`,
      method: 'get'
    })
  },
}

export default orderApi

