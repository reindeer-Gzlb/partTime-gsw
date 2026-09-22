import request from '@/utils/request'

let payApi = {
  // 支付记录列表
  getPayRecords(data) {
    return request({
      url: '/pay/record/list',
      method: 'get',
      params:data
    })
  },
  // 回收订单支付
  payHuishou(data) {
    return request({
        url: '/backOrder/payment',
        method: 'post',
        data
    })
  },
}
export default payApi

