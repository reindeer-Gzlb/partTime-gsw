import request from '@/utils/request'

let quickwordApi = {

  // 获取列表
  getList(query) {
    return request({
      url: '/account/reply/list',
      method: 'get',
      params:query

    })
  },
  //新增
  addWord(data) {
    return request({
      url: '/account/reply',
      method: 'post',
      data: data
    })
  },
  //编辑
  editWord(data) {
    return request({
      url: '/account/reply' ,
      method: 'put',
      data
    })
  },
  //删除
  delWord(id) {
    return request({
      url: '/account/reply/'+id ,
      method: 'delete'
    })
  },
}
export default quickwordApi

