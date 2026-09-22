import request from '@/utils/request'

let orderApi = {

  // 获取订单列表
  getOrderList(query) {
    return request({
      url: '/account/submit/price/list?pageNum='+query.pageNum+'&pageSize='+query.pageSize,
      method: 'get'
    })
  },
  // 编辑
  editOrder(data){
    return request({
      url: '/account/submit/price',
      method: 'put',
      data
    })
  }

}
export default orderApi

