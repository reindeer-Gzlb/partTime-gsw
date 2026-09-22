<template>
  <section class="container">
    <div class="w1200">
      <div class="location">
        <!-- <el-icon class="home-icon" color="#4E5969" :size="15"> -->
        <!-- <House />
        </el-icon> -->
        <el-breadcrumb separator=">">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>账号回收</el-breadcrumb-item>
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
            text-color="#fff"
            active-text-color="#ffd04b"
          >
            <div class="item" style="display: flex" v-for="menu in menus" :key="menu.id">
              <img
                style="width: 24px; height: 24px; margin-top: 15px; margin-left: 20px"
                src="../../assets/index/logoname.png"
                alt=""
              />
              <el-sub-menu v-if="menu.child && menu.child.length" :index="menu.path">
                <template #title>
                  <!-- <el-icon><location /></el-icon> -->
                  <span>{{ menu.name }}</span>
                </template>
                <el-menu-item
                  :class="child.id == obj.id"
                  :index="child.path"
                  v-for="child in menu.child"
                  :key="child.id"
                  @click="changefn(child)"
                  >{{ child.name }}</el-menu-item
                >
              </el-sub-menu>
              <el-menu-item :index="menu.path" v-else>
                <span>{{ menu.name }}</span>
              </el-menu-item>
            </div>
          </el-menu>
          <el-menu
            :default-active="active"
            class="el-menu-vertical-demo"
            @open="handleOpen"
            menu-trigger="click"
            :router="true"
            :unique-opened="true"
            text-color="#fff"
            active-text-color="#ffd04b"
          >
            <div class="item" style="display: flex" v-for="menu in menuchild" :key="menu.id">
              <img
                style="width: 24px; height: 24px; margin-top: 15px; margin-left: 20px"
                src="../../assets/guaishouwan/logoyellow.png"
                alt=""
              />
              <el-sub-menu v-if="menu.child && menu.child.length" :index="menu.path">
                <template #title>
                  <!-- <el-icon><location /></el-icon> -->
                  <span>{{ menu.name }}</span>
                </template>
                <el-menu-item
                  :class="child.id == obj.id ? activeok : ''"
                  :index="child.path"
                  @click="changefn(child)"
                  v-for="child in menu.child"
                  :key="child.id"
                  >{{ child.name }}</el-menu-item
                >
              </el-sub-menu>
              <el-menu-item :index="menu.path" v-else>
                <span>{{ menu.name }}</span>
              </el-menu-item>
            </div>
          </el-menu>
        </div>
        <div class="right">
          <div class="bangzhu">
            <div class="content cooperate topbar">{{ obj.name }}</div>
            <div class="nav">
              <img style="width: 100%" :src="obj.qrCode" alt="" />
            </div>
            <div class="footer">
              <el-button
                @click="goLink('/')"
                style="
                  width: 136px;
                  border: 1px solid #e8572e;
                  color: #e8572e;
                  background-color: #1c1632;
                  border-radius: 20px;
                  margin-right: 30px;
                "
                >返回首页</el-button
              >
              <el-button
              @click="openkefu"
                style="
                  width: 136px;
                  border: 1px solid #e8572e;
                  color: #000;
                  background-color: #e8572e;
                  border-radius: 20px;
                "
                >联系客服</el-button
              >
            </div>
          </div>
          <!-- <router-view /> -->
        </div>
      </div>
    </div>
    <div style="padding-top: 20px"></div>

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
        {
          name: '端游回收',
          id: 3,
          path: 'news',
          child: [

          ]

        }
      ],
      menuchild: [
        {
          name: '手游回收',
          id: 4,
          path: 'anquan',
          child: [

          ]
        }
      ],
      obj: {}
    });

    //获取端游客服
    const getduanyoukefu = async () => {
      const res = await kefu.getsecurity({ type: 6 })
      console.log(res);
      state.menus[0].child = res
      state.obj = res[0]
    }

    //获取端游客服
    const getshouyoukefu = async () => {
      const res = await kefu.getsecurity({ type: 7 })
      console.log(res);
      state.menuchild[0].child = res
    }

    //
    const changefn = (item) => {
      state.obj = item
    }

    // 跳转
    const goLink = (link, query) => {
      if (query) {
        router.push({ path: link, query: query });
      } else {
        router.push({ path: link });
      }
    };

    onMounted(() => {
      getduanyoukefu()
      getshouyoukefu()
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

   const openkefu = () => {
    window.open('https://www30.53kf.com/webCompany.php?arg=10825571&kf_sign=zI0OTMTcxMIzMTEyNDY4NDcyNzMxMDAxNzI4MjU1NzE%253D&style=1')
   }

    return {
      ...toRefs(state),
      handleOpen,
      changefn,
      goLink,
      openkefu
    };
  }
};
</script>

<style scoped lang="less">
.location {
  display: flex;
  // margin-top: 10px;
  padding: 20px 0 10px 0;
  margin-bottom: 15px;
}

.container {
  background-color: #1c1632;
  .left {
    width: 230px;
    flex-shrink: 0;
    height: 321px;
    margin-right: 15px;
    border: 1px solid #2e2a44;
  }

  .right {
    width: 100%;
    background-color: #1c1632;
    min-height: 600px;
    // padding: 15px;
    overflow-x: hidden;
  }
}

.bangzhu {
  width: 939px;
  background-color: #1c1632;
  border: 1px solid #2e2a44;
  min-height: 300px;

  .content {
    padding: 0 15px;
    font-size: 16px;
    color: #fff;
  }

  .nav {
    margin-top: 20px;
    padding: 0 15px;
  }

  .footer {
    text-align: center;
    margin-top: 20px;
    margin-bottom: 30px;
  }
}

.topbar::after {
  content: '';
  position: absolute;
  left: 20px;
  bottom: 0;
  right: 120px;
  width: 100%;
  height: 2px; /* 这是边框的初始宽度 */
  background-image: linear-gradient(to right, #d95132, transparent);
}

.topbar {
  height: 53px;
  line-height: 53px;
  position: relative;
  padding: 2px 10px 12px 20px;
  background-color: #1c1632;
  border-radius: 10px 10px 0 0;

  .tit {
    font-size: 16px;
    font-weight: bold;
    color: #fff;
  }

  .cont {
    .dingjis {
      font-size: 16px;
      font-family: Microsoft YaHei;
      font-weight: 500;
      color: #fff;
      margin-top: 6px;
      margin-left: 10px;
      margin-right: 46px;
    }
    span {
      height: 46px;
      line-height: 46px;
      font-size: 16px;
      cursor: pointer;
      color: #666666;
      margin-right: 30px;
      margin-top: 7px;

      .icon {
        display: none;
        width: 40px;
        height: 4px;
        border-radius: 2px;
        margin: 0 auto;
        margin-top: -6px;
      }

      &.active {
        width: 90px;
        height: 46px;
        text-align: center;
        line-height: 46px;
        background: #96ce20;
        border-radius: 10px 10px 0px 0px;
        color: #fff;
        margin-top: 7px;
      }
    }
  }

  .more {
    font-size: 16px;
    color: #777;
    cursor: pointer;
    display: flex;
    margin-top: 2px;
  }
}

:deep(.el-menu-item) {
  font-size: 14px;
  color: #bdbbbb;
}

.el-menu-item.is-active {
  // background-color: @primaryColorBg;
  color: #fff;
}

.el-menu-item:hover {
  background-color: #1c1632;
  color: #fff;
}

:deep(.el-sub-menu__title:hover) {
  background-color: #1c1632;
  color: #fff;
}

:deep(.el-sub-menu__title) {
  color: #bdbbbb;
  // background-color: @primaryColorBg;
}

:deep(.el-sub-menu__title) {
  font-size: 14px;
  font-weight: bold;
  color: #bdbbbb;
  // background-color: @primaryColorBg;
}

:deep(.el-sub-menu) {
  border-bottom: 1px solid #eee;
}

:deep(.el-menu) {
  background-color: #1c1632;
  // padding-left: 30px;
  margin-left: 10px;
  // border: 1px solid #2e2a44;
}

:deep(.el-sub-menu) {
  border: 0px solid #000;
}

::v-deep .el-menu-item {
  width: 135px;
  overflow: hidden;
  margin-right: -92px;
  // background-color: @primaryColorBg;
}

::v-deep .el-menu-item:hover {
  width: 135px;
  overflow: hidden;
  margin-right: -92px;
  background-color: #452933;
  color: #ff7116;
  border-radius: 10px;
}

:deep(.location .el-breadcrumb__inner) {
  color: #fff;
}

:deep(.location .el-breadcrumb__inner.is-link) {
  color: #fff !important;
}

.activeok {
  background-color: #452933;
  color: #ff7116;
}
</style>
