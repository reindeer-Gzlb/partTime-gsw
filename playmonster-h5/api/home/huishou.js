import request from "@/utils/request.js"
let homeApi = {
	// 我的回收列表
	getList(data) {
		return request({
			url: `/account/backlist/allList`,
			method: 'get',
			data,
			token: uni.getStorageSync('token')
		})
	},
	// 提交回收
	addAccount(data) {
		return request({
			url: `/account/backlist`,
			method: 'post',
			data,
			token: uni.getStorageSync('token')
		})
	},
	// 我的报价
	mybprice(data) {
		return request({
			url: `/account/quotation/mybprice?backId=${data.backId}`,
			method: 'get',
			token: uni.getStorageSync('token')
		})
	},
	// 接受报价
	krecycle(data) {
		return request({
			url: `/account/krecycle`,
			method: 'post',
			token: uni.getStorageSync('token'),
			data
		})
	},

	// 免登录 报价列表
	listForTel(tel) {
		return request({
			url: `/h5/goods/back/listForTel?tel=${tel}`,
			method: 'get'
		})
	},
	// 提交回收信息到商品库待审
	addQThuishou(data) {
		return request({
			url: `/goods/info/consignment`,
			method: 'post',
			data,
			token: uni.getStorageSync('token')
		})
	},
	// 我的账号 列表 我发布的官网代售账号
	myAccountList(data) {
		return request({
			url: `/need/myConsignment`,
			method: 'get',
			data,
			token: uni.getStorageSync('token')
		})
	},
	// 官网代售 提交回收
	addQThuishou(data) {
		return request({
			url: `/need/consignment`,
			method: 'post',
			data,
			token: uni.getStorageSync('token')
		})
	},
	// 代售商品详情
	getDetail(id) {
		return request({
			url: `/need/myConsignment/${id}`,
			method: 'get',
			token: uni.getStorageSync('token')
		})
	},
	// 以下是新版竞价回收相关
	// 提交回收新
	addGoodsBack(data) {
		return request({
			url: `/h5/goods/back`,
			method: 'post',
			data,
			token: uni.getStorageSync('token')
		})
	},
	// 回收列表支付押金 查询押金支付结果
	backlistQueryOrder(data) {
		return request({
			url: `/account/backlist/queryOrder`,
			method: 'post',
			data,
			token: uni.getStorageSync('token')
		})
	},
	//回收订单列表 新
	getBackOrderList(data) {
		return request({
			url: `/backOrder/list`,
			method: 'get',
			data,
			token: uni.getStorageSync('token')
		})
	},
	//回收订单 改价
	changeAmount(data) {
		return request({
			url: `/backOrder/changeAmount`,
			method: 'post',
			data,
			token: uni.getStorageSync('token')
		})
	},
	// /account/backlist/cancel 取消回收记录
	backlistCancel(data) {
		return request({
			url: `/account/backlist/cancel`,
			method: 'post',
			data,
			token: uni.getStorageSync('token')
		})
	},

}

export default homeApi