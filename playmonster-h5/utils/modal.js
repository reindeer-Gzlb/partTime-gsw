/**
 *  提示方法
 * 	带配置项 options，参数同uni
 * 	options.success --- 成功回调
 * 	options.fail --- 失败回调
 */

export default {
	// 简单消息提示
	msg(msg) {
		uni.showToast({
			title: msg,
			icon: 'none',
			duration: 1500
		})
	},
	// 成功提示
	msgSuccess(msg) {
		uni.showToast({
			title: msg,
			icon: 'success',
			duration: 1500
		})
	},
	// 失败提示
	// #ifndef MP-ALIPAY
	msgError(msg) {
		uni.showToast({
			title: msg,
			icon: "error",
			duration: 1500
		})
	},
	// #endif
	// #ifdef MP-ALIPAY
	msgError(msg) {
		uni.showToast({
			title: msg,
			icon: "fail",
			duration: 2000
		})
	},
	// #endif
	// 网络异常提示
	// #ifndef MP-ALIPAY
	msgNetError(msg = '网络异常') {
		uni.showToast({
			title: msg,
			icon: "none",
			image: '/static/error.png',
			duration: 3000
		})
	},
	// #endif
	// #ifdef MP-ALIPAY
	msgNetError(msg = '网络异常') {
		uni.showToast({
			title: msg,
			icon: 'exception',
			duration: 3000
		})
	},
	// #endif
	// 弹框提示
	alert(msg, success) {
		uni.showModal({
			title: msg,
			showCancel: false,
			success(res) {
				success && success();
			}
		})
	},
	// 确认框
	confirm(options) {
		uni.showModal({
			title: options.title || '提示',
			content: options.content,
			success(res) {
				if (res.confirm) {
					options && options.success && options.success();
				} else {
					options && options.fail && options.fail();
				}
			}
		})
	},
	// loading
	loading(msg = '加载中...') {
		uni.showLoading({
			title: msg
		})
	},
	closeLoading() {
		uni.hideLoading();
	}
}
