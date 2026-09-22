import HttpAxios from "@/api/axios";

const httpAxios = new HttpAxios({});
const obj = {
  
  /*return request({
    url: '/upload/imageHead',
    method: 'post',
    data: file,
    timeout: 3000000000000000
  })*/
  // 上传
  postUpload: function(file:any,config:any){
    return httpAxios.sendRequest(`/api/upload/image`, file, 'post', config)
  },
  // 获取游戏类型
  getGameTypes() {
    return httpAxios.sendRequest(`/api/H5/GetGameType`, {}, 'post', {})
  },
  //获取游戏标签
  getGameTag() {
    return httpAxios.sendRequest(`/api/V2/H5/hotWord`, {}, 'get', {})

  },
  // /api/H5/GetGameType
//获取游戏类型
  getGameType() {
   return httpAxios.sendRequest(`/api/H5/GetGameType`, {}, 'post', {})
  },
  //获取游戏列表
  getGameList() {
    return httpAxios.sendRequest(`/api/H5/GetZHList`, {}, 'get', {})
  },

  //获取筛选列表
  getSearchList() {
    return httpAxios.sendRequest(`/api/V2/H5/screenList`, {}, 'get', {})
 
  },

  //账号详情
  getGameAccountInfoByIdMH() {
    return httpAxios.sendRequest(`/api/H5/GetZHInfo`, {}, 'get', {})
  
  },

  //获取属性列表
  attrTypeList(query:any) {
    return httpAxios.sendRequest(`/api/V2/H5/attrList`,query, 'get', {})
 
  },

  //估值
  subValuation(query:any) {
    return httpAxios.sendRequest(`/api/V2/H5/valuation`,query, 'get', {})
  
  },
  //获取角色列表
  getRoleList(query:any) {
    return httpAxios.sendRequest(`/api/V2/H5/roleList`,query, 'get', {})

  },
  //获取角色详情
  getRoleInfo(query:any) {
    return httpAxios.sendRequest(`/api/V2/H5/gengerTitle`,query, 'get', {})

  },

  // 公告列表
  getNoticeList() {
    return httpAxios.sendRequest(`/system/notice/list`,{}, 'get', {})
  }

}
export default obj