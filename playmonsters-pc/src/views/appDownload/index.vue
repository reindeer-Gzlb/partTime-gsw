<template>
  <section class="container">
    <div class="">
      <div class="game-type-box">
        <img class="qrcode" src="../../assets/taohaoqu/appdownload-qrcode.png" alt="">
      </div>
    </div>

    <!-- 底部 -->
    <Footer />
  </section>
</template>

<script>
import { RefreshRight, CaretTop, Search, CaretBottom, House, View } from '@element-plus/icons-vue';
import Footer from '@/components/Footer.vue';
import { reactive, toRefs, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/api/index';
import { useStore } from "vuex";
export default {
  name: 'accountorder',
  components: { House, RefreshRight, Search, CaretBottom, CaretTop, View, Footer },
  setup (props, ctx) {
    const state = reactive({
      gameType: '全部游戏',
      gameTypeList: []
    });2

    const store = useStore();
    const router = useRouter();

    const gameTypeClick = (item) => {
      // localStorage.setItem('currentGameName',item.Detail_Name)
      router.push({ path: '/index/goodList' ,query:{name:item.name,type:item.type,id:item.id}});
    };

    // 获取游戏类型
    const GetGameType = async () => {
      let data = await api.GetGameType()
      //console.log("%c Line:72 🧀 data", "color:#ed9ec7", data);
      state.gameTypeList = data.data
      store.commit('setGameTypeList', state.gameTypeList)
    };

    

    onMounted(() => {
      GetGameType();
      // GetZHList()
    });

    return {
      ...toRefs(state),
      gameTypeClick,
      // GetZHList
    };
  }
};
</script>

<style scoped lang="less">
.container {
  // background-image: url("../../assets/yudu/home/main-bg.png");
    // background-repeat: no-repeat;
    // background-size: 100% auto;
    // min-height: 400px;
  .location {
    display: flex;
    padding-top: 20px;
  }
}
.game-type-box {
  min-height: 500px;
  background-image: url('../../assets/taohaoqu/appdownloadBg.png');
  background-size: 1920px auto;
  background-position: center center;
  background-repeat: no-repeat;
  // margin-top: 20px;
  height: 855px;
  position: relative;
}
.qrcode {
  width: 250px;
  height: 250px;
  position: absolute;
  top: 430px;
  left: 50%;
  transform: translateX(-472px)
}
/deep/ .footer{
  margin-top: 0;
}

</style>