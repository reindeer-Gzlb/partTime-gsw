import request from '@/utils/request'

let financeApi = {
  // 获取财务报表
  getFinanceList(data) {
    return request({
      url: '/account/finance/list',
      method: 'get',
      params: data
    })
  },
  getFinanceListSh(data) {
    return request({
      url: '/account/finance/myList',
      method: 'get',
      params: data
    })
  },
  //获取财务统计
  getmoneyList(data) {
    return request({
      url: '/account/finance/data',
      method: 'get',
      params: data
    })
  },
  //新增财务报表
  addFinance(data) {
    return request({
      url: '/account/finance',
      method: 'post',
      data
    })
  },
  // 编辑财务报表
  editFinance(data) {
    return request({
      url: '/account/finance',
      method: 'put',
      data
    })
  },
  // 删除财务报表
  delFinance(ids, pwd) {
    return request({
      url: `/account/finance/${ids}?pwd=${pwd}`,
      method: 'delete'
    })
  },
  //
  ViewFinance(pwd) {
    return request({
      url: `/account/finance/securityPwd?pwd=${pwd}`,
      method: 'get'
    })
  },
  // 批量模板下载
  exportModel() {
    return request({
      url: `/account/finance/exportModel`,
      method: 'get'
    })
  },
  // 导出
  exportList(data) {
    return request({
      url: `/account/finance/export`,
      method: 'get',
      params: data
    })
  },


  // 获取账单类型
  getBillType(data) {
    return request({
      url: '/account/billType/list',
      method: 'get',
      params: data
    })
  },
  getBillTypeCW(data) {
    return request({
      url: '/account/billType/listCW',
      method: 'get',
      params: data
    })
  },
  //新增账单类型
  addType(data) {
    return request({
      url: '/account/billType',
      method: 'post',
      data
    })
  },
  // 编辑账单类型
  editType(data) {
    return request({
      url: '/account/billType',
      method: 'put',
      data
    })
  },
  // 删除账单类型
  delType(ids) {
    return request({
      url: `/account/billType/${ids}`,
      method: 'delete'
    })
  },
  // 查询类型自定义字段详情
  getTypeDetail(ids) {
    return request({
      url: `/account/billType/${ids}`,
      method: 'get'
    })
  }
}
export default financeApi

