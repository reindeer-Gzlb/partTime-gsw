<template>
  <div class="footer">
    <div class="conter">
      <div class="top flex fbt">
        <div class="logo">
          <img src="../assets/index/logoname.png" alt="" />
        </div>
        <div class="oll">
          <ul class="flex">
            <li @click="getNoticeByType">隐私政策</li>
            <li @click="gotousu">投诉建议</li>
            <li @click="getNoticeByType">服务协议</li>
            <li @click="lianxiwomen">联系我们</li>
          </ul>
        </div>
        <!-- <div class="list">
          <ul>
            <li>
              <ol>
                <li>条款说明</li>
                <li @click="goLink('/index/other/cooperate')">关于我们</li>
                <li @click="getNoticeByType('yonghuxieyi')">用户协议</li>
                <li @click="getNoticeByType('yinsizhengce')">隐私协议</li>
              </ol>
            </li>
            <li>
              <ol>
                <li>服务</li>
                <li @click="gotousu">投诉建议</li>
                <li @click="goLink('/')">客服验证</li>
              </ol>
            </li>
            <li>
              <ol>
                <li>关注我们</li>
                <li>
                  <img src="../assets//fh-icon/di1.png" alt="">
                  <img src="../assets//fh-icon/di2.png" alt="">
                </li>
              </ol>
            </li>
          </ul>
        </div> -->
      </div>
    </div>

    <div class="copyright">
      <div>
        <div class="gs-com">Copyright©2023 guaishouwan.com All rights reserved.</div>
        <div class="gs-com">
          <!-- 粤ICP备2025389065号-2 -->
          <a href="https://beian.miit.gov.cn/#/Integrated/recordQuery" target="_blank"
            >粤ICP备2025389065号</a
          >
        </div>
        <div class="gs-com">友情链接： 腾讯游戏 网易游戏</div>
      </div>
      <div style="display: flex; align-items: center">
        <div class="img_gs">
          <img src="../assets/guaishouwan/smrz.png" alt="" />
        </div>
        <div class="img_gs">
          <img src="../assets/guaishouwan/pprz.png" alt="" />
        </div>
      </div>
    </div>
    <!-- <div class="bottom">
      <p>
        温馨提示：抵制不良游戏 拒绝盗版游戏 注意自我保护 谨防受骗上当 适度游戏有益 沉迷游戏伤身
        合理安排时间 享受健康生活
      </p>
      <p class="beian">
        惠州市怪兽玩清风科技有限公司 备案号：
        <a href="https://beian.miit.gov.cn/#/Integrated/recordQuery" target="_blank">
          粤ICP备2024185403号
        </a>
      </p>
    </div> -->
  </div>
  <Check ref="check"></Check>
</template>

<script lang="ts">
import { onUnmounted, reactive, toRefs, ref } from 'vue';
import { useRouter } from 'vue-router';
import kefu from '@/api/kefu';
import ElMessageBox from '../components/ElMessageBox.vue';
import Check from '@/components/kefuCheck.vue';
export default {
  name: 'footer',
  props: [],
  components: {
    ElMessageBox,
    Check
  },
  setup() {
    const router = useRouter();
    const check = ref(null);
    const state = reactive({
      menuList: []
    });

    onUnmounted(() => {});

    const goLink = (link: any, query: any) => {
      if (query) {
        router.push({ path: link, query: query });
      } else {
        router.push({ path: link });
      }
    };
    // 按类型获取公告列表，并取第一个进行查看详情
    const getNoticeByType = (type) => {
      // kefu.getNoticeList(type).then((res) => {
      //   if (res && res.length) {
      //     router.push({ path: `/index/articleDetail`, query: { id: res[0].noticeId } });
      //   }
      // });
      router.push('/');
    };

    // 去申诉
    const gotousu = () => {
      // 判断是否登录,登陆了就跳转到申诉页面，没登录就提示登陆
      if (localStorage.getItem('token')) {
        goLink('/index/other/suggest');
      } else {
        ElMessage({
          message: '您还未登录，请前往登录!',
          type: 'error'
        });
      }
      // window.open(
      //   'https://www30.53kf.com/webCompany.php?arg=10825571&kf_sign=zI0OTMTcxMIzMTEyNDY4NDcyNzMxMDAxNzI4MjU1NzE%253D&style=1'
      // );
    };

    const lianxiwomen = () => {
      window.open(
        'https://www30.53kf.com/webCompany.php?arg=10825571&kf_sign=zI0OTMTcxMIzMTEyNDY4NDcyNzMxMDAxNzI4MjU1NzE%253D&style=1'
      );
    };

    return {
      ...toRefs(state),
      goLink,
      gotousu,
      getNoticeByType,
      lianxiwomen
    };
  }
};
</script>

<style scoped lang="less">
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

ol,
ul,
li {
  list-style: none;
}

.footer {
  height: 256px;

  background: #211c38;
  // background-image: url('../assets/shanggu/home/bot-bg.png');
  // background-size: 100% 100%;
  // padding-top: 20px;
  color: #fff;
  .conter {
    width: 1200px;
    margin: 0 auto;
    padding-bottom: 10px;
    border-bottom: 1px solid #38344b;

    .top {
      align-items: center;
      padding: 20px;
      padding-top: 45px;

      .logo {
        width: 150px;
        // margin-left: 200px;
        img {
          width: 100px;
          height: 77px;
          cursor: pointer;
        }
      }

      .list {
        height: 160px;
        min-width: 500px;
        border-left: 1px solid #eaeaea;

        ul {
          display: flex;

          > li {
            margin-left: 90px;

            ol {
              > li {
                font-size: 16px;
                font-family: Source Han Sans CN;
                margin-bottom: 15px;
                cursor: pointer;
                font-weight: 300;

                img {
                  width: 20px;
                  height: 20px;
                }

                img:nth-child(1) {
                  margin-right: 10px;
                }

                &:hover {
                  color: #f44650;
                }
              }

              li:nth-child(1) {
                font-size: 18px;
                font-family: Source Han Sans CN;
                font-weight: 500;
                margin-bottom: 28px;
              }
            }
          }
        }
      }
    }
  }
}
.bottom {
  margin-top: 50px;

  p {
    text-align: center;
    font-size: 16px;
    font-family: Source Han Sans CN;
    padding-bottom: 16px;
    line-height: 40px;
    color: rgba(255, 255, 255, 0.6);

    span {
      color: #ff8e00;
    }
  }
  .beian {
    color: #333;
    background-color: #fff;
    padding-bottom: 0;
    margin-top: 24px;
  }
}

.oll {
  li {
    margin-right: 50px;
  }
  li:nth-child(4) {
    margin-right: 0;
  }
}

.copyright {
  width: 1200px;
  margin: 0 auto;
  padding-top: 22px;
  display: flex;
  justify-content: space-between;

  .gs-com {
    color: #bdbbc4;
    a {
      color: #bdbbc4;
    }
    a:hover {
      color: #776d5a;
    }
  }

  .img_gs {
    img {
      width: 100px;
      height: 37px;
      margin-right: 30px;
    }
  }
  .img_gs:nth-child(2) {
    img {
      margin-right: 0;
    }
  }
}
</style>