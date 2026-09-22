import request from '@/utils/request'

let retrieveApi = {
  // 获取列表
  getList(query) {
    return request({
      url: '/account/jymdata/list',
      method: 'get',
      params:query
    })
  },

  // 查询链接
  getLink(id) {
    return request({
      url: `/account/jymdata/${id}`,
      method: 'get'
    })
  },
  //获取游戏标签
  getGameTag(query) {
    return request({
      url: '/V2/H5/hotWord',
      method: 'get'
    })
  },
  //获取全部渠道列表
  getAllChannel() {
    return request({
      url: '/account/jymdata/allChannel',
      method: 'get'
    })
  }


}
export default retrieveApi

