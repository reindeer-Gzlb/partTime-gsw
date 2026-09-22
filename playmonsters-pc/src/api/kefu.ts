import HttpAxios from '@/api/axios';
import { type } from 'os';

const httpAxios = new HttpAxios({});
const obj = {
  // 客服验证
  checkAccount: function (str: any) {
    return httpAxios.sendRequest(`/api/V2/H5/checkAccount`, str, 'get', {});
  },
  // 黑号查询
  checkHeihao: function (data: object) {
    return httpAxios.sendRequest(`/api/black/blackList`, data, 'get', {});
  },
  // 客服列表
  getkefu: function () {
    return httpAxios.sendRequest(`/api/V2/H5/allQQlist`, {}, 'get', {});
  },
  // 特价商品
  // getTejia: function(){
  //   return httpAxios.sendRequest(`/api/H5/V2/holdAccount?page=1&rows=8`, {}, 'get', {})
  // },
  // 最新账号
  getDingji: function (data: object) {
    return httpAxios.sendRequest(`/api/web/good/allList`, data, 'post', {});
  },

  // 介绍详情
  getNotices: function (type: any) {
    return httpAxios.sendRequest(`/api/V2/H5/gonggaoDetails?noticeType=${type}`, {}, 'get', {});
  },
  // 获取qq群
  getQQGrop: function () {
    return httpAxios.sendRequest(`/api/V2/H5/qqGroup`, {}, 'get', {});
  },
  // 发送验证码
  // sendSms: function(phone:any){
  //   return httpAxios.sendRequest(`/api/H5/V2/sendSms?phone=${phone}`, {}, 'get', {})
  // },
  sendSms: function (obj: any) {
    return httpAxios.sendRequest(`/api/sms/sendYzm`, obj, 'get', {});
  },
  // 验证码登录
  loginByPhone: function (obj: any) {
    return httpAxios.sendRequest(`/api/h5/login`, obj, 'post', {});
  },
  // 获取验证码
  getCodeImg: function (phone: any) {
    return httpAxios.sendRequest(`/api/captchaImage`, {}, 'get', {});
  },

  // 登录
  login: function (data: object) {
    return httpAxios.sendRequest(`/api/login`, data, 'post', {});
  },
  // 验证码登录
  // yzmLogin: function(data:object){
  //   return httpAxios.sendRequest(`/api/H5/V2/Smslogin`, data, 'get', {})
  // },
  // 获取用户信息
  getUserInfo: function (data: object) {
    return httpAxios.sendRequest(`/api/system/user/profile`, data, 'get', {});
  },
  // 注册
  register: function (data: object) {
    return httpAxios.sendRequest(`/api/register`, data, 'post', {
      headers: { Token: '', Authorization: '' }
    });
  },
  // 忘记密码
  // SmsforPwd: function(data:object){
  //   return httpAxios.sendRequest(`/api/H5/V2/SmsforPwd`, data, 'post', {})
  // },
  // 编辑用户
  updateZeuser: function (data: object) {
    return httpAxios.sendRequest(`/api/system/user/profile`, data, 'put', {});
  },
  // 收藏商品
  addCollect: function (data: object) {
    return httpAxios.sendRequest(`/api/web/goods/collect`, data, 'post', {});
  },
  // 取消收藏
  delCollect: function (data: object) {
    return httpAxios.sendRequest(`/api/web/goods/collect/remove`, data, 'post', {});
  },
  // 收藏列表
  listCollect: function (data: object) {
    return httpAxios.sendRequest(`/api/web/goods/collect/list`, data, 'get', {});
  },

  // 新增问题反馈
  addProblem: function (data: object) {
    return httpAxios.sendRequest(`/api/account/problem`, data, 'post', {});
  },
  // 获取问题反馈列表
  getProblemList: function (id: any) {
    return httpAxios.sendRequest(`/api/account/problem/list`, id, 'get', {});
  },
  // 通知公告 新闻 找回案例 列表
  getNoticeList: function (type: any) {
    return httpAxios.sendRequest(`/api/V2/H5/gonggaoDetails?noticeType=${type}`, {}, 'get', {});
  },
  // 通知公告 新闻 找回案例详情
  getNoticeDetail: function (id: any) {
    return httpAxios.sendRequest(`/api/system/notice/${id}`, {}, 'get', {});
  },
  // 首页获取轮播图
  getImgs: function (type: any) {
    return httpAxios.sendRequest(`/api/account/zhubo/list?type=${type}`, {}, 'get', {});
  },

  // 获取推荐商品
  getRecommend: function (id: any) {
    return httpAxios.sendRequest(`/api/web/recommend/${id}`, {}, 'get', {});
  },

  // 公告列表
  getNoticeLists: function (noticeType: any) {
    return httpAxios.sendRequest(`/api/web/notice/list?noticeType=${noticeType}`, {}, 'get', {});
  },

  // 服务列表
  getFuwuLists: function (noticeType: any) {
    return httpAxios.sendRequest(`/api/web/notice/list?noticeType=${noticeType}`, {}, 'get', {});
  },

  // 公告列表详情
  getNoticeList_xq: function (noticeid: any) {
    return httpAxios.sendRequest(`/api/web/notice/${noticeid}`, {}, 'get', {});
  },
  // 客服列表
  getsecurity: function (data: object) {
    return httpAxios.sendRequest(`/api/V2/H5/account`, data, 'get', {});
  },
  // 获取商品列表
  getGoodsList: function (data: object) {
    return httpAxios.sendRequest(`/api/need/myConsignment`, data, 'get', {});
  },
  // 获取我的浏览
  getMyBrowse: function () {
    return httpAxios.sendRequest(`/api/need/myBrowse`, {}, 'get', {});
  },
  // 系统信息
  getSystemInfo: function () {
    return httpAxios.sendRequest(`/api/need/message/list`, {}, 'get', {});
  },
  // 信息详情
  getSystemInfoDetail: function (id: any) {
    return httpAxios.sendRequest(`/api/need/message/changeRead/${id}`, {}, 'get', {});
  },
  // 修改密码
  changePasswore: function (data: object) {
    return httpAxios.sendRequest(`/api/h5/SmsforPwd`, data, 'post', {});
  },

  // 获取系统列表
  getSystemList: function (data: object) {
    return httpAxios.sendRequest(
      `/api/web/keylist?gameId=${data.gameId}&systemKey=${data.systemKey}&parentId=${
        data.parentId ? data.parentId : ''
      }`,
      {},
      'get',
      {}
    );
  },
  // 获取运营商列表
  getOperatorList: function (data: object) {
    return httpAxios.sendRequest(`/api/need/operator/list`, data, 'get', {});
  },
  // 获取大区列表
  getAreaList: function (data: object) {
    return httpAxios.sendRequest(`/api/need/area/list`, data, 'get', {});
  },
  // 获取服务器列表
  getServerList: function (data: object) {
    return httpAxios.sendRequest(`/api/need/server/list`, data, 'get', {});
  },
  // 获取服务器列表
  getServerList2: function (data: object) {
    return httpAxios.sendRequest(
      `/api/web/districtSuit?parentId=${data.parentId ? data.parentId : ''}`,
      {},
      'get',
      {}
    );
  },
  // 获取我的评估
  getMyAssess: function (data: object) {
    return httpAxios.sendRequest(`/api/account/backlist/allList`, data, 'get', {});
  },
  // 我要卖
  getMySell: function (data: object) {
    return httpAxios.sendRequest(`/api/need/consignment`, data, 'post', {});
  },
  // 账号评估提交
  submitPinggu: function (data: object) {
    return httpAxios.sendRequest(`/api/account/backlist`, data, 'post', {});
  },
  // 商品详情——账号信息
  getAccountInfo: function (id: any) {
    return httpAxios.sendRequest(`/api/web/gameTempAttr?gameId=${id}`, {}, 'get', {});
  },
  // 上架商品
  getUpGoods: function (id: any) {
    return httpAxios.sendRequest(`/api/goods/info/upGoods/${id}`, {}, 'get', {});
  },
  // 下架商品
  getDownGoods: function (data: any) {
    return httpAxios.sendRequest(`/api/goods/info/downGoods`, data, 'post', {});
  },
  // 账号回收
  getAccountRecovery: function (type: any) {
    return httpAxios.sendRequest(`/api/web/notice/list?noticeType=${type}`, {}, 'get', {});
  },
  // 我的买单
  getMyBuy: function (obj: any) {
    return httpAxios.sendRequest(`/api/need/myOrder`, obj, 'get', {});
  },
  // 最近交易
  getRecentDeal: function (obj: any) {
    return httpAxios.sendRequest(`/api/web/recentlydeal`, obj, 'get', {});
  },
  // 身份认证
  getIdentity: function (obj: any) {
    return httpAxios.sendRequest(`/api/realname/auth `, obj, 'post', {});
  },
  // 获取官方交流群
  getGroup: function () {
    return httpAxios.sendRequest(`/api/V2/H5/qqGroup`, {}, 'get', {});
  },
  // 获取抖音快手
  getDyks: function () {
    return httpAxios.sendRequest(`/api/web/social/list`, {}, 'get', {});
  },
  /**
   * 获取交易须知详情
   */
  getdetails: function (noticeType: any) {
    return httpAxios.sendRequest(
      `/api/V2/H5/gonggaoDetails?noticeType=${noticeType}`,
      {},
      'get',
      {}
    );
  },
  getdetailywjs: function (noticeType: any) {
    return httpAxios.sendRequest(
      `/api/V2/H5/gonggaoDetails?noticeType=${noticeType}`,
      {},
      'get',
      {}
    );
  },
  //我要卖官方客服
  getdetailwym: function (noticeType: any) {
    return httpAxios.sendRequest(
      `/api/V2/H5/gonggaoDetails?noticeType=${noticeType}`,
      {},
      'get',
      {}
    );
  },
  //我要卖代售客服
  getdetaildskf: function (noticeType: any) {
    return httpAxios.sendRequest(
      `/api/V2/H5/gonggaoDetails?noticeType=${noticeType}`,
      {},
      'get',
      {}
    );
  },
  //获取客服二维码
  getkefuer: function (data: any) {
    return httpAxios.sendRequest(`/api/V2/H5/account`, data, 'get', {});
  },
  //修改上架价格
  postprice: function (data: any) {
    return httpAxios.sendRequest(`/api/need/goods/editPrice`, data, 'post', {});
  }
};

export default obj;
