import request from '@/utils/request'

let homeApi = {
  // 公告列表
  getNoticeList(data) {
    return request({
      url: '/system/notice/list',
      method: 'get',
      params:data
    })
  },
  // 供货商出号统计
  subOrder(data) {
    return request({
      url: '/indexData/count/subOrder',
      method: 'get',
      params:data
    })
  },
  // 销售卖号统计
  sellOrder(data) {
    return request({
      url: '/indexData/count/sellOrder',
      method: 'get',
      params:data
    })
  },
  // 供货商账号统计
  userGameCount(data) {
    return request({
      url: '/indexData/count/userGameCount',
      method: 'get',
      params:data
    })
  },
  // 当前任务统计
  taskCount(data) {
    return request({
      url: '/indexData/count/taskCount',
      method: 'get',
      params:data
    })
  },
  // 游戏类型账号统计
  GameTypeCount(data) {
    return request({
      url: '/indexData/count/GameTypeCount',
      method: 'get',
      params:data
    })
  },
}
export default homeApi

