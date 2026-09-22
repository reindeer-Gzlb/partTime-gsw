// 系统内各状态map的集合  统一配置
let obj = {
  // 支付方式 1：微信 2：支付宝
  payWayMap: {
    1: "微信",
    2: "支付宝",
  },
  // 回收订单状态 * 订单状态 1交易中 2待审核 3已审核 4交易完成 5交易失败
    retrieveOrderStatusMap: {
     
      1: {
        label: "交易中",
        type: "warn",
      },
      2: {
        label: "待审核",
        type: "warn",
      },
      3: {
        label: "已审核",
        type: "",
      },
      4: {
        label: "交易完成",
        type: "success",
      },
      5: {
        label: "交易失败",
        type: "danger",
      },
    },
  // 报价状态
  /**     0 、待报价
     4、已取消
     2、已接受
     3、已完成
     5、拒绝报价
 */
  bstatusMap:{
    0: {
      label: "报价中",
      type: "warning",
    },
    2: {
      label: "已接受",
      type: "success",
    },
    // 2: {
    //   label: "已拒绝",
    //   type: "danger",
    // },
    3: {
      label: "已完成",
      type: "success",
    },
    4: {
      label: "已取消",
      type: "danger",
    },
    5: {
      label: "拒绝报价",
      type: "danger",
    },
  },
  // 客服端 报价状态
  kefubstatusMap:{
    0: {
      label: "待接受",
      type: "warning",
    },
    1: {
      label: "已中标",
      type: "success",
    },
    2: {
      label: "未中标",
      type: "danger",
    },
    3: {
      label: "已完成",
      type: "success",
    },
    4: {
      label: "取消交易",
      type: "danger",
    },
    5: {
      label: "商家拒绝",
      type: "danger",
    },
  },
  yajinMap:{
    1: '押金已缴纳',
    2: '押金已退',
    3: '押金不退',
  },
  // 订单买家支付状态 1未支付 2已支付
	buyerPayState: {
		'1': '未支付',
		'2': '已支付'
	},
  isReturnMap:{
    1: {
      label: "已退",
      type: "success",
    },
    2: {
      label: "不退",
      type: "danger",
    },
  },
};
export default obj;
