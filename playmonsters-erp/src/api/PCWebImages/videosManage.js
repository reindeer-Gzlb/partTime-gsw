import request from '@/utils/request'

let videoManageApi = {

  // 获取视频列表
  getList(query) {
    return request({
      url: '/index/resources/list',
      method: 'get',
      params:query
    })
  },
  // 新增
  add(data) {
    return request({
      url: '/index/resources',
      method: 'post',
      data: data
    })
  },
   // 修改
   edit(data) {
    return request({
      url: '/index/resources',
      method: 'put',
      data: data
    })
  },
  // 删除
  del(ids) {
    return request({
      url: `/index/resources/${ids}`,
      method: 'delete'
    })
  },

}
export default videoManageApi

