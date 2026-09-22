<template>
  <div class="main">
    <div class="fixed-bar">
      <div style="background-color: #fff; border-radius: 10px">
        <div
          style="
            height: 39px;
            line-height: 39px;
            background-color: #ff7116;
            border-radius: 10px 10px 0 0;
            color: #fff;
            font-weight: 500;
            margin-bottom: 12px;
          "
        >
          快捷导航
        </div>
        <div class="kefu" @click="goLink('/index/accountOrder')">
          <img class="img" src="../../assets/guaishouwan/anyaomai.png" />
          <div class="name">我要购买</div>
        </div>
        <div class="kefu" @click="goToKefu">
          <!-- <img class="img" src="../../assets/icon/bianpu-icon.png" /> -->
          <img class="img" src="../../assets/guaishouwan/weixin11.png" />
          <div class="name">联系客服</div>
        </div>
        <!-- <div class="kefu" @click="goLink('/index/other/contactkefu')"> -->
        <div class="kefu" @click="zhanghaohuishou">
          <img class="img" src="../../assets/guaishouwan/huishouaccount.png" />
          <div class="name">账号回收</div>
        </div>
        <div class="kefu" @click="goToKefu">
          <img class="img" src="../../assets/guaishouwan/weixin11.png" />
          <div class="name">官方微信</div>
        </div>
        <div class="kefu" @click="gotoTop">
          <img class="img" src="../../assets/guaishouwan/top.png" />
          <div class="name">回到顶部</div>
        </div>
      </div>

      <!-- <div class="gotop" @click="gotop" v-show="gotop_show">
        <img class="img" src="../../assets/icon/GOTOP-icon.png" />
        <div class="name">TOP</div>
      </div> -->
    </div>

    <!--  -->
  </div>
</template>

<script>
import config from '@/api/config';
import kefuApi from '@/api/kefu';
import { reactive, toRefs, onMounted, onUnmounted, ref } from 'vue';
import { useRouter } from 'vue-router';
export default {
  name: 'zhinan',
  components: {
  },
  setup(props, ctx) {
    const router = useRouter();
    const state = reactive({
      title: '',
      text: ``
    });
    const kefu = ref(null);
    const gotop_show = ref(true);

    onMounted(() => { });
    const gotop = (val) => {
      gotop_show.value = true;
      // let body = document.getElementById('app')
      let body = document.getElementById('maincontent');
      //console.log(body.scrollTo());
      body.scrollTo(0, 0);
    };
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

    const openQQ = (val) => {
      //  调用一个需要登录权限的接口，校验是否登录
      kefuApi
        .getSystemInfo()
        .then((res) => {
          if (res.code == 200) {
            //  打开客服弹出框 kefu
            router.push({
              path: '/index/accountOrder',
              query: { to: 'kefu' }
            });
          }
        })
        .catch((err) => {
          //console.log(err);
        });

      //  //console.log(val);
      //  let token = window.localStorage.getItem('token')
      // if (token) {
      //   token = JSON.parse(token)
      // } else {
      //   token = ''
      // }
      // //console.log("%c Line:67 🍡 联系客服链接", "color:#7f2b82",`${config.imUrl}/chat/conversation?hxuser=${val.hxname}&str=${token}&goodsInfo=${encodeURIComponent(state.goodsInfo)}`);
      // window.open(`${config.imUrl}/chat/conversation`)
    };

    const goToKefu = () => {
      window.open('https://www30.53kf.com/webCompany.php?arg=10825571&kf_sign=zI1MjMTcxMM4MzEyNDY4NDcyNzMxMDAxNzI4MjU1NzE%253D&style=1')
    }

    const gotoTop = () => {
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    const zhanghaohuishou = () => {
      router.push('/index/huishouOther/kefu')
    }

    return {
      ...toRefs(state),
      gotop,
      goLink,
      openUrl,
      gotop_show,
      openQQ,
      goToKefu,
      gotoTop,
      zhanghaohuishou
    };
  }
};
</script>

<style scoped lang="less">
.main {
  line-height: 17px;
  font-size: 14px;
  font-weight: 700;

  .fixed-bar {
    width: 88px;
    color: #333;
    text-align: center;
    background-image: url('../../assets/index/logoname.png');
    background-size: 100% 56px;
    background-repeat: no-repeat;
    background-position-y: 10px;
    padding-top: 62px;
    padding-bottom: 15px;
    // background-color: #fff;
    > div {
      background-color: #fff;
      padding-bottom: 30px;
      cursor: pointer;
      img {
        width: 26px;
      }
    }
  }
}

.kefu {
  margin-bottom: 20px;
  font-weight: 500;
  color: #aaaaaa;
}

.kefu:last-child {
  margin-bottom: 0px;
}
</style>
