import request from '@/utils/request'

let huishouApi = {
  //获取号商列表
  getHaoshangList(query) {
    return request({
      url: '/account/compensate/list',
      method: 'get',
      params:query
    })
  },
   // 新增号商
   addHaoshang(data) {
    return request({
      url: '/account/compensate',
      method: 'post',
      data: data
    })
  },
  // 编辑号商
  editHaoshang(data) {
    return request({
      url: '/account/compensate',
      method: 'put',
      data: data
    })
  },
  // 删除号商
  delHaoshang(ids) {
    return request({
      url: `/account/compensate/${ids}`,
      method: 'delete'
    })
  },

}
export default huishouApi

