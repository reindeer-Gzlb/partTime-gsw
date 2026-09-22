import HttpAxios from '@/api/axios';

const httpAxios = new HttpAxios({});
const obj = {
  // 提交竞价回收
  addGoodsBack(data: object) {
    return httpAxios.sendRequest(`/api/h5/goods/back`, data, 'post', {});
  },
  //回收订单列表 新
  getBackOrderList(data: object) {
    return httpAxios.sendRequest(`/api/backOrder/list`, data, 'get', {});
  },
  //  支付押金后查询结果
  backlistQueryOrder(data: object) {
    return httpAxios.sendRequest(`/api/account/backlist/queryOrder`, data, 'post', {});
  },
  //  支付押金后 轮询支付结果
  backlistPayStatusIntervel(data: object) {
    return httpAxios.sendRequest(`/api/account/backlist/backlistPayStatus`, data, 'get', {});
  },
  // 我的回收-获取报价列表
  getPriceList: function (data: object) {
    return httpAxios.sendRequest(`/api/account/quotation/mybprice`, data, 'get', {})
  },
    // 获取字典
  getDict: function(key:string){
    return httpAxios.sendRequest(`/api/system/dict/data/type/${key}`, {}, 'get', {})
  },
  // 接收报价
  backlistAccept: function(data:object){
    return httpAxios.sendRequest(`/api/account/backlist/accept`, data, 'post', {})
  },
};

export default obj;
