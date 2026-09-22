import request from '@/utils/request'

let searchApi = {
  // 全站商品查询
  search(query) {
    return request({
      url: '/submitAccount/accountInfo',
      method: 'get',
      params: query
    })
  },
  // 提号资料提交
  submit(query) {
    return request({
      url: '/submitAccount/submitOrder',
      method: 'post',
      data:query
    })
  },
  // 修改信息
  editOrder(data) {
    return request({
      url: '/account/order',
      method: 'put',
      data: data
    })
  }
}

export default searchApi

