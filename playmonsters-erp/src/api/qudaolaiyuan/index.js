import request from '../../utils/request'

//获取列表
export const getList = (params) => {
    return request({
        url: '/shouFromName/list',
        params
    })
}

//添加
export const addList = (data) => {
    return request({
        url: '/shouFromName',
        method: 'post',
        data
    })
}

//获取详情
export const getoneListdetail = (id) => {
    return request({
        url: `/shouFromName/${id}`
    })
}

//修改
export const editList = (data) => {
    return request({
        url: `/shouFromName`,
        method: 'put',
        data
    })
}

//修改
export const deleteList = (id, pwd) => {
    return request({
        url: `/shouFromName/${id}?pwd=${pwd}`,
        method: 'delete'
    })
}