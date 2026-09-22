
export default {
  // 获取游戏类型
  GetGameType: {
    method: 'get',
    url: '/api/web/game/Type'
  },

   // 获取账号
   GetZHList: {
    method: 'POST',
    url: '/api/web/good/allList'
  },

  // 获取游戏配置 gameId
  getGameConfig: {
    method: 'get',
    url: '/api/web/gameTemp'
  },


  // 获取账号详情
  getGoodDetail: {
    method: 'GET',
    url: '/api/web/good/info',
  },
  // 查询自定义字段属性  带子集选择项
  getAttrs: {
    method: 'GET',
    url: '/api/web/gameTemp'
  },
  // 查询自定义字段属性  不带选择项
  getAttrsSimp: {
    method: 'GET',
    url: '/api/web/gameTempAttr'
  },
  /// 查询游戏字典
  getGameDict: {
    method: 'GET',
    url: '/api/web/good/attr/info'
  },
  // 查询系统运营商区服
	getArea:{
		url: `/api/web/keylist`,
			method: 'get'
	},
	// 查询服务器
	getServeList: {
		url: `/api/web/districtSuit`,
			method: 'get'
	},
  




  
  getLabelType: {
    method: 'GET',
    url: '/api/account/config/labelType',
  },

  getGameDQ: {
    method: 'GET',
    url: '/api/H5/GetGameDQ',
  }
  ,
  getPCIndex: {
    method: 'GET',
    url: '/api/web/pc/index/data'
  }
};
