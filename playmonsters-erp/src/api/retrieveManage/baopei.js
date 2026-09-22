import request from '@/utils/request'

let baopeiApi = {
  // 获取我的包赔列表
  getList(query) {
    return request({
      url: '/account/krecycle/bplist',
      method: 'get',
      params:query
    })
  },
  // 通过竞价编号查询回收信息  
  getHuishouByBackid(backId) {
    return request({
      url: `/account/krecycle/getBackList/${backId}`,
      method: 'get'
    })
  },
  // 通过竞价编号查询包赔信息  
  getBaopeiByBackid(backId) {
    return request({
      url: `/account/krecycle/getcompensate/${backId}`,
      method: 'get'
    })
  }

}
export default baopeiApi

