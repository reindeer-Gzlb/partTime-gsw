import request from '@/utils/request'

let fankuiApi = {

  // 获取反馈列表
  getList(type) {
    return request({
      url: '/account/problem/list',
      method: 'get'
    })
  }
}
export default fankuiApi

