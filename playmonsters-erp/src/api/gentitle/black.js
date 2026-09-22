import request from '@/utils/request'


// 查询黑名单列表列表
export function roleList(query) {
  return request({
    url: '/gen/roleList',
    method: 'get',
    params: query,
    timeout: 2000000
  })
}
export function gengerTitle(query) {
  return request({
    url: '/gen/gengerTitle',
    method: 'get',
    params: query,
    timeout: 2000000
  })
}
export function getAccountImge(query) {
  return request({
    url: '/gen/gengerTitle',
    method: 'get',
    params: query,
    timeout: 2000000
  })
}

