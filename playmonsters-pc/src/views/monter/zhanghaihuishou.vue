<template>
  <section class="container">
    <div class="w1200">
      <div class="location">
        <el-icon class="home-icon" color="#4E5969" :size="15">
          <House />
        </el-icon>
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>{{ activeName }}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="cont flex">
        <div class="left">
          <el-menu
            :default-active="active"
            class="el-menu-vertical-demo"
            @open="handleOpen"
            menu-trigger="click"
            :router="true"
            :unique-opened="true"
            bg-color="#fff"
            text-color="rgba(0,0,0,.7)"
          >
            <div class="item" v-for="menu in menus" :key="menu.id">
              <el-sub-menu v-if="menu.child && menu.child.length" :index="menu.path">
                <template #title>
                  <span>{{ menu.name }}</span>
                </template>
                <el-menu-item :index="child.path" v-for="child in menu.child" :key="child.id">{{
                  child.name
                }}</el-menu-item>
              </el-sub-menu>
              <el-menu-item :index="menu.path" v-else>
                <span>{{ menu.name }}</span>
              </el-menu-item>
            </div>
          </el-menu>
        </div>
        <div class="right">
          <router-view />
        </div>
      </div>
    </div>

    <!-- 底部 -->
    <Footer />
  </section>
</template>
  
  <script>
import { RefreshRight, CaretTop, Search, CaretBottom, House, View } from '@element-plus/icons-vue';
import Footer from '@/components/Footer.vue';
import { reactive, toRefs, onMounted, onUnmounted, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import kefu from '../../api/kefu';
export default {
  name: 'other',
  components: { House, RefreshRight, Search, CaretBottom, CaretTop, View, Footer },
  setup(props, ctx) {
    const router = useRouter();
    const state = reactive({
      active: '',
      activeName: '最新公告',
      menus: [
        //   {
        //     name: '个人中心',
        //     id: 0,
        //     path: '/pages/other/geren'
        //   },
        //   {
        //     name: '我的商品',
        //     id: 5,
        //     path: '/pages/other/goodsList'
        //   },
        //   {
        //     name: '我的订单',
        //     id: 6,
        //     path: '/pages/other/myBuyOrder'
        //   },
        // {
        //   name: '我的回收',
        //   id: 6,
        //   path: '/pages/other/Myevaluation'
        // },

        //   {
        //     name: '收藏商品',
        //     id: 1,
        //     path: '/pages/other/collect'
        //   },
        //   {
        //     name: '找回包赔',
        //     id: 2,
        //     path: '/pages/other/baopei'
        //   },
        {
          name: '端游回收',
          id: 3,
          path: 'news',
          child: [
            // {
            //   name: '公告列表',
            //   id: 201,
            //   path: '/pages/other/gonggao?notice=20'
            // },
            // {
            //   name: '通知公告',
            //   id: 201,
            //   path: '/pages/other/tongzhigg?notice=201'
            // },
            // // {
            // //   name: '找回案例',
            // //   id: 201,
            // //   path: '/pages/other/gonggao?notice=100'
            // // },
          ]

        },
        {
          name: '手游回收',
          id: 4,
          path: 'anquan',
          child: [
            // {
            //   name: '联系客服',
            //   id: 401,
            //   path: '/pages/kefuList'
            // },
            // {
            //   name: '商务合作',
            //   id: 401,
            //   path: '/pages/other/jieshao?notice=105'
            // },
            // {
            //   name: '招贤纳士',
            //   id: 407,
            //   path: '/pages/other/jieshao?notice=205'
            // },
            // // {
            // //   name: '售后案例',
            // //   id: 402,
            // //   path: '/pages/other/jieshao?notice=200'
            // // },
            // {
            //   name: '购买指南',
            //   id: 410,
            //   path: '/pages/other/jieshao?notice=202'
            // },
            // {
            //   name: '出售指南',
            //   id: 403,
            //   path: '/pages/other/jieshao?notice=203'
            // },
            // {
            //   name: '业务介绍',
            //   id: 404,
            //   path: '/pages/other/jieshao?notice=ywjs'
            // },
            // // {
            // //   name: '服务协议',
            // //   id: 405,
            // //   path: '/pages/other/jieshao?notice=10'
            // // },
            // {
            //   name: '主播合作',
            //   id: 406,
            //   path: '/pages/other/jieshao?notice=201'
            // },
            // {
            //   name: '投诉建议',
            //   id: 408,
            //   path: '/pages/other/fankui'
            // },
            // // {
            // //   name: '成为商家',
            // //   id: 409,
            // //   path: '/pages/other/shenqingShangjia'
            // // },
            // // {
            // //   name: '联系我们',
            // //   id: 409,
            // //   path: '/pages/other/jieshao?notice=13'
            // // }
          ]
        }
      ]
    });



    onMounted(() => {
    });
    const handleOpen = (val) => {

    }
    watch(() => router.currentRoute.value.fullPath, (newValue, oldValue) => {
      console.log('watchRouterPathOther', newValue);
      // 反显活动菜单
      // 遍历菜单
      state.menus.forEach(item => {
        if (item.child && item.child.length) {
          item.child.forEach(child => {
            if (child.path == newValue) {
              state.active = child.path
            }
          })
        } else {
          if (item.path == newValue) {
            state.active = item.path
          }
        }
      })

      // state.pageIndex = router.currentRoute.value.query.type
      //         if(newValue=='/pages/accountOrder'||newValue=='/pages/goodList'){
      //           state.pageIndex = 1
      //         }else {
      // state.pageIndex = 0
      //         }
    }, { deep: true, immediate: true })

    router.afterEach((to) => {
      // console.log('to', to)
      // const { id } = to.query
      window.scrollTo(0, 0);
      // if (id && to.name == 'add') {
      //   state.name = '编辑商品'
      // }
    });



    return {
      ...toRefs(state),
      handleOpen
    };
  }
};
  </script>
  
  <style scoped lang="less">
.location {
  display: flex;
  margin-top: 10px;
  margin-bottom: 15px;
}

.container {
  .left {
    width: 190px;
    flex-shrink: 0;
    margin-right: 15px;
  }

  .right {
    width: 100%;
    background-color: #fff;
    min-height: 600px;
    padding: 15px;
    overflow-x: hidden;
  }
}

:deep(.el-menu-item) {
  font-size: 14px;
  color: #666;
}

.el-menu-item.is-active {
  background-color: @primaryColorBg;
  color: #fff;
}

.el-menu-item:hover {
  background-color: @primaryColorBg;
}

:deep(.el-sub-menu__title:hover) {
  background-color: @primaryColorBg;
}

:deep(.el-sub-menu__title) {
  font-size: 14px;
  font-weight: bold;
  color: #333333;
}

:deep(.el-sub-menu) {
  border-bottom: 1px solid #eee;
}
</style>
  