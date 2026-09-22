import request from '@/utils/request'

let systemMsgApi = {

  // 获取消息列表
  getMsgList(query) {
    return request({
      url: '/system/messages/list',
      method: 'get',
      params: query
    })
  }
}
export default systemMsgApi

