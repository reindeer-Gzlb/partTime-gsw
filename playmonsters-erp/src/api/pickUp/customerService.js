import request from '@/utils/request'

let customerServiceApi = {
  // 订单列表
  getList(query) {
    return request({
      url: '/account/order/list',
      method: 'get',
      params: query
    })
  },
  // 取消交易
  cancelOrder(query) {
    return request({
      url: '/submitAccount/cancelOrder',
      method: 'get',
      params: query
    })
  },
  // return request({
  //   url: '/account/order/rec',
  //   method: 'post',
  //   data: data
  // })
  // 下单
  addRec(data) {
    return request({
      url: '/account/order/rec/addTwo',
      method: 'post',
      data: data
    })
  },
  // 我的账号页面转自售
  selfSale(data) {
    return request({
      url: '/account/order/rec/selfSale',
      method: 'post',
      data: data
    })
  },
  //转分期
  zhuanfenqiSale(data) {
    return request({
      url: '/account/order/rec/selfSaleFQ',
      method: 'post',
      data: data
    })
  },
}

export default customerServiceApi

