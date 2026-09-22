import request from '@/utils/request'

// 会话历史消息记录 sessionId

export function getHistoryMessageCustom(sessionId,id='',type=1,limit=20) {
    return request({
        url: `/msg/history/recentList?sessionId=${sessionId}&type=${type}&limit=${limit}&id=${id}`,
        method: 'get'
    })
  }

  // 会话内搜索聊天记录
export function searchInSession(sessionId,content='',type='text') {
    return request({
        url: `/msg/history/searchInSession?sessionId=${sessionId}&content=${content}&type=${type}`,
        method: 'get'
    })
  }