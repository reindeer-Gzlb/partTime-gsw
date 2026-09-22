import request from '@/utils/request'

let shimingApi = {
  // 实名查询
  // check(data){
  //   return request({
  //     url: '/operatorTwoElements',
  //     method: 'post',
  //     data
  //   })
  // },
  check(data) {
    return request({
      url: '/checkRealName/checkPhoneAndRealName',
      method: 'post',
      data
    })
  },
  // 列表
  // getList(data) {
  //   return request({
  //     url: '/operatorTwoElements/list',
  //     method: 'get',
  //     params: data
  //   })
  // }

  getList(data) {
    return request({
      url: '/check/real/list',
      method: 'get',
      params: data
    })
  }

}
export default shimingApi

