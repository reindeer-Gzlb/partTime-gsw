import request from '@/utils/request'

let autoApi = {

  // 获取游戏类型
  getGameTypes() {
    return request({
      url: '/H5/GetGameType',
      method: 'post',
      data: {}
    })
  },
  //获取游戏标签
  getGameTag(query) {
    return request({
      url: '/V2/H5/hotWord',
      method: 'get'
    })
  },

  //获取游戏列表
  getGameList(query) {
    return request({
      url: '/H5/GetZHList',
      method: 'get'
    })
  },

  //获取筛选列表
  getSearchList(query) {
    return request({
      url: '/V2/H5/screenList',
      method: 'get'
    })
  },

  //账号详情
  getGameAccountInfoByIdMH(query) {
    return request({
      url: '/H5/GetZHInfo',
      method: 'get'
    })
  },

  //获取属性列表
  attrTypeList(query) {
    return request({
      url: '/V2/H5/attrList',
      method: 'get',
      params:query
    })
  },

  //估值
  subValuation(query) {
    return request({
      url: '/V2/H5/valuation',
      method: 'post',
      data:query
    })
  },
  //获取角色列表
  getRoleList(query) {
    return request({
      url: '/V2/H5/roleList',
      method: 'get',
      params:query
    })
  },
  //获取角色详情
  getRoleInfo(query) {
    return request({
      url: '/V2/H5/gengerTitle',
      method: 'get',
      params:query
    })
  },
   //测试
   transferSearch(query) {
    return request({
      url: 'https://app.zhuanzhuan.com/zz/transfer/search',
      method: 'post',
      data:query,
      timeout:600000,
      headers:{
        cookie:`	
        tk=9bcff43d47665045125da3851778bc4e9ac44e9d;uid=131559885363068288;lon=115.8633984953723;idfa=FB8DB0A5-5240-410D-B282-8B34916BFD78;brand=Apple;PPU="TT=b14787b99422ee907cdcd457796640b233fe7b4b&UID=131559885363068288&CT=1680765236572&SF=ZHUANZHUAN&SCT=1680768836572&V=2&ET=1683357236572&AP=16"`,
        "user-agent":`zhuanzhuan/10.8.0 (iPhone; iOS 16.2; Scale/3.00)`
      }
    })
  },
  //根据多选的ids获取选项列表
  getSelectList(query) {
    return request({
      url: '/game/attribute/allAttr',
      method: 'post',
      data:query
    })
  },
  // 获取和平营地授权二维码
  /*/V2/H5/getQRcode 755716196
yingdi    营地 755716196
authType  1  qq  2、微信*/
getHpQRcode(query) {
  return request({
    url: '/V2/H5/getQRcode',
    method: 'get',
    params:query
  })
},
// 获取和平营地信息
getHPDatacode(query) {
  return request({
    url: '/V2/H5/getHPDatacode',
    method: 'get',
    params:query,
    timeout:'180000'
  })
},
// 查询自动获取剩余次数
residueNumber(query) {
  return request({
    url: '/V2/H5/residueNumber',
    method: 'get'
  })
},

}
export default autoApi

