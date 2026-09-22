<template>
  <section class="container">
    <div class="w1000">

      <div class="cont">
        <div class="top">
          <ul>
            <li v-for="(item, index) in list_list" :key="item.noticeId" v-html="item.noticeContent">
            </li>
          </ul>
        </div>
        <div>
          <YouxiList :golist="'pinggu'"></YouxiList>
        </div>
        <!-- <div class="content">
          <div class="con_top">
            <ul>
              <li>
                <div class="h6">按系统</div>
                <ol>
                  <li v-for="(item, index) in xitong_list" :key="item.id"
                    :class="{ active: gameActive.one.id == item.id }" @click="gameActive.one = item">
                    <p class="left">{{ item.name }}</p>
                    <p class="right">
                      <img src="@/assets/icon/icon27.png" alt="">
                    </p>
                  </li>
                </ol>
              </li>
              <li>
                <div class="h6">实名情况</div>
                <ol>
                  <li v-for="(item, index) in shiming_list" :key="item.id"
                    :class="{ active: gameActive.two.id == item.id }" @click="gameActive.two = item">
                    <p class="left">{{ item.name }}</p>
                    <p class="right">
                      <img src="@/assets/icon/icon27.png" alt="">
                    </p>
                  </li>
                </ol>
              </li>
              <li>
                <div class="h6">账号来源</div>
                <ol>
                  <li v-for="(item, index) in laiyuan_list" :key="item.id"
                    :class="{ active: gameActive.three.id == item.id }" @click="gameActive.three = item">
                    <p class="left">{{ item.name }}</p>
                    <p class="right">
                      <img src="@/assets/icon/icon27.png" alt="">
                    </p>
                  </li>
                </ol>
              </li>
              <li>
                <div class="h6">系统信息</div>
                <ol>
                  <li>
                    <div class="titl">
                      <span></span>
                      <p>联系方式</p>
                    </div>
                    <input type="text" placeholder="请输入联系方式">
                  </li>
                  <li>
                    <div class="titl">
                      <span></span>
                      <p>联系QQ</p>
                    </div>
                    <input type="text" placeholder="请输入联系QQ">
                  </li>
                  <li>
                    <div class="titl">
                      <span></span>
                      <p>联系微信</p>
                    </div>
                    <input type="text" placeholder="请输入联系微信">
                  </li>
                  <li>
                    <div class="titl">
                      <span></span>
                      <p>交易保障合同</p>
                      <img src="@/assets/icon/icon37.png" alt="">
                    </div>
                  </li>
                </ol>
              </li>
            </ul>
          </div>
          <div class="bottom">
            您的选择：
            <span v-if="gameActive.one.name">{{ gameActive.one.name }}&ensp; {{ gameActive.two.name }} &ensp; {{
              gameActive.three.name }}</span>
            <span v-else>暂未选择</span>
          </div>
        </div>
        <button>下一步 填写信息</button> -->
      </div>
    </div>

    <!-- 底部 -->
    <!-- <Footer /> -->
  </section>
</template>

<script>
import kefu from '@/api/kefu'
import { House } from '@element-plus/icons-vue';
import Footer from '@/components/Footer.vue';
import { reactive, toRefs, onMounted, onUnmounted, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import YouxiList from '@/components/YouxiList.vue'
import useClipboard from 'vue-clipboard3'
export default {
  name: 'other',
  components: { House, Footer ,YouxiList},
  setup(props, ctx) {
    const router = useRouter();
    const state = reactive({
      list_list: [],
      id: '',
      name: '',
      pcIconUrl: '',
      gameActive: {
        one: {},
        two: {},
        three: {},
      }, // 游戏信息
      xitong_list: [
        {
          id: 1,
          name: '安卓QQ',
        },
        {
          id: 2,
          name: '安卓微信',
        },
        {
          id: 3,
          name: '苹果QQ',
        },
        {
          id: 4,
          name: '苹果微信',
        }
      ],
      // 实名情况
      shiming_list: [
        {
          id: 1,
          name: '已实名',
        },
        {
          id: 2,
          name: '未实名',
        },
      ],
      // 账号来源
      laiyuan_list: [
        {
          id: 1,
          name: '自己注册',
        },
        {
          id: 2,
          name: '他人注册',
        },
      ],

    });
    // 跳转
    const goLink = (link, query) => {
      if (query) {
        router.push({ path: link, query: query });
      } else {
        router.push({ path: link });
      }
    };
    // 获取顶级分类
    const getDingjiList = () => {
      //console.log('gameActive', gameActive);
    };

    // 填写信息——下一步
    const nextStep = () => {
      //console.log('gameActive', state.gameActive);
      if (!state.gameActive.one.name) {
        return;
      }
      if (!state.gameActive.two.name) {
        return;
      }
      if (!state.gameActive.three.name) {
        return;
      }
      goLink('/index/fillInformation', {
        id: state.id,   // 游戏id
        name: state.name,   // 游戏名称
        pcIconUrl: state.pcIconUrl, // 游戏图标
        xitong: state.gameActive.one.name,    // 系统
        shiming: state.gameActive.two.name,   // 实名
        laiyuan: state.gameActive.three.name, // 来源
      });
    }

    // 监听路由参数
    watch(
      () => router.currentRoute.value.query,
      (newValue, oldValue) => {
        state.id = router.currentRoute.value.query.id;
        state.name = router.currentRoute.value.query.name;
        state.pcIconUrl = router.currentRoute.value.query.pcIconUrl;
      },
      { deep: true, immediate: true }
    );

    // 获取客服信息
    const getAccountRecovery = () => {
      kefu.getAccountRecovery("zhpg").then(res => {
        //console.log(res)
        state.list_list = res.rows
      })
    }

    onMounted(() => {
      getAccountRecovery()
      state.id = router.currentRoute.value.query.id;
      state.name = router.currentRoute.value.query.name;
      state.pcIconUrl = router.currentRoute.value.query.pcIconUrl;
      //console.log('router.currentRoute.value.query.id', router.currentRoute.value.query);
    });
    return {
      ...toRefs(state),
      goLink, // 跳转
      getDingjiList, // 获取顶级分类
      nextStep,   // 下一步
    }
  }
};
</script>

<style scoped lang="less">
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  list-style: none;
  font-family: 'Microsoft YaHei';
}

.location {
  display: flex;
  margin-top: 10px;
  margin-bottom: 15px;
}

.container {
  .w1000 {
    width: 1200px;
    min-height: calc(100vh - 449px);
    margin: 0 auto;
  }

  .cont {

    .top {
      width: 1200px;
      margin: 0 auto;
      background-color: #fff;
      box-shadow: 0 0 8px #c7c7c7;
      border-radius: 5px;
      margin-top: 10px;
      padding: 15px;
       // 显示滚动条
       overflow-y: scroll;
    }


    .content {
      width: 1200px;
      min-height: 200px;
      background-color: #fff;
      box-shadow: 0 0 8px #ddd;
      margin: 0 auto;
      margin-top: 20px;
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      box-sizing: border-box;

      .con_top {
        width: 100%;

        ul {
          width: 100%;
          display: flex;
          justify-content: space-around;

          >li {
            display: flex;
            flex-direction: column;
            align-items: center;

            .h6 {
              font-size: 16px;
              color: #000;
              height: 65px;
              line-height: 65px;
            }

            ol {
              width: 200px;
              height: 292px;
              border: 1px solid #e4e4e4;

              li {
                width: 100%;
                height: 40px;
                display: flex;
                align-items: center;
                justify-content: space-between;
                cursor: pointer;
                padding: 0 10px;

                .left {
                  font-size: 14px;
                  color: #000;
                  margin-left: 10px;
                }

                .right {
                  img {
                    width: 16px;
                    height: 16px;
                    margin-right: 10px;
                    display: none;
                  }
                }
              }

              .active {
                background-color: #fef0f0;

                .left {
                  color: #FF8E00;
                }

                .right {
                  img {
                    display: block;
                  }
                }
              }
            }
          }

          li:nth-of-type(4) {
            ol {
              li {
                display: block;
                padding: 15px 0;
                height: 80px;
                border-bottom: 1px solid #e4e4e4;

                .titl {
                  display: flex;
                  align-items: center;
                  width: 180px;
                  margin: 0 auto;
                  margin-bottom: 5px;

                  span {
                    display: inline-block;
                    width: 8px;
                    height: 8px;
                    background-color: #FF8E00;
                    margin-right: 5px;
                  }

                  p {
                    font-size: 12px;
                    color: #000;
                  }

                  img {
                    width: 16px;
                    height: 16px;
                    display: block;
                    margin-left: 15px;
                  }
                }

                input {
                  width: 170px;
                  margin: 0 15px;
                  background-color: #f7f7f7;
                  font-size: 12px;
                  color: #000;
                  height: 30px;
                  padding-left: 10px;
                  border: none;
                  outline: none;
                }
              }

              li:nth-of-type(4) {
                height: 50px;
                border: none;
              }
            }
          }
        }
      }

      .bottom {
        width: 1045px;
        margin-top: 20px;
        display: flex;
        align-items: center;

        span {
          font-size: 14px;
          color: #FF8E00;
          margin-right: 5px;
        }
      }
    }

    button {
      width: 300px;
      height: 50px;
      background-color: #FF8E00;
      border-radius: 50px;
      margin: 0 450px;
      margin-top: 30px;
      font-size: 16px;
      color: #fff;
      line-height: 50px;
      text-align: center;
      cursor: pointer;
      border: none;
    }
  }

}
</style>
