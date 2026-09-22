import request from '@/utils/request'

let goodsListApi = {
  // 账号列表
  getGoodsList(data) {
    return request({
      url: '/goods/info/list',
      method: 'get',
      params: data
    })
  },

  // /goods/info/poollist 商户联盟 账号池列表
  getGoodsListPool(data) {
    return request({
      url: '/goods/info/poollist',
      method: 'get',
      params: data
    })
  },
  // /goods/info/poollist 商户联盟 我的账号列表
  myAccount(data) {
    return request({
      url: '/goods/info/myAccount',
      method: 'get',
      params: data
    })
  },
  getGoodsAllList(data) {
    return request({
      url: '/goods/info/allList',
      method: 'get',
      params: data
    })
  },
  // dange
  getGoodsDetail(id) {
    return request({
      url: `/goods/info/${id}`,
      method: 'get'
    })
  },
  // xinzeng
  addGoods(data) {
    return request({
      url: '/goods/info',
      method: 'post',
      data
    })
  },
  // 
  editGoods(data) {
    return request({
      url: '/goods/info',
      method: 'put',
      data
    })
  },
  // 删除商品
  delGoods(ids, pwd) {
    return request({
      url: `/goods/info/${ids}?pwd=${pwd}`,
      method: 'delete'
    })
  },
  //下架商品 
  downGoods(id) {
    return request({
      url: `/goods/info/downGoods/${id}`,
      method: 'get'
    })
  },
  //上架商品 
  upGoods(id) {
    return request({
      url: `/goods/info/upGoods/${id}`,
      method: 'get'
    })
  },
  //下架商品 
  downGoods(data) {
    return request({
      url: `/goods/info/downGoods`,
      method: 'post',
      data
    })
  },
  //转为问题号 
  problemGoods(data) {
    return request({
      url: `/goods/info/problemGoods`,
      method: 'post',
      data
    })
  },
  //转为找回账号
  retrieveGoods(data) {
    return request({
      url: `/goods/info/retrieveGoods`,
      method: 'post',
      data
    })
  },
  // 审核通过账号
  auditGoods(ids) {
    return request({
      url: `/goods/info/auditGoods/${ids}`,
      method: 'get'
    })
  },
  // 审核不通过账号
  auditGoodsFaile(data) {
    return request({
      url: `/goods/info/auditGoodsFailure`,
      method: 'post',
      data
    })
  },
  //goods/info/deleteAllAccount
  // 全部删除
  deleteAllAccount(data) {
    return request({
      url: `goods/info/deleteAllAccount`,
      method: 'get',
      params: data
    })
  },
  // 批量下架
  batchDown(ids) {
    return request({
      url: `/goods/info/down/${ids}`,
      method: 'get'
    })
  },
  // 销售端提号
  salePickUp(ids) {
    return request({
      url: `/goods/info/submitAccount/${ids}`,
      method: 'get'
    })
  },
  // 新增账号时推送渠道选择
  pushAllList() {
    return request({
      url: `/partner/partner/allList`,
      method: 'get'
    })
  },
  // 渠道分组列表
  pushGroupList() {
    return request({
      url: `/sale/group/list`,
      method: 'get'
    })
  },
  // 新增分组
  addPushGroup(data) {
    return request({
      url: `/sale/group`,
      method: 'post',
      data
    })
  },
  // 账号是否精品号
  topOnOrOff(data) {
    return request({
      url: '/goods/info/topOnOrOff',
      method: 'post',
      data
    })
  },

  //账号是否推送

  tuisongOff(data) {
    return request({
      url: '/goods/info/updateIsPush',
      method: 'post',
      data
    })
  },


  zhuanGuashou(id) {
    return request({
      url: `/goods/info/editStateGoods/${id}`,
      method: 'post'
    })
  },

  zhuanfenqi(id) {
    return request({
      url: `/goods/info/convertFQ/${id}`,
      method: 'post'
    })
  },
  zhuanconvertYFJ(id) {
    return request({
      url: `/goods/info/convertYFJ/${id}`,
      method: 'post'
    })
  },
  zhuanconvertYSX(id) {
    return request({
      url: `/goods/info/convertYSX/${id}`,
      method: 'post'
    })
  },
  zhuanconvertPFZ(id) {
    return request({
      url: `/goods/info/convertPFZ/${id}`,
      method: 'post'
    })
  },
  zhuanconvertYJCS(id) {
    return request({
      url: `/goods/info/convertYJCS/${id}`,
      method: 'post'
    })
  },
  zhuanconvertHSPF(id) {
    return request({
      url: `/goods/info/convertHSPF/${id}`,
      method: 'post'
    })
  },

  zhuanconvertZSFJ(id) {
    return request({
      url: `/goods/info/convertZSFJ/${id}`,
      method: 'post'
    })
  },



}
export default goodsListApi

