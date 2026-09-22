<template>
  <div class="bangzhu-box">
    <TopTitle :title="'找回案例'"></TopTitle>
    <div class="content">
      <!-- <div class="top">
                <img src="../../assets/fh-img/zhaohui.jpg" alt="">
              </div> -->
      <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
        <el-tab-pane label="打击找回案例" name="first">
          <div class="con">
            <div
              class="li"
              v-for="(item, index) in left_list"
              :key="item.noticeId"
              @click="goLink('/index/other/helpdetail', { id: item.noticeId })"
            >
              <div class="img">
                <img :src="item.titleImg" alt="" />
              </div>
              <div class="text">
                <div class="title">
                  {{ item.noticeTitle }}
                </div>
                <div class="time_go">
                  <div class="time">{{ item.createTime }}</div>
                  <div class="go">
                    查看详情
                    <img src="../../assets//fh-icon/youjiantou_bg_lan.png" alt="" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </el-tab-pane>

        <el-tab-pane label="立案/诉讼文书" name="second">
          <div class="con">
            <div
              class="li"
              v-for="(item, index) in right_list"
              :key="item.noticeId"
              @click="goLink('/index/other/helpdetail', { id: item.noticeId })"
            >
              <div class="img">
                <img :src="item.titleImg" alt="" />
              </div>
              <div class="text">
                <div class="title">
                  {{ item.noticeTitle }}
                </div>
                <div class="time_go">
                  <div class="time">{{ item.createTime }}</div>
                  <div class="go">
                    查看详情
                    <img src="../../assets//fh-icon/youjiantou_bg_lan.png" alt="" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import { reactive, toRefs, onMounted, markRaw, watch } from 'vue';
import { useRouter } from 'vue-router';
import TopTitle from '@/components/TopTitle.vue';
import kefu from '@/api/kefu';
export default {
  name: 'cooperate',
  components: {
    TopTitle
  },
  setup(props, ctx) {
    const router = useRouter();
    const state = reactive({
      title: '',
      loading: false,
      text: ``,
      type: 209,
      list: [],
      left_list: [],
      right_list: [],
      activeName: 'first'
    });

    onMounted(() => {
      getData1();
    });

    // const getData = (val) => {
    //     state.loading = true
    //     kefuApi.getNoticeLists('xwgg').then(res => {
    //         if (res.code == 200 || res != []) {
    //             state.list = res.rows
    //         }
    //     })
    // };
    // 获取数据-找回包赔服务
    const getData1 = async () => {
      kefu.getNotices('djzhal').then((res) => {
        state.left_list = res;
        //console.log("res", res);
      });

      kefu.getNotices('lassws').then((res) => {
        state.right_list = res;
      });
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
      goLink
    };
  }
};
</script>

<style lang="less" scoped>
.bangzhu-box {
  background-color: #fff;
  width: 100%;
}
h2 {
  text-align: center;
  font-size: 20px;
  font-weight: 500;
}
.content {
  padding: 20px;
  .con {
    .li {
      display: flex;
      margin-top: 25px;
      border-bottom: 1px solid #eaeaea;
      padding-bottom: 25px;

      .img {
        img {
          width: 233px;
          height: 132px;
          border-radius: 8px;
          margin-right: 24px;
        }
      }

      .text {
        flex: 1;

        .title {
          width: 632px;
          height: 90px;
          font-size: 20px;
          font-family: Source Han Sans CN;
          font-weight: 500;
          color: #333;
          line-height: 30px;
          text-align: left;
          // 只显示三行，超出显示省略号
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 3;
          -webkit-box-orient: vertical;
        }

        .time_go {
          display: flex;
          align-items: center;
          justify-content: space-between;

          .time {
            font-size: 18px;
            font-weight: 400;
            color: #999;
          }

          .go {
            display: flex;
            align-items: center;
            font-size: 17px;
            color: #FF8E00;
            cursor: pointer;

            img {
              margin-left: 5px;
              width: 8px;
              height: 13px;
              margin-left: 8px;
            }
          }
        }
      }
    }
  }
}
</style>
