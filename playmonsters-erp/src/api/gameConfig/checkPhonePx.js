import request from '@/utils/request'

let checkPhoneApi = {
  // 
  getList(data) {
    return request({
      url: '/account/monitoring/list',
      method: 'get',
      params:data
    })
  },
  // xinzeng
  add(data) {
    return request({
      url: '/account/monitoring',
      method: 'post',
      data
    })
  },
  // 
  // edit(data) {
  //   return request({
  //     url: '/account/unbind',
  //     method: 'put',
  //     data
  //   })
  // },
  // 删除
  del(ids) {
    return request({
      url: `/account/monitoring/${ids}`,
      method: 'delete'
    })
  },
  // 上架
  up(ids) {
    return request({
      url: `/account/monitoring/up/${ids}`,
      method: 'get'
    })
  },
  // 下架
  down(ids) {
    return request({
      url: `/account/monitoring/down/${ids}`,
      method: 'get'
    })
  },
  // 查询结果
  query(ids) {
    return request({
      url: `/account/monitoring/query/${ids}`,
      method: 'get'
    })
  },
  // 查询任务的记录列表
  queryrecords(data) {
    return request({
      url: `/account/monitoring/query/list`,
      method: 'get',
      params:data
    })
  },

}
export default checkPhoneApi

