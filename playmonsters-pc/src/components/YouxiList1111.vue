<template>
  <section class="container">
    <div class="game-type-box">
      <!-- 游戏大全 -->
      <div class="game-items-wrapper">
        <div class="game-item" @click="gameTypeClick(item)" v-for="(item, index) in gameTypeList" :key="`game${index}`">
          <div class="game-icon">
            <img :src="item.icon" style="width:100%;height:auto" alt />
          </div>
          <div class="name">{{ item.name }}</div>
        </div>
      </div>

    </div>
    <kefuDialog ref="kefu" />
  </section>
  
</template>
  
<script>
import { RefreshRight, CaretTop, Search, CaretBottom, House, View } from '@element-plus/icons-vue';
import Footer from '@/components/Footer.vue';
import { reactive, toRefs, onMounted, watch,ref } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/api/index';
import kefuApi from '@/api/kefu';
import { useStore } from "vuex";
import kefuDialog from '../views/home/kefuDialog.vue';
export default {
  name: 'accountorder',
  components: { House, RefreshRight, Search, CaretBottom, CaretTop, View, Footer,kefuDialog },
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
     
      params: {
        type: '',
        initial: '',
        name: ''
      }
    });
const kefu = ref()
    const store = useStore();
    const router = useRouter();
    const gameTypeClick = (item) => {
      if (props.golist == 'sale') {
        kefuApi.getMyBrowse().then(res => {
          if (res.code == 200) {
            router.push({ path: '/index/BasicInformation', query: { id: item.id, name: item.name, type: item.type, pcIconUrl: item.icon, pageType: 'sale' } });
          }

        })
      } else if (props.golist == 'pinggu') {
        kefuApi.getMyBrowse().then(res => {
          if (res.code == 200) {
            router.push({ path: '/index/BasicInformation', query: { id: item.id, name: item.name, type: item.type, pcIconUrl: item.icon, pageType: 'pinggu' } });
          }
        })
      } else if (props.golist == 'buy') {
        router.push({ path: '/index/goodList', query: { name: item.name, type: item.type, id: item.id, pageType: 'buy' } });
        // item存储到recently_list里前面并筛选出最近浏览的6个，去除相同的，最后存到本地
        state.recently_list.unshift(item)
        state.recently_list = state.recently_list.filter((item, index) => {
          return state.recently_list.findIndex((item2) => item2.id === item.id) === index
        })
        if (state.recently_list.length > 8) {
          state.recently_list.pop()
        }
        localStorage.setItem('recently_list', JSON.stringify(state.recently_list))
      }
      else if (props.golist == 'hire') {
        router.push({ path: '/index/goodList', query: { name: item.name, type: item.type, id: item.id, pageType: 'hire' } });
      }
      else if (props.golist == 'huishou') {
        router.push({ path: '/index/other/contactkefu' });
      } else if (props.golist == 'kefu') {
        // 打开客服弹窗
        kefu.value.open(null, item.id)
      }else if (props.golist == 'huishoukefu') {
        // 告诉父组件点击的游戏是哪个
        emit('select',item.id)
      }else if (props.golist == 'jingjiahuishou') {
        router.push({ path: '/index/jingjiahuishou',query: { id: item.id, name: item.name, type: item.type, pcIconUrl: item.icon, pageType: 'huishou' } });
      }
    };

    // 获取游戏类型
    const GetGameType = async () => {
      let data = await api.GetGameType(state.params)
      console.log("%c Line:83 🧀 data", "color:#ed9ec7", data);
      state.gameTypeList = data.data
      store.commit('setGameTypeList', state.gameTypeList)
    };


    

    onMounted(() => {
      GetGameType();
      // 获取最近浏览
      let recently_list = localStorage.getItem('recently_list')
      if (recently_list) {
        state.recently_list = JSON.parse(recently_list)
      }
    });


    return {
      ...toRefs(state),
      gameTypeClick,
      GetGameType,
      golist: props.golist,
      kefu
    };
  }
};
</script>
  
<style scoped lang="less">
.container {
  width: 1200px;
  margin: 0 auto;
  padding: 15px 20px;
  // background-image: url("../../assets/yudu/home/main-bg.png");
  background-repeat: no-repeat;
  background-size: 100% auto;
  min-height: 300px;
  background-color: #fff;
  box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
  margin-top: 20px;
  border-radius: 10px;
  overflow: hidden;
  margin-bottom: 40px;


  .game-type-box {
    width: 100%;
    min-height: 200px;


    // 游戏大全
    .game-items-wrapper {
      width: 100%;
      padding: 5px;
      box-sizing: border-box;
      height: auto;
      display: flex;
      flex-wrap: wrap;
      margin: 20px 0 20px;

      .game-item {
        width: 102px;
        display: flex;
        flex-direction: column;
        align-items: center;
        cursor: pointer;
        margin: 0 24px 15px 20px;

        &:hover {
          transform: scale(1.1);
          transition: all 0.3s;
        }

        .game-icon {
          width: 90px;
          height: auto;
          margin-bottom: 6px;
        }

        .name {
          color: #25282b;
          font-size: 17px;
          text-align: center;
          line-height: 3;
        }
      }
    }
  }
}

// .game-type-btn {
//   background-color: #fff !important;
// }
</style>