<template>
  <div style="background: #1c1632">
    <div class="w1200 location" style="padding: 20px 0">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>我要卖</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="wrapper">
      <div class="box">
        <div class="one topbar">
          <div class="flex" style="align-items: center">
            <img style="width: 24px; height: 24px" src="../../assets/guaishouwan/gfkf.png" alt />
            <div style="cursor: pointer; margin-left: 10px" class="olo">官方客服</div>
          </div>
          <div @click="goback" style="cursor: pointer" class="olo">返回</div>
        </div>
        <div class="two">
          <div class="kefuBox" v-for="item in list" :key="item.id">
            <div class="weixins" style="cursor: pointer">微信号：{{ item.weixin }}</div>
            <div class="tupian">
              <img class="imhgs" :src="item.qrCode" alt />
            </div>
            <div class="titles">{{ item.name }}</div>
            <!-- <div class="lianxifs">
              <div class="wenzi">QQ:{{ item.qq }}</div>
              <div class="wenzi">微信：{{ item.weixin }}</div>
            </div>-->
            <div class style="color: #9b9b9b; padding-top: 12px">使用微信扫描二维码添加</div>
            <div class="buttons">
              <button class="bths" @click="goToKefu(item)" style="cursor: pointer">点击复制</button>
            </div>
          </div>
        </div>
      </div>
      <div style="padding-top: 20px"></div>
      <Footer :home="true" />
    </div>
  </div>
</template>

<script>
import { reactive, watch, ref, toRefs, onMounted, markRaw } from 'vue';
import Footer from '@/components/Footer.vue';
import Header from '@/components/Header.vue';
import kefu from '@/api/kefu';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
export default {
  components: {
    Footer,
    Header
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      type: '1',
      list: []
    });

    const goback = () => {
      router.go(-1);
    };

    const getList = async () => {
      const res = await kefu.getkefuer({ createId: state.type });
      console.log(res);
      state.list = res;
    };

    const goToKefu = (text) => {
      let input = document.createElement('input');
      input.setAttribute('readonly', 'readonly');
      input.setAttribute('value', text.weixin);
      document.body.appendChild(input);
      input.select();
      if (document.execCommand('copy')) {
        document.execCommand('copy');
        ElMessage({
          type: 'success',
          message: '复制成功'
        });
      }
      document.body.removeChild(input);
      // location.href = 'https://tb.53kf.com/code/client/770cb50d87dbd1d053bf4fb3f25464897/1'
      // window.open('https://tb.53kf.com/code/client/e83a1f20874cb35a77868f07e7df6a687/1');
      //   window.open('https://tb.53kf.com/code/worker/e83a1f20874cb35a77868f07e7df6a687/11651191');
    };

    onMounted(() => {
      getList();
    });

    return {
      ...toRefs(state),
      goback,
      getList,
      goToKefu
    };
  }
};
</script>

<style lang="less" scoped>
.wrapper {
  font-size: 14px;
  width: 100%;
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
.box {
  width: 1200px;
  border: 1px solid #2e2a44;
  margin: 0 auto;
  min-height: 100px;
  background-color: #211c38;
  padding: 0 20px;
  .one {
    display: flex;
    justify-content: space-between;
    padding: 10px 20px;
    color: #fff;
    height: 40px;
    line-height: 40px;
    border-radius: 15px;
    .olo {
      font-size: 16px;
      font-weight: 500;
      color: #fff;
    }
    span:nth-child(2) {
      font-size: 18px;
    }
  }
  .two {
    margin-top: 30px;
    display: flex;
    justify-content: start;
    flex-wrap: wrap;
    padding-left: 20px;
    .kefuBox {
      width: 200px;
      height: 276px;
      text-align: center;
      background-color: #f3f3f3;
      padding: 20px 10px;
      margin: 0 0 10px 10px;
      border-radius: 10px;
      .weixins {
        color: #b7b6b7;
        font-size: 16px;
      }

      .tupian {
        margin-top: 15px;
        .imhgs {
          width: 126px;
          height: 126px;
        }
      }
      .titles {
        padding: 5px 0;
        font-size: 14px;
        color: #000;
      }

      .lianxifs {
        display: flex;
        justify-content: space-between;
        .wenzi {
          font-size: 13px;
          color: #b7b6b7;
        }
      }
      .buttons {
        margin-top: 20px;
        .bths {
          width: 166px;
          height: 30px;
          border: none;
          color: #fff;
          border-radius: 20px;
          background: #ed3e23;
        }
      }
    }
  }
  .three {
    margin-top: 30px;
    .danbaoa {
      background-color: #f3f3f3;
      height: 50px;
      line-height: 50px;
      border-radius: 15px;
    }
    .detailds {
      margin-top: 26px;
      background-color: #f3f3f3;
      min-height: 10px;
      padding: 10px;
      line-height: 2;
    }
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
  padding: 10px 10px 22px 20px;
  background-color: #211c38;
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

:deep(.location .el-breadcrumb__inner) {
  color: #fff;
}

:deep(.location .el-breadcrumb__inner.is-link) {
  color: #fff !important;
}
</style>