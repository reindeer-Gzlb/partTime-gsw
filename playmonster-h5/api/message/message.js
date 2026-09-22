import request from "@/utils/request.js"
let messageApi = {
	// 获取环信token
	getHxToken() {
		return request({
			url: `/im/msg/getUserToken`,
			method: 'get',
			token: uni.getStorageSync('token')
		})
	},
	// 发送环信消息
	sendMsg(data) {
		return request({
			url: `/im/msg/sendMsg`,
			method: 'post',
			data,
			token: uni.getStorageSync('token')
		})
	},
	// 获取群成员信息
	getGroupPersons(groupId, fromHxId) {
		return request({
			url: `/other/getOtherGroupInfo?groupId=${groupId}&fromHxId=${fromHxId}`,
			method: 'get',
			token: uni.getStorageSync('token')
		})
	},
	//热门问题
	bangzhuzhongxin(data) {
		return request({
			url: `/web/notice/list`,
			method: 'get',
			data
		})
	},
	// 用户备注列表
	userRemarkList(fromHxId, toHxId = '') {
		return request({
			url: `/userRemark/list?fromHxId=${fromHxId}&toHxId=${toHxId}`,
			method: 'get',
			token: uni.getStorageSync('token')
		})
	},
	// 查询系统字典表
	getDict(dictType) {
		return request({
			url: "/system/dict/data/type/" + dictType,
			method: 'get',
			token: uni.getStorageSync('token')
		})
	},
	//推送 云函数
	pushMsg(data) {
		return request({
			url: "/push",
			method: 'post',
			data,
			domain: '/unicloud'
		})
	},

}

export default messageApi