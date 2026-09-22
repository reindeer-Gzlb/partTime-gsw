import HttpAxios from "@/api/axios";
import { type } from "os";

const httpAxios = new HttpAxios({});
const orderApi = {
  // 下单test
  addOrder: function (data:object) {
    return httpAxios.sendRequest(`/api/pay/record/order`, data, 'post', {})
  },
  // 下单new
  addSaleOrderNew: function (data:object) {
    return httpAxios.sendRequest(`/api/account/order/rec/createOrder`, data, 'post', {})
  },
  // 查询订单详情
  getOrderDetail: function (id:any) {
    return httpAxios.sendRequest(`/api/need/myOrder/${id}`, {}, 'get', {})
  },
  // 查询订单详情
  getOrderDetailByNo: function (data:any) {
    return httpAxios.sendRequest(`/api/need/myOrder/byCenterNo`, data, 'get', {})
  },
  // 查询订单支付状态
  queryOrder: function (data:any) {
    return httpAxios.sendRequest(`/api/account/order/rec/queryOrder`, data, 'post', {})
  },
  // 取消订单
  cancelOrder:function (data:any) {
    return httpAxios.sendRequest(`/api/need/myOrder/cancel`, data, 'post', {})
  },
  // 轮询订单状态
  orderPayStatusInterval:function (data:any) {
    return httpAxios.sendRequest(`/api/account/order/rec/orderStatus`, data, 'get', {})
  },
  
}

export default orderApi