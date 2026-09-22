// 区分环境 ,HbuilderX编辑运行时dev开发环境，发行后自动时生成环境
import {
	baseUrl
} from "@/config.js"
let baseUrlNew = baseUrl
let isLogin = true
//定义接口
function request(opts = {}) {
	//监听网络链接
	uni.onNetworkStatusChange((res) => {
		if (!res.isConnected) {
			uni.showToast({
				title: '网络连接不可用！',
				icon: 'none'
			});
		}
		return false
	});
	//定义参数对象
	if (!opts.method) opts.method = 'get'
	if (opts.domain) {
		baseUrlNew = opts.domain
	} else {
		baseUrlNew = baseUrl
	}
	// token
	// let userToken = uni.getStorageSync('token')
	let pagePath = location.href
	console.log('pagePath', pagePath);
	
	// #ifdef APP-PLUS
	let resource = 'app'
	console.log('resource', resource);
	// #endif
	
	// #ifdef H5
	let resource = 'h5'
	console.log('resource', resource);
	//#endif
	
	let header = {
		Authorization: opts.token || null,
		Token: opts.token || null,
		'Content-Type': 'application/json; charset=UTF-8',
		pagePath,
		resource
	}
	
	// let header = {}
	// // 删除鉴权
	// if (opts.noAuth) {
	// 	delete header.Authorization
	// 	delete header.Token
	// 	delete header.jwt
	// }
	return new Promise((resolve, reject) => {
		uni.showLoading({
			title: '加载中...'
		})
		uni.request({
			url: baseUrlNew + opts.url,
			data: opts.data,
			method: opts.method,
			header: header,
			timeout: opts.timeout | 10000,
			success: res => {
				// console.log('基础返回值', res)
				uni.hideLoading();
				if (res.statusCode == 200) {
					resolve(res.data);
					if (res.data.code == 401) {
						// 获取当前页面路由
						let pages = getCurrentPages();
						let redirect = pages[pages.length - 1].$page.fullPath
						console.log('当前页面的路由：', pages[pages.length - 1].$page.fullPath)
						uni.showToast({
							title: "请先登录！",
							icon: 'none'
						})
						setTimeout(() => {
							uni.redirectTo({
								url: `/pages/login/index?redirect=${redirect}`
							});
						}, 1000)
					}
				} else if (res.statusCode == 403) {
					if (isLogin) {
						isLogin = false;
						uni.showModal({
							title: '提示',
							content: '您未登录，请先登录',
							showCancel: false,
							success: function(res) {
								if (res.confirm) {
									uni.clearStorageSync();
									uni.reLaunch({
										url: '/pages/login/index'
									});
									isLogin = true;
								}
							}
						});
					}
				}
			},
			fail: () => {
				uni.hideLoading();
				uni.showToast({
					title: '网络错误!',
					icon: 'none',
					duration: 1500
				});
			}
		});
	})
}

export default request