import request from '@/utils/request'

// 查询今日收入
export function gettodayPrice() {
    return request({
      url: '/data/center/today',
      method: 'get'
    })
  }

  // 查询今日回收和售出
export function getshouchuzhichu () {
    return request({
      url: '/data/center/todayHSSC',
      method: 'get'
    })
  }

    // 查询热门游戏
export function getremenyouxi() {
    return request({
      url: '/data/center/hotGame',
      method: 'get'
    })
  }

  /**
   * 获取实时成交数据
   * 
   */
  export function getshishichengjiao(data) {
    return request({
      url: '/data/center/todaySCinfo',
      method: 'get',
      params:data
    })
  }

  /**
   * 获取财务统计数据
   * 
   * 
   */
  export function getcaiwutongji(data) {
    return request({
      url: '/data/center/finance',
      method: 'get',
      params:data
    })
  }

  /**
   * 获取货源统计
   */
  export function gethuoyuan() {
    return request({
      url: '/data/center/source',
      method: 'get'
    })
  }