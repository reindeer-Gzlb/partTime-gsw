import request from '@/utils/request'

let shipinApi = {

  // 获取app列表
  getList(data) {
    return request({
      url: `/social/info/list`,
      method: 'get',
      params:data
    })
  },
  // 新增
  add(data){
    return request({
      url: '/social/info',
      method: 'post',
      data
    })
  },
  // 编辑
  edit(data){
    return request({
      url: '/social/info',
      method: 'put',
      data
    })
  },
  // 获取详情
  getDetail(id){
    return request({
      url: `/social/info/${id}`,
      method: 'get'
    })
  },
  // 删除
  del(id){
    return request({
      url: `/social/info/${id}`,
      method: 'delete'
    })
  },

}
export default shipinApi

