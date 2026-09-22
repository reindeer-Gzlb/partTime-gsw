<template>
  <div class="header">
    <div class="topbar">
      <div class="w1200 flex fbt">
        <div class="welcome" v-if="!userInfo">
          您好，欢迎来到怪兽玩！游戏交易平台！安全交易！放心交易！找回包赔！
        </div>
        <div v-if="userInfo && userInfo.nickName">
          您好，{{
            userInfo.nickName + '，'
          }}欢迎来到怪兽玩！游戏交易平台！安全交易！放心交易！找回包赔！
        </div>
        <div class="namebox">
          <span v-if="!userInfo" @click="goLink('/login')">登录</span>
          <span v-if="!userInfo" @click="goLink('/login')">免费注册</span>
          <span @click="goLink('/')">首页</span>
          <span @click="goLink('/index/yewujieshao')">业务中心</span>
          <span @click="zhanghaohuishou">账号回收</span>
          <span v-if="userInfo" @click="goLink('/index/other/collect')">我的收藏</span>
          <span v-if="userInfo" @click="goLink('/index/other/suggest')">投诉建议</span>
          <!-- <span @click="goLink('/index/other/gonggao', { notice: 20 })">系统消息</span> -->
          <!-- <span @click="goLink('/index/other/mycenter')">帮助中心</span> -->
          <!-- <span @click="openUrl('https://tb.53kf.com/code/client/548e30ae833331416eeede707988c68c7/1')">联系客服</span> -->
          <span @click="gokefu">联系客服</span>
          <span v-if="userInfo" @click="out()">退出登录</span>
        </div>
      </div>
    </div>
    <div class="header-top">
      <div class="left">
        <div @click="backIndex" class="left-top">
          <img class="title-img" src="@/assets/index/logoname.png" alt srcset />
        </div>
      </div>
      <div class="right">
        <div class="input-select-wrapper">
          <el-input
            class="input-with-select custom-input"
            v-model="searchInput"
            placeholder="请输入搜索关键词"
            @change="searchBtn"
          >
            <template #prepend>
              <el-select
                class="select-wrapper"
                v-model="select"
                placeholder="选择游戏"
                style="width: 115px"
                @change="gameChange"
              >
                <el-option
                  v-for="(item, index) in gameSelects"
                  :key="`game${index}`"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </template>
            <!-- <template> -->

            <!-- </template> -->
          </el-input>
          <el-button @click="searchBtn" class="search-btn-wrapper" type="primary">搜索</el-button>
        </div>
        <!-- <div class="hot-search-text">
          <span><img src="../assets/icon/hot-icon.png" alt="" /></span>
          <span class="text">热门搜索：</span>
          <span class="text" v-for="(item, index) in hotListData" :key="`hot${index}`" @click="hotWordClick(item)">{{ item
          }}</span>
        </div> -->
      </div>
    </div>

    <div class="menu-wrapper">
      <div class="w1200">
        <div
          class="menu-item"
          :class="{ 'active-menu-item': index === pageIndex }"
          @click="menuItemClick(item, index)"
          v-for="(item, index) in menuList"
          :key="`menu${index}`"
        >
          <div style="display: flex; margin: 0 auto">
            <img style="width: 24px; height: 24px; margin-top: 10px" :src="item.img" alt="" />
            <div class="text" style="margin-left: 8px">
              <span>{{ item.value }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, reactive, toRefs, computed, watch } from 'vue';
import { useRouter } from 'vue-router';
// import axios from "@/utils/axios";
import { localRemove, pathMap } from '@/utils';
import { useStore } from 'vuex';
import { Search } from '@element-plus/icons-vue';
import api from '@/api/index';
import huishouApi from '@/api/huishou';
import shouhome from '@/assets/guaishouwan/shouhome.png';
import wym from '@/assets/guaishouwan/wym.png';
import zhhs from '@/assets/guaishouwan/zhhs.png';
import zjdb from '@/assets/guaishouwan/zjdb.png';
import xinwen from '@/assets/guaishouwan/xinwen.png';
export default {
  name: 'Header',
  components: {
    Search
  },
  setup(props, ctx) {
    const router = useRouter();
    const store = useStore();

    const state = reactive({
      tipname: 'header',
      userInfo: null,
      hasBack: false,
      hotListData: ['王者荣耀', '星元', '和平', '盲僧'],
      gameSelects: [],
      //
      menuList: [
        {
          key: 0,
          value: '首页',
          path: '/',
          img: shouhome
        },
        {
          key: 1,
          value: '我要买',
          path: '/index/accountOrder',
          img: wym
        },
        {
          key: 2,
          value: '我要卖',
          path: '/index/zjdb',
          img: zjdb
        },
        {
          key: 3,
          value: '租号中心',
          // path: '/index/appDownload'
          path: '/index/helpcenter',
          img: xinwen
        },
        {
          key: 4,
          value: '业务介绍',
          path: '/index/yewujieshao',
          img: xinwen
        },
        {
          key: 5,
          value: '个人中心',
          path: '/index/other/mycenter',
          img: zhhs
        }
      ],

      pageIndex: 0,
      select: null,
      searchInput: null,
      selectGame: {},
      link: null
    });

    const nameClick = (value) => {
      console.log(value);
    };

    const searchBtn = () => {
      if (!state.select) return alert('请先选择游戏！');
      router.push({
        path: `/index/goodList`,
        query: {
          type: state.selectGame.type,
          searchText: state.searchInput,
          name: state.selectGame.name,
          id: state.selectGame.id
        }
      });
    };
    // 游戏改变
    const gameChange = (e) => {
      console.log('%c Line:144 🍬 e', 'color:#fca650', e);
      state.select = Number(e);
      const arr = state.gameSelects.filter((r) => {
        return r.id == e;
      });
      state.selectGame = arr[0];
    };

    const hotWordClick = (item) => {
      // state.searchInput = item;
      const arr = state.gameSelects.filter((r) => {
        return r.name == item;
      });
      state.selectGame = arr[0];
      state.select = state.selectGame.id;
      searchBtn();
    };

    onMounted(() => {
      // state.select = router.currentRoute.value.query.id;
      GetGameType();
      getZuhaoLink();
    });
    // 获取游戏类型
    const GetGameType = async () => {
      let games = await api.GetGameType();
      state.gameSelects = JSON.parse(JSON.stringify(games.data));
      // 截取gameTypeList前六个添加到hotListData里面
      state.hotListData = state.gameSelects.slice(0, 6).map((item) => item.name);
    };

    // 事件触发
    function menuItemClick(item, index) {
      console.log('index', index);
      if (index == 3) {
        window.open(state.link);
      } else {
        state.pageIndex = index;
        router.push({ path: item.path });
      }
    }

    const getZuhaoLink = async () => {
      const res = await huishouApi.getDict('RentLink');
      console.log('res', res);
      state.link = res.data[0].dictValue;
    };

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
        if (router.currentRoute.value.path === '/index/goodList') {
          router.currentRoute.value.query.id && gameChange(router.currentRoute.value.query.id);
        }
      },
      { deep: true }
    );
    watch(
      () => router.currentRoute.value,
      (newValue, oldValue) => {
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
          newValue.path === '/index/goodList' ||
          newValue.path === '/index/accountOrder' ||
          newValue.fullPath.indexOf('pageType=buy') > -1 ||
          newValue.path === '/pages/detail/index'
        ) {
          state.pageIndex = 1;
        } else if (newValue.path === '/index/sell' || newValue.path === '/index/BasicInformation') {
          state.pageIndex = 0;
        } else if (
          newValue.path === '/index/security' ||
          newValue.fullPath.indexOf('pageType=hire') > -1
        ) {
          state.pageIndex = 3;
        } else if (
          newValue.path === '/index/other/mycenter' ||
          newValue.path.indexOf('other') > -1
        ) {
          state.pageIndex = 5;
        } else if (newValue.path === '/index/yewujieshao') {
          state.pageIndex = 4;
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
        window.localStorage.setItem('userInfo', '');
        router.push({ path: '/login' });
      });
    };

    const gokefu = () => {
      window.open(
        'https://www30.53kf.com/webCompany.php?arg=10825571&kf_sign=zI0OTMTcxMIzMTEyNDY4NDcyNzMxMDAxNzI4MjU1NzE%253D&style=1'
      );
    };

    const zhanghaohuishou = () => {
      router.push('/index/huishouOther/kefu');
    };

    return {
      ...toRefs(state),
      nameClick,
      menuItemClick,
      searchChange,
      searchBtn,
      hotWordClick,
      backIndex,
      goLink,
      openUrl,
      out,
      gameChange,
      gokefu,
      zhanghaohuishou
    };
  }
};
</script>

<style scoped lang="less">
.header {
  // height: 180px;
  // border-bottom: 1px solid #e9e9e9;

  // padding: 0 0 8px;
  background-color: #1c1632;
  box-sizing: border-box;

  .topbar {
    height: 36px;
    line-height: 36px;
    background: #1c1632;
    font-size: 14px;
    margin: 0;
    margin-bottom: 20px;
    border-bottom: 1px solid #29243e;
    color: #fff;

    .w1200 {
      span {
        margin-left: 20px;
        cursor: pointer;
      }
    }
  }

  .header-top {
    width: 1200px;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    align-items: center;
    position: relative;
  }
}

.menu-wrapper {
  height: 50px;
  background: linear-gradient(
    180deg,
    rgba(215, 84, 51, 0),
    rgba(215, 84, 51, 0) 66%,
    rgba(215, 84, 51, 0.2) 100%
  );
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  // border-bottom: 1px solid #332E47;
  // background: linear-gradient(to bottom, #1c1632, #1c1632, #1c1632, #1c1632, #482433);
  color: #fff;
  margin-top: 28px;
  font-size: 18px;

  .w1200 {
    margin-top: 20px;
    display: flex;
    align-items: end;
  }

  .menu-item {
    position: relative;
    display: flex;
    font-weight: medium;
    line-height: 50px;
    color: #fff;
    cursor: pointer;
    width: 200px;
    height: 50px;
    text-align: center;

    // margin-right: 10px;
    padding: 0 10px;
    .text {
      width: 100%;
      display: inline-block;
      // background: #FFFFFF;
      border-radius: 5px 5px 0px 0px;
      height: 50px;
      margin-top: 6px;
      line-height: 30px;
      position: relative;
      font-size: 16px;
      // position: absolute;
      // left: 0;
      // bottom: 0;
      // border-right: 1px solid #fff;
    }

    // .icon {
    //   display: inline-block;
    //   width: 2px;
    //   height: 26px;
    //   background: rgba(255, 255, 255, 0.5);
    //   // margin-top: 12px;
    //   position: absolute;
    //   right: 0;
    //   top: 7px;
    // }

    &:hover {
      color: #fff;
      background: radial-gradient(rgb(255 86 74), rgb(146 54 47 / 0%), rgb(226 62 38 / 0%));
      background-position: -53px -120px;
      background-repeat: no-repeat;
      background-size: 299px 196px;

      .text {
        // background: #af4042;
      }

      &::before {
        content: '';
        bottom: 3px;
        left: 50%;
        transform: translateX(-50%);
        position: absolute;
        width: 50px;
        height: 0px;
        border: 2px solid;
        border-image: linear-gradient(
            87deg,
            rgb(229 56 34) 0%,
            rgb(241 154 142) 45%,
            #ffffff 47%,
            #ffffff 53%,
            rgb(241 154 142) 55%,
            rgb(229 56 34) 100%
          )
          4 4;
        filter: blur(3px);
      }
    }
  }

  .active-menu-item {
    // background: #96ce20;
    color: #ff7116;
    // background:rgba(255,255, 255, 0.9);
    // background-size: 100% 50%;
    position: relative;
    background: radial-gradient(rgb(255 86 74 / 100%), rgb(146 54 47 / 0%), rgb(226 62 38 / 0%));
    background-position: -53px -120px;
    background-repeat: no-repeat;
    background-size: 299px 196px;

    .text {
      // background: #af4042;
    }

    &::before {
      content: '';
      bottom: 3px;
      left: 50%;
      transform: translateX(-50%);
      position: absolute;
      width: 50px;
      height: 0px;
      border: 2px solid;
      border-image: linear-gradient(
          87deg,
          rgb(229 56 34) 0%,
          rgb(241 154 142) 45%,
          #ffffff 47%,
          #ffffff 53%,
          rgb(241 154 142) 55%,
          rgb(229 56 34) 100%
        )
        4 4;
      filter: blur(3px);
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
  margin-left: 50px;

  .left-top {
    .title-img {
      width: 100px;
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
  right: 50px;
  width: 700px;
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
  // border: 2px solid #000;
  border: 1px solid #29243e;
  margin-bottom: 10px;
  border-radius: 7px !important;
  display: flex;
  justify-content: space-between;
}

.select-wrapper {
  color: #000000 !important;
  font-weight: medium !important;
}

.search-btn-wrapper {
  width: 126px !important;
  color: #fff !important;
  height: 42px;
  background: #ee3e23 !important;
  margin-left: 20px;

  .search-icon {
    margin-right: 10px;
    font-weight: 700;
  }
}

:deep(.el-input-group__append) {
  width: 89px !important;
  padding: 0;
  margin: 0;
}

:deep(.el-input-group__prepend) {
  background-color: #1c1632;
}

:deep(.el-input__wrapper) {
  background-color: #1c1632;
}

:deep(.el-select__wrapper.el-tooltip__trigger) {
  border: none !important;
  height: 42px;
}

:deep(.el-input .el-input-group .el-input-group--prepend .input-with-select .custom-input) {
  border-radius: 20px;
}

:deep(.el-input__wrapper .el-input__inner) {
  color: #fff;
  background-color: #1c1632;
}

:deep(.el-select__selected-item) {
  color: #fff;
}

:deep(.el-icon .el-select__caret .el-select__icon) {
  color: #fff;
}

.custom-input {
  --el-input-focus-border: #dcdfe6;
  --el-input-focus-border-color: #dcdfe6;
}

/* 假设你的Vue项目使用了scoped样式，需要使用深度选择器 >>> */
:deep(.el-select .el-input .is-focus .el-input__inner) {
  background-color: #f56c6c !important; /* 你想要的背景色 */
}
</style>