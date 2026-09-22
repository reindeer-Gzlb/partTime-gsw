import {http} from "./request.js"

// get请求
const get = (url, params,options = {}) => {
	return new Promise(resolve => {
		http(url, 'GET', params, options).then(data => {
			resolve(data);
		});
	})
}

// post请求
const post = (url, params,options = {}) => {
	return new Promise(resolve => {
		http(url, 'POST', params,options).then(data => {
			resolve(data);
		});
	})
}

// put请求
const put = (url, params,options = {}) => {
	return new Promise(resolve => {
		http(url, 'PUT', params,options).then(data => {
			resolve(data);
		});
	})
}

// del请求
const del = (url, params,options = {}) => {
	return new Promise(resolve => {
		http(url, 'DELETE', params, options).then(data => {
			resolve(data);
		});
	})
}

export function isUrl(urlString) {
	var reg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?/;
	return reg.test(urlString)
}
module.exports = {
	put: put,
	get: get,
	post: post,
	del: del,
	isUrl: isUrl
}
