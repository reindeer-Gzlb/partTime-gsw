import request from '@/utils/request'

let userApi = {

  // 获取收藏列表
  listAccount(userId) {
    return request({
      url: '/account/collect/listAccount?userId='+userId,
      method: 'get'
    })
  },
  // 商户申请列表
  auditList(data) {
    return request({
      url: '/customer/audit/list',
      method: 'get',
      params:data

    })
  },
  // 查看商户申请详情
  getApplyDetail(id) {
    return request({
      url: `/customer/audit/${id}`,
      method: 'get'
    })
  },
  // 审核通过
  applyPass(data) {
    return request({
      url: '/customer/audit/pass',
      method: 'post',
      data

    })
  },
  // 审核拒绝
  applyRefuse(data) {
    return request({
      url: '/customer/audit/refuse',
      method: 'post',
      data

    })
  },

  // // 获取所有用户
  // allUserList() {
  //   return request({
  //     url: '/system/user/allList',
  //     method: 'get'

  //   })
  // },


}
export default userApi

