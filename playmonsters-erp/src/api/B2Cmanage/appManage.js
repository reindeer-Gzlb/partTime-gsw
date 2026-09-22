import request from '@/utils/request'

let appApi = {

  // 获取app列表
  getList(data) {
    return request({
      url: `/app/version/list`,
      method: 'get',
      params:data
    })
  },
  // 新增
  add(data){
    return request({
      url: '/app/version',
      method: 'post',
      data
    })
  },
  // 删除
  del(id){
    return request({
      url: `/app/version/${id}`,
      method: 'delete'
    })
  },

}
export default appApi

