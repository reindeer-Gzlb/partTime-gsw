<template>
  <div class="bangzhu">
    <div class="content cooperate topbar">你是谁</div>
    <div class="nav">
      <img style="width: 100%" src="../../assets/guaishouwan/woyaomai.png" alt="" />
    </div>
    <div class="footer">
        <el-button @click="goLink('/')" style="border: 1px solid #e8572e;color: #e8572e;background-color: #1c1632;border-radius: 20px;margin-right: 30px;">返回首页</el-button>
        <el-button style="border: 1px solid #e8572e;color: #000;background-color: #e8572e;border-radius: 20px;">联系客服</el-button>
    </div>
  </div>
</template>

<script>
import { reactive, toRefs, onMounted, markRaw, watch } from 'vue';
import { useRouter } from 'vue-router';
import TopTitle from '@/components/TopTitle.vue'
import kefuApi from '@/api/kefu';
export default {
  name: 'cooperate',
  components: {
    TopTitle,
  },
  setup(props, ctx) {
    const router = useRouter();
    const state = reactive({
      title: '',
      loading: false,
      text: ``,
      type: 209,
      list: []
    });

     

    onMounted(() => {
      getData()
    });

    const getData = (val) => {
      state.loading = true
      kefuApi.getNoticeLists(state.type).then(res => {
        if (res.code == 200 || res != []) {
          state.list = res.rows
        }
      })
    };

    // 跳转
    const goLink = (link, query) => {
      if (query) {
        router.push({ path: link, query: query });
      } else {
        router.push({ path: link });
      }
    };

    return {
      ...toRefs(state),
      goLink,

    };
  }
};
</script>

<style lang="less" scoped>
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
</style>