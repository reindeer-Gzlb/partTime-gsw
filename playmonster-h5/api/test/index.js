import request from "@/utils/request.js"
let messageApi = {
	// 
	pay(data) {
		return request({
			url: `/pay/unify`,
			method: 'post',
			data,
			token: uni.getStorageSync('token')
		})
	}

}

export default messageApi