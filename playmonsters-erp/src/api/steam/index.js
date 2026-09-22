import request from '@/utils/request'

// 查询任务执行列表
export function getList(query) {
    return request({
        url: '/steam/collect/list',
        method: 'get',
        params: query
    })
}

// 导出任务执行列表
export function daochurenwu(query) {
    return request({
        url: '/steam/collect/export',
        method: 'get',
        params: query
    })
}
// 查询详情
export function getDetail(id) {
    return request({
        url: `/steam/collect/${id}`,
        method: 'get'
    })
}
// 新增任务执行
export function addList(data) {
    return request({
        url: `/steam/collect`,
        method: 'post',
        data
    })
}

// 修改任务执行
export function editList(data) {
    return request({
        url: `/steam/collect`,
        method: 'put',
        data
    })
}

// 回收
export function backList(data) {
    return request({
        url: `/steam/collect/backList`,
        method: 'put',
        data
    })
}

// 重新校验
export function againRules(data) {
    return request({
        url: `/steam/collect/restart`,
        method: 'put',
        data
    })
}

// 删除执行任务
export function deleteList(ids) {
    return request({
        url: `/steam/collect/${ids}`,
        method: 'delete'
    })
}