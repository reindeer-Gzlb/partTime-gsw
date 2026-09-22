//接口访问地址
import {
	baseUrl
} from "@/config.js"
import {
	isUrl
} from "./HttpSite.js"

import $modal from '@/utils/modal.js';
let header = {
	'content-type': 'application/json',
	 // 'content-type': 'application/x-www-form-urlencoded;charset=UTF-8',
};

// 配置请求头
var headers = (customHeader) => {
	header['CompanyId'] = 101;
	// const Authorization = JSON.parse(uni.getStorageSync('userInfo') || false);
	// if (Authorization) {
	// 	header['Authorization'] = 'Ticket ' + Authorization.token || '';
	// }
	// 接口自定义请求头配置
	if (customHeader) {
		Object.assign(header, customHeader);
	}
	return header
};
//http通信
export function http(url, method, params, options={}) {
	if (!isUrl(url)) {
		url = baseUrl + url;
	}
	return new Promise((resolve, reject) => {
		// 加载中 options.hideLoading 控制是否显示加载中
		!options.hideLoading && $modal.loading();
		uni.request({
			url: url,
			data: params,
			header: headers(options.header),
			dataType: "json", //数据类型转为json
			method: method,
			success: function(res) {
				// 结束loading
				$modal.closeLoading();
				let {
					statusCode:code
				} = res;
				switch (code) {
					case 200:
						resolve(res.data);
						break;
					case 401:
						uni.removeStorageSync('userInfo')
						$modal.alert('当前会话已失效，请关闭页面重新打开')
						reject(res.data);
						break;
					case 402:
						uni.removeStorageSync('userInfo')
						$modal.alert('当前会话已失效，请关闭页面重新打开', () => {
							uni.redirectTo({
								url: '/pages/login/login'
							});
						})
						reject(res.data);
						break;
					case 511:
						uni.removeStorageSync('userInfo')
						$modal.alert('缓存数据库异常，请重新登录(511)', () => {
							uni.redirectTo({
								url: '/pages/login/login'
							});
						})
						reject(res.data);
						break;
					case 10010:
						uni.removeStorageSync('userInfo')
						$modal.alert('当前会话已失效，请关闭页面重新打开', () => {
							uni.redirectTo({
								url: '/pages/login/login'
							});
						})
						reject(res.data);
						break;
					case 404:
						$modal.msg('访问资源不存在');
						reject(res.data);
						break;
					case 500:
						$modal.msgError(res.data.message||"系统错误，请联系");
						reject(res.data);
						break;
					default:
						$modal.msgError(res.data.message);
						reject(res.data);
						break;
				}
			},
			fail: function(res) {
				// 结束loading
				$modal.closeLoading();
				$modal.msgNetError();
				reject(res);
			},
		})
	});
}
