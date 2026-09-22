import request from "@/utils/request.js"

let userApi = {
	// 手机号一键登录
	oneKeylogin(data) {
		return request({
			url: '/oneKeylogin',
			method: 'post',
			data: data
		})
	},

	// 登录方法
	login(data) {
		return request({
			url: '/login',
			method: 'post',
			data: data
		})
	},
	// 手机验证码登录方法
	phoneLogin(data) {
		return request({
			url: '/h5/login',
			method: 'post',
			data: data
		})
	},
	// 注册方法
	register(data) {
		return request({
			url: '/register',
			method: 'post',
			headers: {
				isToken: false
			},
			data: data
		})
	},
	// 找回密码
	SmsforPwd(data) {
		return request({
			url: '/h5/SmsforPwd',
			method: 'post',
			headers: {
				isToken: false
			},
			data: data
		})
	},
	// 获取用户详细信息
	getInfo(token) {
		return request({
			url: '/system/user/profile/new',
			method: 'get',
			token: uni.getStorageSync('token')
		})
	},
	// B编辑用户详细信息
	editInfo(token, data) {
		return request({
			url: '/system/user/profile',
			method: 'put',
			token: token,
			data,
			token: uni.getStorageSync('token')
		})
	},
	// 重置密码
	// /system/user/profile/updatePwd?oldPassword=123456&newPassword=123456
	resetPsd(token, data) {
		return request({
			url: '/system/user/profile/updatePwd',
			method: 'put',
			token: token,
			data
		})
	},
	// 退出方法
	logout(data) {
		return request({
			url: '/logout',
			method: 'post'
		})
	},
	// 获取验证码
	getCodeImg(data) {
		return request({
			url: '/captchaImage',
			method: 'get',
			timeout: 20000
		})
	},
	// 收藏商品 、/*accountId  createId*/

	addCollect(data) {
		return request({
			url: '/web/goods/collect',
			method: 'POST',
			data,
			token: uni.getStorageSync('token')
		})
	},
	// 取消收藏商品 、/*accountId  createId*/

	removeCollct(data) {
		return request({
			url: '/web/goods/collect/remove',
			method: 'POST',
			data,
			token: uni.getStorageSync('token')
		})
	},
	// 收藏列表
	listAccount(createId) {
		return request({
			url: `/web/goods/collect/list?createId=${createId}`,
			method: 'get',
			token: uni.getStorageSync('token')
		})
	},
	// 申请成为商户
	applySupplier(data) {
		return request({
			url: `/customer/audit`,
			method: 'post',
			data,
			token: uni.getStorageSync('token')
		})
	},
	// 获取商户申请最新一条的记录
	lastOne(data) {
		return request({
			url: `/customer/audit/lastOne`,
			method: 'get',
			token: uni.getStorageSync('token')
		})
	},
	// 用户修改手机号专用
	editPhone(data) {
		return request({
			url: '/system/user/editPhone',
			method: 'put',
			token: uni.getStorageSync('token'),
			data
		})
	}







}

export default userApi