import request from "@/utils/request";

// 查询列表
export function getlist(params) {
    return request({
        url: "/check/account/list",
        method: "get",
        params
    });
}

// 添加
export function addlist(data) {
    return request({
        url: "/check/account",
        method: "post",
        data
    });
}

// 获取详情
export function getOnelist(id) {
    return request({
        url: `/check/account/${id}`,
        method: "get"
    });
}

// 修改
export function editlist(data) {
    return request({
        url: "/check/account",
        method: "put",
        data
    });
}

// 删除
export function dellist(id) {
    return request({
        url: `/check/account/${id}`,
        method: "delete"
    });
}