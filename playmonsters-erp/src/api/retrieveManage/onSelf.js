import request from '@/utils/request'

let onSelfApi = {
  // 获取待报价列表
  getList(query) {
    return request({
      url: '/account/backlist/list',
      method: 'get',
      params:query
    })
  },
  // 获取已报价列表
  getQuotationList(query) {
    return request({
      url: '/account/quotation/list',
      method: 'get',
      params:query
    })
  },

  // 获取列表 我的回收
  getListMy(query) {
    return request({
      url: '/account/backlist/myList',
      method: 'get',
      params:query
    })
  },
  // 获取已报价列表 我的回收
  getQuotationListMy(query) {
    return request({
      url: '/account/quotation/myList?pageNum='+query.pageNum+'&pageSize='+query.pageSize+'&type='+query.type+'&des='+query.des,
      method: 'get'
    })
  },



  // 客服端列表
  krecycleList(query) {
    return request({
      url: '/account/krecycle/list',
      method: 'get',
      params:query
    })
  },
   // 客服端编辑
   krecycleEdit(data) {
    return request({
      url: '/account/krecycle',
      method: 'put',
      data
    })
  },


  // /account/backlist/{id}  查看手机号和账号

  getPhone(id) {
    return request({
      url: `/account/backlist/${id}`,
      method: 'get'
    })
  },
  //提交报价
  quotation(data) {
    return request({
      url: '/account/quotation',
      method: 'post',
      data
    })
  },
  //编辑回收数据类型   1我的回收 2回收联盟 transfer
  transition(data) {
    return request({
      url: '/account/backlist',
      method: 'put',
      data
    })
  },
  // 商家专属回收  -根据竞价编号获取商品表信息
  getGoodsInfo(data){
    return request({
      url: '/account/backlist/getAccountInfo',
      method: 'get',
      params:data
    })
  },
  // 商家专属回收  -获取包赔信息
  getBaopeiInfo(id){
    return request({
      url: `/account/backCompensate/${id}`,
      method: 'get'
    })
  },
  // 商家专属回收  -新增包赔信息
  addBaopeiInfo(data){
    return request({
      url: `/V2/H5/backCompensate`,
      method: 'put',
      data
    })
  },
  // 商家专属回收  -编辑包赔信息
  editBaopeiInfo(data){
    return request({
      url: `/account/backCompensate`,
      method: 'put',
      data
    })
  },
   // 商家专属回收  -编辑单个回收信息
   editHuishou(data){
    return request({
      url: `/account/backlist`,
      method: 'put',
      data
    })
  },
   // 商家专属回收  -一键发布
   yjfb(data){
    return request({
      url: `/account/backlist/submitGoodsInfo`,
      method: 'put',
      data
    })
  },

  // 商家专属回收报价
  Shangjiabaojia(data) {
    return request({
      url: '/account/backlist/editInfo',
      method: 'put',
      data
    })
  },
  // 黑号查询
  heihaoCheck(data) {
		return request({
			url: `/black/blackList?game_sn=${data.game_sn}&content=${data.content}`,
			method: 'get'
		})
	},

  // 客服一键拉群
  createGroupRoom(data){
    return request({
			url: `/account/backlist/createPublicGroup?id=${data.backId}`,
			method: 'get'
		})
  },

  // 客服拉包赔进群
  pullBaopeiGroup(data){
    return request({
			url: `/account/backlist/addGroupMember?id=${data.id}&cpsId=${data.cpsId}`,
			method: 'get'
		})
  },

   // 提醒用户打开聊天页面
   quotationNotify(id){
    return request({
			url: `/account/quotation/notify/${id} `,
			method: 'get'
		})
  },

  // 我的回收-拒收
   refauseMyOrder(data){
    return request({
			url: `/account/backlist/refuse`,
			method: 'get',
      params:data
		})
  },
  // 回收联盟-拒收
  refauseLianmengOrder(data){
    return request({
			url: `/account/quotation/refuse`,
			method: 'get',
      params:data
		})
  },

  // // 回收订单-导出 /account/krecycle/export
  exportHuishouOrder(data){
    return request({
			url: `/account/krecycle/export`,
			method: 'get',
      params:data
		})
  },
  // k客服查看报价列表
  kefuViewPrices(data){
    return request({
			url: `account/quotation/list`,
			method: 'get',
      params:data
		})
  },
  // 客服在回收订单改备注
  updateRemake(data){
    return request({
			url: `account/krecycle/updateRemake`,
			method: 'post',
      data
		})
  },

  // 客服端回收记录列表
  kfList(data){
    return request({
			url: `/account/backlist/kfList`,
			method: 'get',
      params:data
		})
  },
  // 客服端通过回收记录查询报价列表
  quotationkfList(data){
    return request({
			url: `/account/quotation/kfList`,
			method: 'get',
      params:data
		})
  },
  // 客服端 回收订单管理
  // 回收订单列表
  getBackorderList(data){
    return request({
			url: `/backOrder/list`,
			method: 'get',
      params:data
		})
  },
    // 手动生成回收订单
    createOrder(data){
      return request({
        url: `/backOrder/create`,
        method: 'post',
        data
      })
    },
     // 一键拉群
     pullGroup(data){
      return request({
        url: `/backOrder/pullGroup`,
        method: 'post',
        data
      })
    },
     // 取消群组
     cancelGroup(data){
      return request({
        url: `/backOrder/cancelGroup`,
        method: 'post',
        data
      })
    },
     // 恢复群组
     recoveryGroup(data){
      return request({
        url: `/backOrder/recoveryGroup`,
        method: 'post',
        data
      })
    },
     // 回收订单改价
    backOrderChangeAmount(data){
      return request({
        url: `/backOrder/changeAmount`,
        method: 'post',
        data
      })
    },
     // 回收订单完成
     backOrderFinishOrder(data){
      return request({
        url: `/backOrder/finishOrder`,
        method: 'post',
        data
      })
    },
    //客服取消回收记录
      backlistCancel(data) {
        return request({
          url: `/account/backlist/cancel`,
          method: 'post',
          data
        })
      },
    //客服取消回收订单
    backCancelOrder(data) {
      return request({
        url: `/backOrder/cancelOrder`,
        method: 'post',
        data
      })
    },
      // 商户端 回收订单管理
   // 查询单个回收订单详情
   getBackorderDetail(id){
    return request({
			url: `/backOrder/${id}`,
			method: 'get'
		})
  },
  // 回收订单支付后点击刷新
  backOrderQueryOrder(data){
    return request({
			url: `/backOrder/queryOrder`,
			method: 'post',
      data
		})
  },
  // 回收订单支付时 支付二维码轮询状态
  buyerPayStatus(id){
    return request({
			url: `/backOrder/buyerPayStatus?backOrderId=${id}`,
			method: 'get'
		})
  },
  // 包赔订单  新增包赔资料
  guaranteeInfoSave(data){
    return request({
			url: `/guaranteeInfo`,
			method: 'post',
      data
		})
  },
  // 包赔订单  更新包赔资料
  guaranteeInfoUpdate(data){
    return request({
			url: `/guaranteeInfo`,
			method: 'put',
      data
		})
  },
  // 包赔订单  获取包赔资料
  getByBackOrderId(params){
    return request({
			url: `/guaranteeInfo/getByBackOrderId`,
			method: 'get',
      params
		})
  },
  // 包赔订单  审核包赔资料
  auditGuaranteeInfo(data){
    return request({
			url: `/backOrder/audit/sucess`,
			method: 'post',
      data
		})
  },
  // 回收订单转待审核  /backOrder/toAudit
  transToAudit(data){
    return request({
			url: `/backOrder/toAudit`,
			method: 'post',
      data
		})
  },
   // 回收订单 修改买家支付状态
   editBuyPayState(data){
    return request({
			url: `/backOrder/changeBuyerPayStatus`,
			method: 'post',
      data
		})
  },

}
export default onSelfApi

