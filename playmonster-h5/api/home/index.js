import request from "@/utils/request.js"
let homeApi = {
	// 查询商品信息
	getInfo(id) {
		return request({
			url: `/submitAccount/superSubmit?id=${id}`,
			method: 'get'
		})
	},
	// 提交账号资料
	submit(data) {
		return request({
			url: `/submitAccount/superSubmitRemake`,
			method: 'post',
			data
		})
	},
	// 以上是供货商填报资料页面专用
	// 查询列表
	getList(data) {
		return request({
			url: `/web/good/allList`,
			method: 'post',
			data
		})
	},
	// 查询特价列表
	// getTejiaList(data) {
	// 	return request({
	// 		url: `/H5/V2/holdAccount?rows=${data.rows}&page=${data.page}`,
	// 		method: 'get'
	// 	})
	// },
	// 查询详情
	getDetail(id, shopId) {
		return request({
			url: `/web/good/info?id=${id}&retailId=${shopId}`,
			method: 'get',
			token: uni.getStorageSync('token')
		})
	},
	// 查询游戏类型
	getGames(data) {
		return request({
			url: `/web/game/Type`,
			method: 'get',
			data
		})
	},

	// 查询自定义字段属性  带子集选择项
	getAttrs(id) {
		return request({
			url: `/web/gameTemp?gameId=${id}`,
			method: 'get'
		})
	},
	// 查询自定义字段属性  不带选择项
	getAttrsSimp(id) {
		return request({
			url: `/web/gameTempAttr?gameId=${id}`,
			method: 'get'
		})
	},
	// 查询游戏字典
	getGameDict(id) {
		return request({
			url: `/web/good/attr/info?gameId=${id}`,
			method: 'get'
		})
	},
	// 查询系统运营商区服
	getArea(data) {
		return request({
			url: `/web/keylist?gameId=${data.gameId}&systemKey=${data.systemKey}&parentId=${data.parentId?data.parentId:''}`,
			method: 'get'
		})
	},
	// 查询服务器
	getServeList(data) {
		return request({
			url: `/web/districtSuit?parentId=${data.parentId?data.parentId:''}`,
			method: 'get'
		})
	},
	// 查询客服列表
	getKefuList() {
		return request({
			url: `/V2/H5/account`,
			method: 'get'
		})
	},
	// QQ群组列表
	getQQGroup() {
		return request({
			url: `/V2/H5/qqGroup`,
			method: 'get'
		})
	},
	// 获取公告列表
	gonggaoDetails(data) {
		return request({
			url: `/V2/H5/gonggaoDetails`,
			method: 'get',
			data
		})
	},
	// 查询某一类型的文章
	noticeDetails(id) {
		return request({
			url: `/web/notice/${id}`,
			method: 'get'
		})
	},
	// 获取找回案例列表
	backDetails() {
		return request({
			url: `/V2/H5/backDetails`,
			method: 'get'
		})
	},
	// 提交反馈
	addproblem(data) {
		return request({
			url: `/account/problem`,
			method: 'post',
			data,
			token: uni.getStorageSync('token')
		})
	},
	// 立即购买
	submitPrice(data) {
		return request({
			url: `/V2/H5/submitPrice`,
			method: 'post',
			data
		})
	},

	// 发送验证码  phone  type
	sendYzm(data) {
		return request({
			url: `/sms/sendYzm?phone=${data.phone}&type=${data.type}`,
			method: 'get'
		})
	},
	// 首页轮播图
	getImgs(type) {
		return request({
			url: `/account/zhubo/list/?type=${type}`,
			method: 'get',
			token: uni.getStorageSync('token')
		})
	},
	// app更新检测
	lastOneApp(appType) {
		return request({
			url: `/app/version/lastOne?appType=${appType}`,
			method: 'get'
		})
	},
	// 黑号查询
	heihaoCheck(data) {
		return request({
			url: `/black/blackList?game_sn=${data.game_sn}&content=${data.content}`,
			method: 'get',
			token: uni.getStorageSync('token')
		})
	},
	// 客服验证
	kefuCheck(value) {
		return request({
			url: `/V2/H5/checkAccount/${value}`,
			method: 'get'
		})
	},
	//交易须知
	jiaoyixuzhi(data) {
		return request({
			url: `/V2/H5/gonggaoDetails`,
			method: 'get',
			data
		})
	},
	//联系客服
	lianxikefu(data) {
		return request({
			url: `/V2/H5/account`,
			method: 'get',
			data
		})
	},
	//官方客服微信验证
	weixinyanzheng(data) {
		return request({
			url: `/V2/H5/checkAccount`,
			method: 'get',
			data
		})
	},
	//平台收款账号
	shoukunanzhanghao(data) {
		return request({
			url: `/V2/H5/checkAccount`,
			method: 'get',
			data
		})
	},
	//黑号查询
	heihaochaxun(data) {
		return request({
			url: `/black/blackList`,
			method: 'get',
			data,
			token: uni.getStorageSync('token')
		})
	},
	//官方视频号
	shipinhaoInfo() {
		return request({
			url: `/web/social/list`,
			method: 'get',
			token: uni.getStorageSync('token')
		})
	},
	//联系客服2
	lianxikefus(data) {
		return request({
			url: `/V2/H5/account`,
			method: 'get',
			data
		})
	},
	//帮助中心
	bangzhuzhongxin(data) {
		return request({
			url: `/web/notice/list`,
			method: 'get',
			data
		})
	},
	getbangzhuzhongxinInfo(id) {
		return request({
			url: `/web/notice/${id}`,
			method: 'get',
		})
	},
	//我的买单
	myMaidan(data) {
		return request({
			url: `/need/myOrder`,
			method: 'get',
			data,
			token: uni.getStorageSync('token')
		})
	},
	//公告
	getGonggaoInfo(data) {
		return request({
			url: '/web/recentlydeal',
			data
		})
	},
	//实名认证
	getIsNumber(data) {
		return request({
			url: '/realname/auth',
			method: 'post',
			data,
			token: uni.getStorageSync('token')
		})
	},
	//官方交易群
	getqqDroup() {
		return request({
			url: '/V2/H5/qqGroup'
		})
	},
	//修改价格
	changePrice(data) {
		return request({
			url: '/need/goods/editPrice',
			method: 'post',
			data,
			token: uni.getStorageSync('token')
		})
	},
	//下架
	xiajiaGoods(data) {
		return request({
			url: '/goods/info/downGoods',
			method: 'post',
			data,
			token: uni.getStorageSync('token')
		})
	},
	//上架
	shangjiaGoods(id) {
		return request({
			url: `/goods/info/upGoods/${id}`,
			token: uni.getStorageSync('token')
		})
	},
	// 修改密码
	changePassword(data) {
		return request({
			url: "/h5/SmsforPwd",
			method: 'post',
			data,
			token: uni.getStorageSync('token')
		})
	},
	//怪兽玩代售消息
	getdetailInfo(data) {
		return request({
			url: '/need/message/list',
			method: 'get',
			data,
			token: uni.getStorageSync('token')
		})
	},
	//消息已读未读
	getYuedu(id) {
		return request({
			url: `/need/message/changeRead/${id}`,
			method: 'get',
			token: uni.getStorageSync('token')
		})
	}

}

export default homeApi
// ?noticeType=jyxz