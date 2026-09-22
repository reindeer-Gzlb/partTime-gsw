import HttpAxios from "@/api/axios";

const httpAxios = new HttpAxios({});
const obj = {
  // 客服验证
  checkAccount: function(str:any){
    return httpAxios.sendRequest(`/api/V2/H5/checkAccount/${str}`, {}, 'get', {})
  },
  // 客服列表
  getkefu: function(){
    return httpAxios.sendRequest(`/api/V2/H5/allQQlist`, {}, 'get', {})
  },
  // 特价商品
  getTejia: function(){
    return httpAxios.sendRequest(`/api/H5/V2/holdAccount?page=1&rows=8`, {}, 'get', {})
  },
  // 最新商品
  getNewList: function(){
    return httpAxios.sendRequest(`/api/H5/GetZHList?rows=8&page=1`, {}, 'get', {})
  },
  // 顶级账号
  getDingji: function(YXLX:any){
    return httpAxios.sendRequest(`/api/H5/GetZHList?rows=16&page=1&YXLX=${YXLX}&commonOdr=1`, {}, 'get', {})
  },
  // 介绍详情
  getNotices: function(type:any){
    return httpAxios.sendRequest(`/api/H5/V2/noticeDetails/${type}`, {}, 'get', {})
  },
  // 获取qq群
  getQQGrop: function(){
    return httpAxios.sendRequest(`/api/V2/H5/qqGroup`, {}, 'get', {})
  },
  // 发送验证码
  sendSms: function(phone:any){
    return httpAxios.sendRequest(`/api/H5/V2/sendSms?phone=${phone}`, {}, 'get', {})
  },
  // 登录
  login: function(data:object){
    return httpAxios.sendRequest(`/api/H5/V2/login`, data, 'get', {})
  },
  // 验证码登录
  yzmLogin: function(data:object){
    return httpAxios.sendRequest(`/api/H5/V2/Smslogin`, data, 'get', {})
  },
  // 注册
  register: function(data:object){
    return httpAxios.sendRequest(`/api/H5/V2/register`, data, 'post', {})
  },
  // 忘记密码
  SmsforPwd: function(data:object){
    return httpAxios.sendRequest(`/api/H5/V2/SmsforPwd`, data, 'post', {})
  },
  // 编辑用户
  updateZeuser: function(data:object){
    return httpAxios.sendRequest(`/api/account/zeuser`, data, 'put', {})
  },
  // 收藏商品
  addCollect: function(data:object){
    return httpAxios.sendRequest(`/api/account/collect`, data, 'post', {})
  },
   // 取消收藏
   delCollect: function(data:object){
    return httpAxios.sendRequest(`/api/account/collect/removeCollct`, data, 'post', {})
  },
   // 收藏列表
   listCollect: function(data:object){
    return httpAxios.sendRequest(`/api/account/collect/listAccount`, data, 'get', {})
  },
  // 新增问题反馈
  addProblem: function(data:object){
    return httpAxios.sendRequest(`/api/account/problem`, data, 'post', {})
  },
  // 通知公告 新闻 找回案例 列表
  getNoticeList: function(type:any){
    return httpAxios.sendRequest(`/api/V2/H5/gonggaoDetails?noticeType=${type}`, {}, 'get', {})
  },
  // 通知公告 新闻 找回案例详情
  getNoticeDetail: function(id:any){
    return httpAxios.sendRequest(`/api/system/notice/${id}`, {}, 'get', {})
  },
  // 首页获取轮播图
  getImgs: function(type:any){
    return httpAxios.sendRequest(`/api/account/zhubo/list?type=${type}`, {}, 'get', {})
  },

}
export default obj