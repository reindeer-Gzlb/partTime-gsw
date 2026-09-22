<template>
  <div style="background-color: #1c1632;">
    <section class="container">
      <div class="w1000">
        <div class="cont flex">
          <div class="left">
            <ul>
              <li
                v-for="item in list"
                :key="item.id"
                @click="goLink(item)"
                :class="class_id == item.id ? 'class_id' : ''"
              >
                <img :src="item.iconActive" alt="" v-if="class_id == item.id" />
                <img :src="item.icon" alt="" v-else />
                <p>{{ item.name }}</p>
              </li>
            </ul>
          </div>

          <div class="right">
            <router-view />
          </div>
        </div>
      </div>
      <!-- 底部 -->
    </section>
    <Footer />
  </div>
</template>
  
  <script>
import { RefreshRight, CaretTop, Search, CaretBottom, House, View } from '@element-plus/icons-vue';
import Footer from '@/components/Footer.vue';
import { reactive, toRefs, onMounted, onUnmounted, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import li0 from '../../assets//fh-icon/li0.png';
import li1 from '../../assets//fh-icon/li1.png';
import li2 from '../../assets//fh-icon/li2.png';
import li3 from '../../assets//fh-icon/li3.png';
import li4 from '../../assets//fh-icon/li4.png';
import li5 from '../../assets//fh-icon/li5.png';
import li6 from '../../assets//fh-icon/li6.png';
import li7 from '../../assets//fh-icon/li7.png';
import li8 from '../../assets//cebian/li8.png';
import li9 from '../../assets//cebian/li9.png';
import li100 from '../../assets//cebian/li9.png';

import li00 from '../../assets//fh-icon/li00.png';
import li11 from '../../assets//fh-icon/li11.png';
import li22 from '../../assets//fh-icon/li22.png';
import li33 from '../../assets//fh-icon/li33.png';
import li44 from '../../assets//fh-icon/li44.png';
import li55 from '../../assets//fh-icon/li55.png';
import li66 from '../../assets//fh-icon/li66.png';
import li77 from '../../assets//fh-icon/li77.png';
import li88 from '../../assets//cebian/li88.png';
import li99 from '../../assets//cebian/li99.png';
import li1000 from '../../assets//cebian/li99.png';

export default {
  name: 'other',
  components: { House, RefreshRight, Search, CaretBottom, CaretTop, View, Footer },
  setup(props, ctx) {
    const router = useRouter();
    const state = reactive({
      fullPath: '',
      class_id: 0,
      class_ziji_id: 1.1,
      active: '',
      activeName: '最新公告',
      list: [
        {
          name: '个人中心',
          id: 0,
          path: '/index/other/mycenter',
          icon: li00,
          iconActive: li00
        },
        {
          name: '密码管理',
          id: 1,
          path: '/index/other/passwore',
          icon: li11,
          iconActive: li11
        },
        // {
        //   name: '我的买单',
        //   id: 2,
        //   path: '/index/other/MyProducts',
        //   icon:li2,
        //   iconActive:li22
        // },
        // {
        //   name: '竞价中心',
        //   id: 10,
        //   path: '/index/other/myJingjiahuishou',
        //   icon:li100,
        //   iconActive:li1000
        // },
        {
          name: '我的代售',
          id: 3,
          path: '/index/other/Mybrowsing',
          icon: li33,
          iconActive: li33
        },
        {
          name: '我的收藏',
          id: 4,
          path: '/index/other/collect',
          icon: li44,
          iconActive: li44
        },
        // {
        //   name: '帮助中心',
        //   id: 5,
        //   path: '/index/other/bangzhu',
        //   icon: li55,
        //   iconActive: li55
        // },
        // {
        //   name: '联系客服',
        //   id: 6,
        //   path: '/index/accountOrder',
        //   icon:li6,
        //   iconActive:li66,
        //   query:{
        //     to:'kefu'
        //   }
        // },
        {
          name: '投诉建议',
          id: 8,
          path: '/index/other/suggest',
          icon: li99,
          iconActive: li99
        },
        {
          name: '关于我们',
          id: 7,
          path: '/index/other/cooperate',
          icon: li77,
          iconActive: li77
        },
        
        //   {
        //     name: '商务合作',
        //     id: 8,
        //     path: '/index/other/shanghu',
        //     icon:li8,
        //     iconActive:li88
        //   },
        //   {
        //     name: '新闻公告',
        //     id: 9,
        //     path: '/index/other/xinwen',
        //     icon:li9,
        //     iconActive:li99
        //   },
        //   {
        //     name: '找回案例',
        //     id: 12,
        //     path: '/index/other/zhaohui',
        //     icon:li9,
        //     iconActive:li99
        //   },
        //   {
        //     name: '人才招募',
        //     id: 13,
        //     path: '/index/other/zhaomu',
        //     icon:li9,
        //     iconActive:li99
        //   }
      ]
    });

    onMounted(() => {
      // //console.log('routerrouterrouter', router.currentRoute.value.fullPath);
      state.fullPath = router.currentRoute.value.fullPath;
      // //console.log('state.fullPath', state.fullPath);
    });
    const handleOpen = (val) => { };
    watch(
      () => router.currentRoute.value.fullPath,
      (newValue, oldValue) => {
        state.fullPath = router.currentRoute.value.fullPath;
        state.list.forEach((item, index) => {
          if (item.path == state.fullPath) {
            state.class_id = item.id;
          }
        });
      },
      { deep: true, immediate: true }
    );

    router.afterEach((to) => {
      window.scrollTo(0, 0);
    });

    // 跳转
    const goLink = (item) => {
      state.class_id = item.id;
      //console.log('state.class_id', state.class_id);
      if (item.query) {
        router.push({ path: item.path, query: item.query });
      } else {
        router.push({ path: item.path });
      }
    };

    return {
      ...toRefs(state),
      handleOpen,
      goLink
    };
  }
};
  </script>
  
  <style scoped lang="less">
* {
  list-style: none;
  text-decoration: none;
}

.location {
  display: flex;
  margin-top: 10px;
  // margin-bottom: 15px;
}

.container {
  width: 1200px;
  margin: 0 auto;
  padding-top: 20px;
  min-height: 400px;
  margin-bottom: 30px;

  .w1000 {
    width: 100%;
    height: 100%;
  }

  .cont {
    height: 100%;
    width: 100%;
  }

  .left {
    height: 100%;
    width: 226px;
    background-color: #211c38;
    border: 1px solid #6f6e6e;
    text-align: center;
    border-radius: 8px;
    // padding-bottom: 20px;

    ul {
      width: 100%;
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      margin: 0;
      padding: 0;

      > li {
        width: 100%;
        height: 67px;
        font-size: 18px;
        line-height: 67px;
        cursor: pointer;
        border-bottom: 1px solid transparent;
        display: flex;
        align-items: center;
        justify-content: center;
        color: #fff;

        img {
          width: 28px;
          height: 25px;
          margin-right: 35px;
        }

        &:hover {
          p {
            color: #ee3e23;
          }
        }
      }

      li:nth-of-type(8) {
        img {
          width: 28px;
          height: 28px;
        }
      }

      .class_id {
        background-color: #ee3e23;
        color: #fff;
        font-weight: 550;
      }

      .class_id:hover {
        p {
          color: #fff;
        }
      }
    }
  }

  .right {
    width: 100%;
    height: 100%;
    margin-left: 25px;
    box-sizing: border-box;
    width: 933px;
  }
}

.fixed {
  position: fixed;
  right: 0px;
  bottom: 44.2%;
  transform: translateY(50%);
  z-index: 999;
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