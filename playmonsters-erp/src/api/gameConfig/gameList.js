import request from '@/utils/request'

let gameListApi = {
  // 
  getGameList(data) {
    return request({
      url: '/game/info/list',
      method: 'get',
      params:data
    })
  },
  // dange
  getGameDetail(id) {
    return request({
      url: `/game/info/${id}`,
      method: 'get'
    })
  },
  // xinzeng
  addGame(data) {
    return request({
      url: '/game/info',
      method: 'post',
      data
    })
  },
  // 
  editGame(data) {
    return request({
      url: '/game/info',
      method: 'put',
      data
    })
  },
  // 删除
  delGame(ids) {
    return request({
      url: `/game/info/${ids} `,
      method: 'delete'
    })
  }

}
export default gameListApi

