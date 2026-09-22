import request from '@/utils/request'

let imageApi = {

  // 获取图片列表
  getList(type) {
    return request({
      url: '/account/zhubo/list?type='+type,
      method: 'get'
    })
  },
  //新增图片
  addImage(data) {
    return request({
      url: '/account/zhubo',
      method: 'post',
      data: data
    })
  },
  //删除图片
  delImage(id) {
    return request({
      url: '/account/zhubo/' + id,
      method: 'delete'
    })
  },
  //修改
  editImage(data) {
    return request({
      url: '/account/zhubo',
      method: 'put',
      data: data
    })
  }
}
export default imageApi

