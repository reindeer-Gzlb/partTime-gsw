import HttpAxios from "@/api/axios";
import { type } from "os";

const httpAxios = new HttpAxios({});
const obj = {
  // 下单test
  pay: function (data:object) {
    return httpAxios.sendRequest(`/api/pay/unify`, data, 'post', {})
  },
  // 调起支付
  goPay: function (data:object) {
    return httpAxios.sendRequest(`/api/pay/record/payment`, data, 'post', {})
  },
   // 调起支付new
   goPay2: function (data:object) {
    return httpAxios.sendRequest(`/api/pay/record/paymentTwo`, data, 'post', {})
  },
  // 调起支付回收押金
  backlistPayment: function (data:object) {
    return httpAxios.sendRequest(`/api/account/backlist/payment`, data, 'post', {})
  },
}

export default obj