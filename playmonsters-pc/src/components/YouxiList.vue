<template>
  <section class="container">
    <!-- 切换 -->
    <div class="youxi">
      <div
        class="one"
        :class="{ activeTitle: ids == item.id }"
        @click="changeyanzheng(item)"
        v-for="(item, index) in arr"
        :key="index"
      >
        <i v-if="ids == item.id"></i>
        <span>{{ item.name }}</span>
      </div>
      <div class="flex" style="margin-left: 600px">
        <el-input
          v-model="params.name"
          class="w-50 m-2"
          placeholder="请输入游戏名称"
          @change="GetGameType"
          clearable
        />
        <el-button
          style="
            background-color: #e75d31;
            color: #fff;
            border: none;
            height: 39px;
            margin-left: 6px;
          "
          >搜索</el-button
        >
      </div>
    </div>
    <div class="game-type-box">
      <!-- <div class="zhanghaos">账号</div> -->
      <div class="bottom">
        <!-- 遍历26个字母 -->
        <div class="li" :class="{ on: params.initial == '' }">
          <span @click="charChange('')">全部</span>
        </div>
        <div
          class="li"
          :class="{ on: params.initial == String.fromCharCode(65 + index) }"
          v-for="(item, index) in 26"
          :key="index"
          @click="charChange(String.fromCharCode(65 + index))"
        >
          <span>{{ String.fromCharCode(65 + index) }}</span>
        </div>
      </div>
      <!-- 最近浏览 -->
      <div class="zjll">
        <div>最近浏览</div>
        <div class="game-items-wrapper">
          <div
            class="game-item"
            @click="gameTypeClick(item)"
            v-for="(item, index) in recently_list"
            :key="`game${index}`"
          >
            <div class="game-icon">
              <img :src="item.icon" style="width: 100%; height: auto" alt />
            </div>
            <div class="name">{{ item.name }}</div>
          </div>
        </div>
      </div>
      <!-- 游戏大全 -->
      <div class="game-items-wrapper">
        <div
          class="game-item"
          @click="gameTypeClick(item)"
          v-for="(item, index) in gameTypeList"
          :key="`game${index}`"
        >
          <div class="game-icon">
            <img :src="item.icon" style="width: 100%; height: auto" alt />
          </div>
          <div class="name">{{ item.name }}</div>
        </div>
      </div>
    </div>
    <kefuDialog ref="kefu" />
  </section>
</template>
  
<script>
import { RefreshRight, CaretTop, CaretBottom, Search, House, View } from '@element-plus/icons-vue';
import Footer from '@/components/Footer.vue';
import { reactive, toRefs, onMounted, watch, ref } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/api/index';
import kefuApi from '@/api/kefu';
import { useStore } from 'vuex';
import kefuDialog from '../views/home/kefuDialog.vue';
import { ElMessage } from 'element-plus'
export default {
  name: 'accountorder',
  components: { House, RefreshRight, CaretBottom, CaretTop, View, Footer, kefuDialog },
  props: {
    golist: {
      type: String,
      default: 'buy'
    }
  },
  setup(props, { emit }) {
    const state = reactive({
      gameType: '全部游戏',
      gameTypeList: [],
      recently_list: [],
      gameActive: 0,
      typeActive: '1',

      params: {
        type: '',
        initial: '',
        name: ''
      },
      arr: [
        { name: '全部游戏', id: '' },
        { name: '端游', id: '1' },
        { name: '手游', id: '0' }
      ],
      ids: ''
    });
    const kefu = ref();
    const store = useStore();
    const router = useRouter();
    const gameTypeClick = (item) => {
      console.log('55555555555', props.golist);
      if (props.golist == 'sale') {
        kefuApi.getMyBrowse().then((res) => {
          if (res.code == 200) {
            router.push({
              path: '/index/BasicInformation',
              query: {
                id: item.id,
                name: item.name,
                type: item.type,
                pcIconUrl: item.icon,
                pageType: 'sale'
              }
            });
          }
        });
      } else if (props.golist == 'pinggu') {
        kefuApi.getMyBrowse().then((res) => {
          if (res.code == 200) {
            router.push({
              path: '/index/BasicInformation',
              query: {
                id: item.id,
                name: item.name,
                type: item.type,
                pcIconUrl: item.icon,
                pageType: 'pinggu'
              }
            });
          }
        });
      } else if (props.golist == 'buy') {
        router.push({
          path: '/index/goodList',
          query: { name: item.name, type: item.type, id: item.id, pageType: 'buy' }
        });
        // item存储到recently_list里前面并筛选出最近浏览的6个，去除相同的，最后存到本地
        state.recently_list.unshift(item);
        state.recently_list = state.recently_list.filter((item, index) => {
          return state.recently_list.findIndex((item2) => item2.id === item.id) === index;
        });
        if (state.recently_list.length > 8) {
          state.recently_list.pop();
        }
        localStorage.setItem('recently_list', JSON.stringify(state.recently_list));
      } else if (props.golist == 'hire') {
        router.push({
          path: '/index/goodList',
          query: { name: item.name, type: item.type, id: item.id, pageType: 'hire' }
        });
      } else if (props.golist == 'huishou') {
        router.push({ path: '/index/other/contactkefu' });
      } else if (props.golist == 'kefu') {
        // 打开客服弹窗
        kefu.value.open(null, item.id);
      } else if (props.golist == 'huishoukefu') {
        // 告诉父组件点击的游戏是哪个
        emit('select', item.id);
      } else if (props.golist == 'jingjiahuishou') {
        router.push({
          path: '/index/jingjiahuishou',
          query: {
            id: item.id,
            name: item.name,
            type: item.type,
            pcIconUrl: item.icon,
            pageType: 'huishou'
          }
        });
      }
    };

    // 获取游戏类型
    const GetGameType = async () => {
      let data = await api.GetGameType(state.params);
      console.log('%c Line:83 🧀 data', 'color:#ed9ec7', data);
      state.gameTypeList = data.data;
      store.commit('setGameTypeList', state.gameTypeList);
    };

    onMounted(() => {
      GetGameType();
      // 获取最近浏览
      let recently_list = localStorage.getItem('recently_list');
      if (recently_list) {
        state.recently_list = JSON.parse(recently_list);
      }
    });

    const changeyanzheng = (item) => {
      console.log(item);
      state.ids = item.id;
      state.params.type = item.id;
      state.params.initial = '';
      GetGameType();
    };

    // 点击首字母
    const charChange = async (txt) => {
      console.log('%c Line:164 🍒 txt', 'color:#4fff4B', txt);
      state.params.initial = txt;
      // GetGameType()
      let data = await api.GetGameType(state.params);
      console.log('%c Line:83 🧀 data', 'color:#ed9ec7', data);
      state.gameTypeList = data.data;
      store.commit('setGameTypeList', state.gameTypeList);
      if (state.gameTypeList.length <= 0) {
        return ElMessage({
          message: '暂无数据',
          type: 'info',
        })
      }
    };

    // 游戏类型切换
    // const GetGameTypeChange = async () => {
    //   if (state.typeActive == 4) {
    //     let games = await localStorage.getItem('historyViews');
    //     if (games) {
    //       games = JSON.parse(games);
    //       // const result = [...new Set(games)];
    //       games = games.splice(0, 12);
    //       state.gameTypeList = games;
    //     } else {
    //       state.gameTypeList = [];
    //     }
    //     return;
    //   }
    //   let type = '';
    //   if (state.typeActive == 2) {
    //     type = 1;
    //   } else if (state.typeActive == 3) {
    //     type = 0;
    //   } else {
    //     type = '';
    //   }
    //   let games = await api.GetGameType({ type: type });
    //   if (games.data && games.data.length) {
    //     games.data = games.data.splice(0, 12);
    //     state.gameTypeList = games.data;
    //   }
    // };

    return {
      ...toRefs(state),
      ElMessage,
      Search,
      gameTypeClick,
      GetGameType,
      golist: props.golist,
      kefu,
      changeyanzheng,
      charChange
    };
  }
};
</script>
  
<style scoped lang="less">
.container {
  width: 1200px;
  margin: 0 auto;
  // background-image: url("../../assets/yudu/home/main-bg.png");
  background-repeat: no-repeat;
  background-size: 100% auto;
  min-height: 300px;
  background-color: #1c1632;
  box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
  margin-top: 20px;
  border-radius: 10px;
  overflow: hidden;
  // padding-bottom: 40px;
  // padding-bottom: 20px;

  // 切换
  .youxi {
    width: 100%;
    height: 39px;
    line-height: 39px;
    background-color: #1c1632;
    display: flex;
    padding: 20px;
    .one {
      // width: 100px;
      padding: 0 20px;
      height: 39px;
      text-align: center;
      background: #1c1632;
      border: 1px solid #1c1632;
      font-size: 16px;
      color: #fff;
      cursor: pointer;
      margin-right: 10px;
      // .fived {
      //   flex-shrink: 0;
      //   width: 100%;
      //   border-top: 4px solid @primaryColor;
      //   margin-left: -20px;
      //   text-align: center;
      //   position: relative;

      // }
    }

    .m-2 {
      --el-input-border-color: #302d45;
      --el-input-focus-border: #d2d2d2;
      --el-input-focus-border-color: #d2d2d2;
    }
  }

  // .activeTitle {
  // color: #96ce20 !important;
  // border-top: 3px solid #96ce20 !important;
  // background-color: #fff !important;
  // border-radius: 4px;
  // border-left: 1px solid #fff !important;
  // border-right: 1px solid #fff !important;
  // border-bottom: 1px solid #fff !important;
  // text-align: center;
  // position: relative;
  // i {
  //   position: absolute;
  //   display: inline-block;
  //   margin-left: -6px;
  //   height: 0;
  //   width: 0;
  //   border-top: 5px solid #96ce20;
  //   border-bottom: 5px solid transparent;
  //   border-left: 5px solid transparent;
  //   border-right: 5px solid transparent;
  //   left: 50%;
  //   top: -1px;
  // }
  // }
  .activeTitle {
    color: #fff !important;
    background-color: #e75d31 !important;
    border-radius: 20px;
    position: relative;
    // padding-bottom: 10px; /* 为三角形留出空间 */
  }

  .game-type-box {
    width: 100%;
    min-height: 200px;
    padding: 15px;

    .zhanghaos {
      margin-left: 10px;
      margin-top: 10px;
      width: 120px;
      height: 36px;
      line-height: 36px;
      text-align: center;
      background: rgba(125, 183, 4, 0.3);
      color: #7db704;
      font-size: 16px;
      border-radius: 5px;
    }

    .bottom {
      display: flex;
      align-items: center;
      width: 1172px;
      padding: 15px 20px;
      // width: calc(100% - 40px);
      margin: 0px 0 10px 0;
      border-radius: 10px;
      box-sizing: border-box;
      justify-content: space-between;
      background: #29243e;

      .li {
        width: 20px;
        text-align: center;
        cursor: pointer;
        color: #fff;
        font-size: 14px;
      }

      .li:nth-child(1) {
        width: 50px;
        text-align: center;
        cursor: pointer;
        color: #fff;
        font-size: 14px;
      }

      .on {
        // padding: 1px 4px;
        width: 20px;
        height: 20px;
        text-align: center;
        line-height: 20px;
        border-radius: 10px;
        color: #fff;
        font-weight: 500;
        background-color: #e75d31;
      }
    }
    // 游戏大全
    .game-items-wrapper {
      width: 100%;
      padding: 5px;
      box-sizing: border-box;
      height: auto;
      display: flex;
      flex-wrap: wrap;
      margin: 20px 0 30px;

      .game-item {
        width: 100px;
        cursor: pointer;
        padding: 10px;
        margin-right: 53px;
        margin-bottom: 26px;

        &:hover {
          transform: scale(1.1);
          transition: all 0.3s;
        }

        .game-icon {
          width: 100px;
          height: 100px;
          margin-bottom: 6px;
        }

        .name {
          color: #fff;
          font-size: 14px;
          text-align: center;
          line-height: 2;
        }
      }
      .game-item:nth-child(7n) {
        margin: 0;
      }
    }
  }
}

.zjll {
  color: #fff;
  font-size: 16px;
  margin-top: 33px;
  width: 1172px;
  margin-bottom: 33px;
  border-bottom: 1px solid #38324b;
}

// .game-type-btn {
//   background-color: #fff !important;
// }
/deep/ .el-input__prefix {
  margin-top: 13px;
}

/deep/ .el-input__wrapper {
  background-color: #24213a;
}

/deep/ .el-input__inner {
  color: #fff;
}
</style>