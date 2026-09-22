import request from '@/utils/request'

// 新增账号标签
export function addLable(data) {
  return request({
    url: '/account/lable',
    method: 'post',
    data: data
  })
}
export function oneKeyLable() {
  return request({
    url: '/account/lable/oneKeyLable',
    method: 'get',
    timeout: 20000000000
  })
}
