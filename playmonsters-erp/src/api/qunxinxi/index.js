import request from '@/utils/request'

let qunxinxiApi = {

  // 获取列表
  getList(query) {
    return request({
      url: '/group/info/list',
      method: 'get',
      params:query
    })
  },
  // 恢复
  huifuId(id) {
    return request({
      url: `/group/info/recoveryGroupInfo/${id}`,
      method:'put'
    })
  },
  //编辑
  editWord(data) {
    return request({
      url: '/group/info/updateSettings' ,
      method: 'post',
      data
    })
  },
  //删除
  delWord(id) {
    return request({
      url: `/group/info/${id}` ,
      method: 'delete'
    })
  },
  //群信息
  qunxinxiList(params){
    return request({
      url:`/msg/history/searchGroupInfo`,
      method:'get',
      params
    })
  }
}
export default qunxinxiApi

