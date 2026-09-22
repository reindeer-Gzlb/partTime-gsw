<template>
  <div class="header">
    <!-- 头部导航 -->
    <div class="topbar w1300 flex">
      <div class="menu-item" :class="{ 'active': index === pageIndex }" @click="pageIndex = index;menuItemClick(item, index);" v-for="(item,index) in menuList" :key="index">
        {{item.value}}
      </div>
      <div class="right_con">
          <div class="no_login" v-if="userInfo == null">
            <span @click="goLink('/login')">登录/注册</span>
          </div>
          <el-dropdown v-else>
            <div class="user-box flex">
              <div class="username font14">{{ userInfo.nickName }}</div>
              <div class="head-img">
                <img :src="userInfo.avatar||'../assets/shanggu/home/default-head.png'" alt="" />
              </div>
              
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="show(1)">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
    </div>
  </div>
  <!-- 退出登录 -->
  <ElMessageBox :is_show="is_shows" @update="handleChildEvent">
    <div class="is_shows" @click.stop="on">
      <img
        class="icon_cha"
        src="@/assets/fh-icon/cha.png"
        alt=""
        @click.stop="handleChildEvent(1)"
      />
      <h2>退出登录</h2>
      <div class="content">
        <p>确定要退出当前登录账号吗？</p>
        <div class="but">
          <div class="quxiao" @click.stop="handleChildEvent(1)">取消</div>
          <div class="queding" @click.stop="handleChildEvent(2)">确定</div>
        </div>
      </div>
    </div>
  </ElMessageBox>
</template>

<script>
import { onMounted, reactive, toRefs, computed, watch } from 'vue';
import { useRouter } from 'vue-router';
// import axios from "@/utils/axios";
import { localRemove, pathMap } from '@/utils';
import { useStore } from 'vuex';
import { Search } from '@element-plus/icons-vue';
import api from '@/api/index';
import kefu from '@/api/kefu';
import { ElMessageBox } from 'element-plus';
import { ElMessage } from 'element-plus';
import EgeBox from './ElMessageBox.vue';

export default {
  name: 'Header',
  components: {
    Search,
    EgeBox
  },
  setup(props, ctx) {
    const router = useRouter();
    const store = useStore();

    const state = reactive({
      is_show: false,
      is_shows: false,
      login_ye: false,
      userInfo: {},
      nickName: '', // 昵称
      login_list: false,
      gameTypeList: [],
      sousuo_text: {
        keywords: ''
      },
      sousuo: false,
      tipname: 'header',
      hasBack: false,
      hotListData: [],
      //
      menuList: [
        {
          key: 0,
          value: '首页',
          path: '/'
        },
        {
          key: 1,
          value: '我要买',
          path: '/index/accountOrder'
        },
        {
          key: 2,
          value: '我要卖',
          path: '/index/sell'
        },
        {
          key: 3,
          value: '消息中心',
          path: '/index/security'
        },
        {
          key: 4,
          value: '个人中心',
          path: '/index/other/mycenter'
        },
        // {
        //   key: 5,
        //   value: 'APP下载',
        //   path: '/index/appDownload'
        // }
      ],

      pageIndex: 0,
      select: null,
      searchInput: null
    });

    // 搜索
    const gosousuo = (item) => {
      if (item.keywords) {
        router.push({
          path: '/index/home',
          query: { keyword: item.keywords, type: item.type, id: item.id }
        });
      } else {
        router.push({ path: '/index/home' });
      }
    };
    const sousuo_go = (item) => {
      state.sousuo_text = item;
      state.sousuo = !state.sousuo;
      // 把item添加到state.hotListData前面并且删除state.hotListData里相同的并且只保留state.hotListData的前六个
      let index = state.hotListData.findIndex((value) => value.name === item.name);
      if (index !== -1) {
        state.hotListData.splice(index, 1);
      }
      state.hotListData.unshift(item);
      if (state.hotListData.length > 6) {
        state.hotListData.splice(6, state.hotListData.length - 6);
      }
    };
    const searchBtn = () => {
      router.push({
        path: `/index/goodList`,
        query: { type: state.select, searchText: state.searchInput, scrollTop: 0 }
      });
    };

    const hotWordClick = (item) => {
      state.searchInput = item;
      searchBtn();
    };

    onMounted(() => {
      // 获取本地存储的userInfo
      GetGameType();
      state.select = router.currentRoute.value.query.type;

      // 获取本地存储的userInfo
      state.userInfo = JSON.parse(localStorage.getItem('userInfo'));
      if (state.userInfo) {
        if (state.userInfo.nickName && state.userInfo.nickName.length === 11) {
          console.log(
            state.userInfo.nickName.slice(0, 3) + '****' + state.userInfo.nickName.slice(7)
          );
          state.nickName =
            state.userInfo.nickName.slice(0, 3) + '****' + state.userInfo.nickName.slice(7);
        } else {
          state.nickName = state.userInfo.nickName;
        }
      }
    });

    // 获取游戏类型
    const GetGameType = async () => {
      let games = await api.GetGameType();
      state.gameTypeList = JSON.parse(JSON.stringify(games.data));
      // 截取gameTypeList前六个添加到hotListData里面
      state.hotListData = state.gameTypeList.slice(0, 6).map((item) => item);
    };

    // 事件触发
    function menuItemClick(item, index) {
      state.pageIndex = index;
      router.push({ path: item.path });
    }

    const searchChange = (value) => {
      // store.commit('setCurrentGameName', value);
      searchBtn();
    };

    const backIndex = () => {
      router.push({ path: `/index/home` });
    };

    watch(
      () => router.currentRoute.value.query,
      (newValue, oldValue) => {
        state.select = router.currentRoute.value.query.type;
      },
      { deep: true }
    );

    watch(
      () => router.currentRoute.value,
      (newValue, oldValue) => {
        if (newValue.path === '/login' || newValue.path === '/register') {
          state.login_ye = false;
        } else {
          state.login_ye = true;
        }
        // 获取个人信息
        let info = window.localStorage.getItem('userInfo');
        if (info) {
          // 判断info.nickName是不是手机号，是就手机号私密处理
          state.userInfo = JSON.parse(info);
        }
        // 判断当前页面
        if (newValue.path === '/index/home') {
          state.pageIndex = 0;
        } else if (
          newValue.path === '/index/accountOrder' ||
          newValue.fullPath.indexOf('pageType=buy') > -1 ||
          newValue.path === 'pages/detail/index'
        ) {
          state.pageIndex = 1;
        } else if (newValue.path === '/index/sell' || newValue.path === '/index/BasicInformation') {
          state.pageIndex = 2;
        } else if (
          newValue.path === '/index/security' ||
          newValue.fullPath.indexOf('pageType=hire') > -1
        ) {
          state.pageIndex = 3;
        } else if (
          newValue.path === '/index/other/mycenter' ||
          newValue.path.indexOf('other') > -1
        ) {
          state.pageIndex = 4;
        } else if (newValue.path === '/index/appDownload') {
          state.pageIndex = 5;
        }
      },
      { deep: true, immediate: true }
    );

    const back = () => {
      router.back();
    };

    // 前置配置路由守卫
    router.beforeEach((to, from, next) => {
      // document.getElementById('maincontent').scrollTop = 0
      //如果访问主页，则放行
      if (to.path === '/home') {
        state.pageIndex = 0;
      }
      //如果用户未登陆，则跳转到主页
      // const user = sessionStorage.getItem('user')
      // if (!user) {
      //   return next('/')
      // }
      //如果用户已登陆，则放行
      next();
    });
    // 跳转
    const goLink = (link, query) => {
      handleChildEvent(1);
      if (query) {
        router.push({ path: link, query: query });
      } else {
        router.push({ path: link });
      }
    };
    const openUrl = (val) => {
      window.open(val);
    };
    const out = () => {
      ElMessageBox.confirm('确定退出登录吗？', '提示', {
        showCancelButton: false,
        'confirm-button-text': '确定',
        type: 'warning'
      }).then(() => {
        window.localStorage.clear();
      });
    };

    // 退出登录
    const secede = () => {
      // 清空本地存储的userInfo和token
      localRemove('userInfo');
      localRemove('token');
      // 跳转到登录页面
      // router.push('/login');
    };

    // 开
    const show = (item) => {
      state.is_shows = true;
    };
    // 关
    const handleChildEvent = (item) => {
      if (item == 1) {
        state.is_show = false;
        state.is_shows = false;
      } else {
        state.is_shows = false;
        // 退出登录，清空本地存储的userInfo和token
        localRemove('userInfo');
        localRemove('token');
        // 跳转到登录页面
        router.push('/login');
        // router.push({ path: '/login' });
        // 获取本地存储的userInfo
        let info = window.localStorage.getItem('userInfo');
        state.userInfo = info;

        console.log(info);
        ElMessage({
          message: '退出成功！',
          type: 'success'
        });
      }
      // 让浏览器滚动条显示
      document.documentElement.style.overflow = 'auto';
    };

    //
    const on = (val) => {
      console.log(val);
    };

    return {
      ...toRefs(state),
      menuItemClick,
      searchChange,
      searchBtn,
      hotWordClick,
      backIndex,
      goLink,
      openUrl,
      out,
      gosousuo,
      GetGameType,
      sousuo_go,
      secede,
      show,
      handleChildEvent,
      on
    };
  }
};
</script>

<style scoped lang="less">
@primaryColor: #333;

.header {
  box-sizing: border-box;
  width: 100%;
  height: 72px;
  background: #000000;
  font-size: 18px;
  color: #FFFFFF;
  .topbar{
    height: 100%;
    align-items: center;
    justify-content: flex-end;
    .menu-item{
      cursor: pointer;
      margin-right: 80px;
      
      &.active{
        font-weight: 600;
        background:linear-gradient(180deg, #6DD400 0%, #F7B500 100%);
        -webkit-background-clip: text;
         -webkit-text-fill-color: transparent;
      }
    }
    .right_con {
      width: 200px;
      height: 100%;
      line-height: 72px;
      color: #fff;
      display: flex ;
      align-items: center;
        .login {
          margin-left: 20px;
          cursor: pointer;
        }

        .no_login {
          color: #EFA600;
          span {
            margin-left: 10px;
            cursor: pointer;
            font-size: 18px;
          }
        }

        img {
            width: 40px;
            cursor: pointer;
          }
          .nick-name{
            cursor: pointer;
            color: #EFA600;
            font-size: 16px;
            margin-right: 10px;
          }
          .user-box {
            align-items: center;
            cursor: pointer;
            .head-img {
              width: 38px;
              height: 38px;
              border-radius: 50%;
              overflow: hidden;
              img {
                width: 100%;
                height: 100%;
              }
            }
            .username {
              
              color: #EFA600;
              font-weight: 600;
              margin-right: 10px;
            }
          }
      }
  }
}

.is_show {
  width: 310px;
  height: 590px;
  background-color: #fff;
  border-radius: 10px;
  // position: absolute;
  position: fixed;
  top: 10%;
  left: 72%;
  padding: 0;
  // margin-top: 100px;
  .top {
    width: 100%;
    border-bottom: 1px solid #eaeaea;
    padding: 25px 0 17px 25px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-sizing: border-box;

    .img {
      img {
        width: 63px;
        height: 63px;
        border-radius: 50%;
        margin-right: 13px;
      }
    }

    .xinxi {
      h4 {
        font-size: 18px;
        font-weight: 500;
        margin: 10px 0;
      }

      p {
        font-size: 15px;
        margin: 10px 0;
      }
    }

    .shiming {
      height: 28px;
      background-color: #FF8E00;
      color: #fff;
      padding: 0 8px;
      border-radius: 17px 0 0 17px;
      display: flex;
      align-items: center;

      img {
        width: 15px;
        height: 17px;
        margin-right: 5px;
      }

      span {
        background-color: #FF8E00;
        color: #fff;
        border-radius: 17px 0 0 17px;
      }
    }
  }

  .nav_list {
    width: 100%;

    ul {
      margin: 0;
      padding: 0;

      li {
        display: flex;
        align-items: center;
        margin-top: 30px;
        margin-left: 70px;
        cursor: pointer;

        img {
          width: 22px;
          height: 24px;
          margin-right: 42px;
        }

        &:hover {
          color: #FF8E00;
        }
      }
    }
  }
}

.is_shows {
  width: 500px;
  height: 300px;
  background-color: #fff;
  border-radius: 10px;
  position: fixed;
  top: 40%;
  right: 25%;
  transform: translate(-50%, -50%);
  z-index: 9999;

  .icon_cha {
    width: 25px;
    height: 25px;
    position: absolute;
    top: 20px;
    right: 20px;
    cursor: pointer;
  }

  h2 {
    text-align: center;
    line-height: 3;
    font-weight: 500;
  }

  .content {
    width: 100%;
    padding: 0 30px;
    box-sizing: border-box;

    p {
      text-align: center;
      font-size: 16px;
      font-weight: 500;
      margin-top: 40px;
    }

    .but {
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 60px;
      box-sizing: border-box;
      margin-top: 50px;

      .quxiao {
        width: 35%;
        height: 42px;
        text-align: center;
        line-height: 42px;
        border-radius: 5px;
        color: #FF8E00;
        border: 1px solid #FF8E00;
        background-color: #fff;
        cursor: pointer;
      }

      .queding {
        cursor: pointer;
        width: 35%;
        height: 42px;
        text-align: center;
        line-height: 42px;
        border-radius: 5px;
        color: #fff;
        background-color: #FF8E00;
        border-color: #FF8E00;
        margin-left: 0;
      }
    }
  }
}

.menu-box {
  align-items: center;
}

.menu-wrapper {
  height: 50px;
  line-height: 50px;
  color: #666666;
  font-size: 18px;
  display: flex;

  .menu-item {
    height: 50px;
    position: relative;
    display: flex;
    font-weight: medium;
    cursor: pointer;
    text-align: center;
    padding: 0 30px;
    margin-right: 30px;
  }

  .active-menu-item {
    background-color: #EFF7FF;
    border-bottom: 2px solid #FF8E00;

    .text {
      color: #000;
    }
  }
}

.userinfo {
  margin-left: 30px;

  // width: 200px;
  .login-reg-btn {
    padding: 0 15px;
    height: 30px;
    background: #ffffff;
    border-radius: 15px;
    color: @primaryColor;
    line-height: 30px;
    font-size: 14px;

    span {
      cursor: pointer;
    }
  }

  .username {
    display: flex;
    align-items: center;
    text-decoration: underline;
    cursor: pointer;

    .headImg {
      width: 50px;
      height: 50px;
      border-radius: 50%;
      margin-left: 5px;
    }
  }
}

.input-with-select .el-input-group__prepend {
  // background-color: var(--el-fill-color-blank);
}

.left {
  display: flex;
  justify-content: space-between;
  flex-direction: column;

  .left-top {
    .title-img {
      width: 150px;
      height: auto;
      cursor: pointer;
    }
  }
}

.right {
  display: flex;
  position: absolute;
  flex-direction: column;
  justify-content: center;
  right: 0;
  width: 640px;
}

.hot-search-text {
  font-size: 14px;
  color: #aaa;

  img {
    width: 22px;
    height: 22px;
    vertical-align: middle;
    margin-right: 10px;
  }

  .text {
    margin-right: 10px;
    cursor: pointer;
  }
}

/* .menu-item:not(:last-child)::after{
  display:inline-block;
  content: "";
  width:2px;
  height:20px;
  background-color:black;
  margin-left: 10px;
} */

.el-icon-back {
  border: 1px solid #e9e9e9;
  padding: 4px;
  border-radius: 50px;
  margin-right: 10px;
}

.right > div > .icon {
  font-size: 18px;
  margin-right: 6px;
}

.author {
  margin-left: 10px;
  cursor: pointer;
}

.input-select-wrapper {
  border: 2px solid @primaryColor;
  margin-bottom: 10px;
  border-radius: 7px !important;
}

.select-wrapper {
  color: #000000 !important;
  font-weight: medium !important;
}

.search-btn-wrapper {
  width: 110px !important;
  color: #fff !important;
  height: 42px;
  background: @primaryColor !important;
  border-radius: 0px 5px 5px 0px !important;

  .search-icon {
    margin-right: 10px;
    font-weight: 700;
  }
}

.taohaoqu {
  color: #FF8E00;
  font-size: 14px;
}

:deep(.el-input-group__append) {
  width: 89px !important;
  padding: 0;
  margin: 0;
}
</style>
