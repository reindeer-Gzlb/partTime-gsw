import request from '../../utils/request'

export function getTongJI(params) {
    return request({
        url: '/account/finance/statisticsData',
        method: 'get',
        params
    })
}


export function getmoneyAll(params) {
    return request({
        url: '/account/finance/data',
        method: 'get',
        params
    })
}