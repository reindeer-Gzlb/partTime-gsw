import {
	get,
	post
} from '@/utils/request/HttpSite.js'


//获取属性列表
export const attrTypeList = function(data) {
	return get(
		"/V2/H5/attrList",
		data
	)
}

//估值
export const subValuation = function(params) {
	return post(
		"/V2/H5/valuation",
		params
	)
}

//获取角色列表
export const getRoleList = function(data) {
	return get(
		"/V2/H5/roleList",
		data
	)
}
//获取角色详情
export const getRoleInfo = function(data) {
	return get(
		"/V2/H5/gengerTitle",
		data
	)
}

//生成图片
export const gengerImg = function(data) {
	return post(
		"/V2/H5/gengerImge",
		data
	)
}

//生成图片
export const getDicts = function(dictType) {
	return get(
		"/system/dict/data/type/" + dictType
	)
}