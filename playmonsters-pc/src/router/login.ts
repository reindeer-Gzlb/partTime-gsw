export default [
  {
    path: '/',
    redirect: '/index/home'
  },

  {
    path: '/index',
    name: 'index',
    component: () => import(/* webpackChunkName: "home" */ '../views/index.vue'),
    redirect: { name: 'home' },
    children: [
      { path: '/', redirect: { name: 'home' } },
      {
        path: 'goZFB',
        name: 'goZFB',
        component: () => import(/* webpackChunkName: "home" */ '../views/goZFB.vue')
      },
      // 测试
      {
        path: 'pay',
        name: 'test',
        component: () => import(/* webpackChunkName: "home" */ '../views/home/test.vue')
      },
      //业务介绍
      {
        path: 'yewujieshao',
        name: 'yewujieshao',
        component: () => import(/* webpackChunkName: "home" */ '../views/monter/yewujieshao.vue')
      },
      // 中介担保
      {
        path: 'zjdb',
        name: 'zjdb',
        component: () => import(/* webpackChunkName: "home" */ '../views/monter/zhongjiedanbao.vue')
      },
      // 确认订单
      {
        path: 'sureOrder',
        name: 'sureOrder',
        component: () => import(/* webpackChunkName: "home" */ '../views/goodDetail/sureOrder.vue')
      },
      // 订单详情
      {
        path: 'orderDetail',
        name: 'orderDetail',
        component: () =>
          import(/* webpackChunkName: "home" */ '../views/goodDetail/orderDetail.vue')
      },
      {
        path: 'home',
        name: 'home',
        component: () => import(/* webpackChunkName: "home" */ '../views/home/home.vue')
      },

      // 最近交易
      {
        path: 'finish',
        name: 'finish',
        component: () => import(/* webpackChunkName: "home" */ '../views/home/finish.vue')
      },
      // 全部游戏类型
      {
        path: 'accountOrder',
        name: 'accountOrder',
        component: () =>
          import(/* webpackChunkName: "accountOrder" */ '../views/accountOrder/index.vue')
      },
      // 列表
      {
        path: 'goodList',
        name: 'goodList',
        component: () =>
          import(/* webpackChunkName: "accountOrder" */ '../views/goodList/index.vue'),
        meta: {
          title: '商品列表',
          isKeepAlive: false
        },
        // 路由独享守卫
        beforeEnter: (to, from) => {
          to.meta.isKeepAlive = to.name === 'goodList' && from.name == 'goodDetail' ? true : false;
          return true;
        }
      },
      // 详情
      // {
      //   path: 'goodDetail',
      //   name: 'goodDetail',
      //   component: () =>
      //     import(/* webpackChunkName: "accountOrder" */ '../views/goodDetail/index.vue')
      // },
      // APP下载
      {
        path: 'appDownload',
        name: 'appDownload',
        component: () =>
          import(/* webpackChunkName: "accountOrder" */ '../views/appDownload/index.vue')
      },
      // 账号回收页面
      {
        path: 'sale',
        name: 'sale',
        component: () =>
          import(/* webpackChunkName: "accountOrder" */ '../views/recovery/index.vue')
      },
      // 我要卖
      {
        path: 'sell',
        name: 'sell',
        component: () => import('../views/sell/index.vue')
      },
      // 我要卖——基本情况
      {
        path: 'BasicInformation',
        name: 'BasicInformation',
        component: () => import('../views/sell/BasicInformation.vue')
      },
      // 我要卖——账号信息填写
      {
        path: 'fillInformation',
        name: 'fillInformation',
        component: () => import('../views/fillInformation/index.vue')
      },
      // 我要租页面
      {
        path: 'hire',
        name: 'hire',
        component: () => import('../views/hire/index.vue')
      },
      // 估价结果
      {
        path: 'commit',
        name: 'commit',
        component: () =>
          import(/* webpackChunkName: "accountOrder" */ '../views/recovery/commit.vue')
      },
      // 竞价回收填报页
      {
        path: 'jingjiahuishou',
        name: 'Jingjiahuishou',
        component: () => import('../views/sell/jingjiahuishou.vue')
      },
      // 中介担保
      {
        path: 'security',
        name: 'security',
        component: () => import('../views/security/index.vue')
      },
      //新闻中心
      {
        path: 'helpcenter',
        name: 'helpcenter',
        component: () => import('../views/monter/xinwencenter.vue')
      },
      //通知公告等 文章详情页
      {
        path: 'articleDetail',
        name: 'ArticleDetail',
        component: () =>
          import(/* webpackChunkName: "accountOrder" */ '../views/articleDetail/index.vue')
      },
      // 其他 帮助
      {
        path: 'huishouOther',
        name: 'huishouOther',
        component: () => import(/* webpackChunkName: "accountOrder" */ '../views/other/index.vue'),
        redirect: { name: 'gonggao' },
        children: [
          { path: '/', redirect: { name: 'gonggao' } },
          {
            path: 'kefu',
            name: 'kefu',
            component: () => import('../views/other/kefu.vue')
          },

          {
            path: 'passwore',
            name: 'passwore',
            component: () => import('../views/other/passwore.vue')
          },
          {
            path: 'xiaoxi',
            name: 'xiaoxi',
            component: () => import('../views/other/xiaoxi.vue')
          },
          {
            path: 'cooperate',
            name: 'cooperate',
            component: () => import('../views/other/cooperate.vue')
          },
          {
            path: 'geren',
            name: 'geren',
            component: () => import('../views/other/gerenzhongxin.vue')
          },

          {
            path: 'gonggao',
            name: 'gonggao',
            component: () => import('../views/other/gonggao.vue')
          },
          {
            path: 'bangzhu',
            name: 'bangzhu',
            component: () => import('../views/other/bangzhu.vue')
          },
          {
            path: 'contactkefu',
            name: 'contactkefu',
            component: () => import('../views/other/contactkefu.vue')
          },
          // 新闻详情
          {
            path: 'newsDetail',
            name: 'newsDetail',
            component: () => import('../views/other/help/newsDetail.vue')
          },
          {
            path: 'collect',
            name: 'collect',
            component: () => import('../views/other/collect.vue')
          },
          {
            path: 'jieshao',
            name: 'jieshao',
            component: () => import('../views/other/help/jieshao.vue')
          },
          {
            path: 'fankui',
            name: 'fankui',
            component: () => import('../views/other/help/fankui.vue')
          },
          // // 我发布的竞价回收
          {
            path: 'myJingjiahuishou',
            name: 'MyJingjiahuishou',
            component: () => import('../views/other/jingjiahuishou/myJingjiahuishou.vue')
          },
          // //竞价回收 发布的商品报价详情
          {
            path: 'jingjiahuishouPriceList',
            name: 'JingjiahuishouPriceList',
            component: () => import('../views/other/jingjiahuishou/jingjiahuishouPriceList.vue')
          },
          // 我的商品
          {
            path: 'MyProducts',
            name: 'MyProducts',
            component: () => import('../views/other/MyProducts.vue')
          },
          // 我的评估
          {
            path: 'Myevaluation',
            name: 'Myevaluation',
            component: () => import('../views/other/Myevaluation.vue')
          },
          // 我的浏览
          {
            path: 'Mybrowsing',
            name: 'Mybrowsing',
            component: () => import('../views/other/Mybrowsing.vue')
          },
          // 实名认证
          {
            path: 'Realname',
            name: 'Realname',
            component: () => import('../views/other/realname.vue')
          },
          // 帮助中心详情
          {
            path: 'helpdetail',
            name: 'helpdetail',
            component: () => import('../views/other/helpdetail.vue')
          },
          // 投诉建议
          {
            path: 'suggest',
            name: 'suggest',
            component: () => import('../views/other/suggest.vue')
          },
          // 问题反馈列表
          {
            path: 'ProblemList',
            name: 'ProblemList',
            component: () => import('../views/other/ProblemList.vue')
          },
          {
            path: 'shanghu',
            name: 'shanghu',
            component: () => import('../views/other/myshanghu.vue')
          },
          {
            path: 'xinwen',
            name: 'xinwen',
            component: () => import('../views/other/myxinwen.vue')
          },
          {
            path: 'zhaohui',
            name: 'zhaohui',
            component: () => import('../views/other/zhaohui.vue')
          },
          {
            path: 'zhaomu',
            name: 'zhaomu',
            component: () => import('../views/other/zhaomu.vue')
          }
        ]
      },
      {
        path: 'other',
        name: 'other',
        component: () => import(/* webpackChunkName: "accountOrder" */ '../views/other/indexxin.vue'),
        redirect: { name: 'gonggao' },
        children: [
          { path: '/', redirect: { name: 'gonggao' } },
          {
            path: 'mycenter',
            name: 'mycenter',
            component: () => import('../views/other/mycenter.vue')
          },

          {
            path: 'passwore',
            name: 'passwore',
            component: () => import('../views/other/passwore.vue')
          },
          {
            path: 'xiaoxi',
            name: 'xiaoxi',
            component: () => import('../views/other/xiaoxi.vue')
          },
          {
            path: 'cooperate',
            name: 'cooperate',
            component: () => import('../views/other/cooperate.vue')
          },
          {
            path: 'geren',
            name: 'geren',
            component: () => import('../views/other/gerenzhongxin.vue')
          },

          {
            path: 'gonggao',
            name: 'gonggao',
            component: () => import('../views/other/gonggao.vue')
          },
          {
            path: 'bangzhu',
            name: 'bangzhu',
            component: () => import('../views/other/bangzhu.vue')
          },
          {
            path: 'contactkefu',
            name: 'contactkefu',
            component: () => import('../views/other/contactkefu.vue')
          },
          // 新闻详情
          {
            path: 'newsDetail',
            name: 'newsDetail',
            component: () => import('../views/other/help/newsDetail.vue')
          },
          {
            path: 'collect',
            name: 'collect',
            component: () => import('../views/other/collect.vue')
          },
          {
            path: 'jieshao',
            name: 'jieshao',
            component: () => import('../views/other/help/jieshao.vue')
          },
          {
            path: 'fankui',
            name: 'fankui',
            component: () => import('../views/other/help/fankui.vue')
          },
          // // 我发布的竞价回收
          {
            path: 'myJingjiahuishou',
            name: 'MyJingjiahuishou',
            component: () => import('../views/other/jingjiahuishou/myJingjiahuishou.vue')
          },
          // //竞价回收 发布的商品报价详情
          {
            path: 'jingjiahuishouPriceList',
            name: 'JingjiahuishouPriceList',
            component: () => import('../views/other/jingjiahuishou/jingjiahuishouPriceList.vue')
          },
          // 我的商品
          {
            path: 'MyProducts',
            name: 'MyProducts',
            component: () => import('../views/other/MyProducts.vue')
          },
          // 我的评估
          {
            path: 'Myevaluation',
            name: 'Myevaluation',
            component: () => import('../views/other/Myevaluation.vue')
          },
          // 我的浏览
          {
            path: 'Mybrowsing',
            name: 'Mybrowsing',
            component: () => import('../views/other/Mybrowsing.vue')
          },
          // 实名认证
          {
            path: 'Realname',
            name: 'Realname',
            component: () => import('../views/other/realname.vue')
          },
          // 帮助中心详情
          {
            path: 'helpdetail',
            name: 'helpdetail',
            component: () => import('../views/other/helpdetail.vue')
          },
          // 投诉建议
          {
            path: 'suggest',
            name: 'suggest',
            component: () => import('../views/other/suggest.vue')
          },
          // 问题反馈列表
          {
            path: 'ProblemList',
            name: 'ProblemList',
            component: () => import('../views/other/ProblemList.vue')
          },
          {
            path: 'shanghu',
            name: 'shanghu',
            component: () => import('../views/other/myshanghu.vue')
          },
          {
            path: 'xinwen',
            name: 'xinwen',
            component: () => import('../views/other/myxinwen.vue')
          },
          {
            path: 'zhaohui',
            name: 'zhaohui',
            component: () => import('../views/other/zhaohui.vue')
          },
          {
            path: 'zhaomu',
            name: 'zhaomu',
            component: () => import('../views/other/zhaomu.vue')
          }
        ]
      },
      {
        path: '/',
        name: '/',
        component: () => import(/* webpackChunkName: "home" */ '../views/goodDetail/index.vue'),
        // redirect: { name: 'pages' },
        children: [
          // 详情goodDetail
          {
            path: 'pages/detail/index',
            name: 'pages/detail/index',
            component: () =>
              import(/* webpackChunkName: "accountOrder" */ '../views/goodDetail/index.vue')
          },
        ]
      }
    ]
  }
];
