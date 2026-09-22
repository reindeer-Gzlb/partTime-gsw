import request from '@/utils/request'

let gameDictionaryApi = {
  // 
  getList(data) {
    return request({
      url: '/game/attribute/list',
      method: 'get',
      params:data
    })
  },
  // dange
  getDetail(id) {
    return request({
      url: `/game/attribute/${id}`,
      method: 'get'
    })
  },
  // xinzeng
  add(data) {
    return request({
      url: '/game/attribute',
      method: 'post',
      data
    })
  },
  // 
  edit(data) {
    return request({
      url: '/game/attribute',
      method: 'put',
      data
    })
  },
  // 删除
  del(ids) {
    return request({
      url: `/game/attribute/${ids}`,
      method: 'delete'
    })
  },
  // 子级属性列表
  getChildList(data) {
    return request({
      url: `/game/attribute/keylist`,
      method: 'get',
      params:data
    })
  }

}
export default gameDictionaryApi

