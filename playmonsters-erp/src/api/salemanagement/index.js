import request from '../../utils/request'

//获取列表
export const getList = (params) => {
    return request({
        url: '/saleInfo/detail/list',
        params
    })
}

//添加
export const addList = (data) => {
    return request({
        url: '/saleInfo/detail',
        method: 'post',
        data
    })
}

//获取详情
export const getoneListdetail = (id) => {
    return request({
        url: `/saleInfo/detail/${id}`
    })
}

//修改
export const editList = (data) => {
    return request({
        url: `/saleInfo/detail`,
        method: 'put',
        data
    })
}

//修改
export const deleteList = (id, pwd) => {
    return request({
        url: `/saleInfo/detail/${id}?pwd=${pwd}`,
        method: 'delete'
    })
}

//导出
export const daochuList = (params) => {
    return request({
        url: `/saleInfo/detail/export`,
        method: 'get',
        params
    })
} 