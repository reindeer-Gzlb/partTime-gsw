import request from '@/utils/request'

let gameConfigApi = {
  // 获取游戏配置 gameId
  getGameConfig(data) {
    return request({
      url: '/goods/gameattrtemplate/list',
      method: 'get',
      params:data
    })
  },
  // 新增游戏配置
  addGameConfig(data) {
    return request({
      url: '/goods/gameattrtemplate',
      method: 'post',
      data
    })
  },
  // 修改游戏配置
  editGameConfig(data) {
    return request({
      url: '/goods/gameattrtemplate',
      method: 'put',
      data
    })
  },
  // 获取游戏属性列表   type="王者荣耀" parentId=0
  getAttrList(data) {
    return request({
      url: '/game/attribute/list',
      method: 'get',
      params:data
    })
  },

}
export default gameConfigApi

