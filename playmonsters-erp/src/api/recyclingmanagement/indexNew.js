import request from '../../utils/request'

//获取列表
export const getList = (params) => {
    return request({
        url: '/recovery/info/myList',
        params
    })
}

//添加
export const addList = (data) => {
    return request({
        url: '/recovery/info',
        method: 'post',
        data
    })
}

//获取详情
export const getoneListdetail = (id) => {
    return request({
        url: `/recovery/info/${id}`
    })
}

//修改
export const editList = (data) => {
    return request({
        url: `/recovery/info`,
        method: 'put',
        data
    })
}

//修改
export const deleteList = (id, pwd) => {
    return request({
        url: `/recovery/info/${id}?pwd=${pwd}`,
        method: 'delete'
    })
}

//导出
export const daochuList = (params) => {
    return request({
        url: `/recovery/info/export`,
        method: 'get',
        params
    })
} 