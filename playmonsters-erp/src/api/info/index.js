import request from '@/utils/request'

// PanelGroup
export function salesCount(query) {
  return request({
    url: '/indexData/salesCount',
    method: 'get',
    params: query
  })
}

export function gameTypeSale(query) {
  return request({
    url: '/indexData/gameTypeSale',
    method: 'get',
    params: query
  })
}

export function yearCount() {
    return request({
      url: '/indexData/yearCount',
      method: 'get',
    })
  }
  export function gameTypeGon() {
    return request({
      url: '/indexData/gameTypeGon',
      method: 'get',
    })
  }
  export function gameTypeFor() {
    return request({
      url: '/indexData/gameTypeFor',
      method: 'get',
    })
  }
  export function sevenDaysCount() {
    return request({
      url: '/indexData/sevenDaysCount',
      method: 'get',
    })
  }