<template>
  <!-- 收藏商品 -->
  <div class="main">
    <TopTitle :title="'我的收藏'"></TopTitle>

    <div class="cont" v-loading="loading">
      <div
        class="item"
        v-for="item in list"
        :key="item.id"
        @click="goLink('/pages/detail/index', { id: item.id })"
      >
        <div class="bot">
          <!-- <img src="../../assets//taohaoqu/icon/zhichibaopei.png" alt=""> -->
          <div class="img">
            <img :src="item.titleImg" alt="" />
          </div>
          <div class="texts">
            <div class="title">
              {{ item.des }}
            </div>
            <div class="qufu">
              {{ item.serverName }}
            </div>
            <div class="price-bar flex fbt">
              <div class="price">
                <span></span>
                <span>¥</span>
                <span>{{ item.newPrice }}</span>
              </div>
              <!-- 浏览数 -->
              <div class="baopei">
                <img src="../../assets//fh-icon/liulan.png" alt="" />
                <span v-text="!item.browseNum ? '0' : item.browseNum"></span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="null" v-if="list.length == 0" style="text-align: center; line-height: 200px">
        暂无数据
      </div>
    </div>
  </div>
  <div class="feiye">
    <el-pagination
      small
      style="float: right; --el-color-primary: #b42c1d"
      background
      layout="prev, pager, next"
      :total="total"
      class="mt-4"
      v-model:page-size="pageSize"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import { reactive, toRefs, onMounted, onUnmounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import commodityList from '@/components/commodityList.vue'

// 导入top_title
import TopTitle from '@/components/TopTitle.vue'
import kefu from '@/api/kefu'
export default {
  name: 'zhinan',
  components: {
    TopTitle,
    commodityList
  },
  setup(props, ctx) {
    const route = useRouter();
    const state = reactive({
      gameActive_id: 0,   // 当前选中的游戏
      currentPage: 1, // 当前页
      pageSize: 10,   // 每页显示条数
      total: 0,   // 总条数
      title: '',
      text: ``,
      userInfo: null,
      list: [],
      loading: false,
      /**  audit(0, "待审核"),
    unsold(1, "未出售"),
    sold(2, "出售中"),
    InTheSale(3, "已售"),
    problem(4, "问题账号"),
    back(5, "找回账号"),
    timeout(6, "超时未售"),
    sinceSale(7, "自售账号"),
    shelves(8, "已下架"), */
      stateOption: {
        0: "待审核",
        1: "未出售",
        2: '出售中',
        3: '已售',
        4: '问题账号',
        5: '找回账号',
        6: '超时未售',
        7: '自售账号',
        8: '已下架',
      }
    });

    onMounted(() => {
      let info = window.localStorage.getItem('userInfo')
      if (info) {
        state.userInfo = JSON.parse(info)
      } else {
        route.replace({ path: '/login' })
      }
      setTimeout(() => {
        getLisst()
      }, 100)
    });
    const getLisst = () => {
      state.loading = true
      let obj = {
        createId: state.userInfo.userId,
        pageSize: state.pageSize,
        pageNum: state.currentPage, // 当前页
      }
      kefu.listCollect(obj).then(res => {
        //console.log("%c Line:37 🌽 res收藏列表", "color:#f5ce50", res);
        state.loading = false
        state.list = res.data
        // state.total = res.total
      })
    }
    // 取消收藏
    const delCollect = (val) => {
      kefu.delCollect({
        accountId: val.id,
        createId: state.userInfo.userId
      }).then(res => {
        ElMessage({
          type: 'success',
          message: res.msg
        })
        getLisst()
      })
    }
    // 
    const goDetail = (val) => {
      const url = router.resolve({ path: `/pages/detail/index`, query: { id: item.id } })
      window.open(url.href, '_blank')
    }
    // 跳转
    const goLink = (link, query) => {
      if (query) {
        route.push({ path: link, query: query });
      } else {
        route.push({ path: link });
      }
    };

    // 分页操作
    const handleCurrentChange = (val) => {
      //console.log("11", val)

      state.currentPage = val
      getLisst()
    }

    // 分页操作
    const handleSizeChange = (val) => {
      //console.log("11", val)
      state.pageSize = val
    }


    return {
      ...toRefs(state),
      getLisst,
      goDetail,
      delCollect,
      goLink,
      handleCurrentChange,
      handleSizeChange
    };
  }
};
</script>

<style scoped lang="less">
.main {
  font-size: 16px;
  background-color: #211c38;
  border: 1px solid #6f6d6d;
}

.tit {
  width: 100%;

  font-size: 16px;
  font-weight: bold;
  color: @primaryColor;
  line-height: 50px;

  span {
    flex-shrink: 0;
    width: 100px;
    border-top: 4px solid @primaryColor;
    margin-left: -20px;
    text-align: center;
    position: relative;

    i {
      position: absolute;
      display: inline-block;
      height: 0;
      width: 0;
      border-top: 5px solid @primaryColor;
      border-bottom: 5px solid transparent;
      border-left: 5px solid transparent;
      border-right: 5px solid transparent;
      left: 50%;
      top: -1px;
    }
  }

  div {
    width: 1000px;
    height: 50px;
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
  }
}

.cont {
  padding: 0 15px;
  font-size: 14px;
  min-height: 400px;
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;

  .item {
    width: 220px;
    height: 100%;
    // border-bottom: 1px solid #f3f3f3;
    box-sizing: border-box;
    background-color: #1c1632;
    // box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    margin-right: 17px;
    margin-top: 20px;
    padding-bottom: 10px;
    border-radius: 8px;
    overflow: hidden;

    .top {
      color: #999999;
      line-height: 50px;

      span {
        margin-right: 20px;
      }
    }

    .bot {
      position: relative;
      width: 200px;
      > img {
        position: absolute;
        top: 0;
        left: 0;
        width: 120px;
      }

      .img {
        width: 220px;
        height: 150px;
        overflow: hidden;

        img {
          width: 100%;
          height: auto;
        }
      }

      .texts {
        margin-left: 10px;
        width: 200px;
        margin: 0 10px;
        margin-top: 10px;

        .title {
          width: 190px;
          height: 50px;
          line-height: 1.5;
          font-size: 16px;
          font-weight: 500;
          color: #fff;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2; // 超出多少行
          -webkit-box-orient: vertical;
        }

        .qufu {
          color: #999999;
          line-height: 21px;
          margin-top: 10px;
        }

        .price-bar {
          margin-top: 10px;
          display: flex;
          justify-content: space-between;
          align-items: center;

          .price {
            color: #999;

            span:nth-of-type(3) {
              font-weight: bold;
              color: #ff6700;
              line-height: 21px;
              font-size: 20px;
            }

            span:nth-of-type(2) {
              color: #ff6700;
            }
          }

          .baopei {
            display: flex;
            align-items: center;
            font-size: 18px;
            color: #999;

            img {
              width: 20px;
              height: 12px;
              margin-right: 7px;
            }
          }
        }
      }

      .btns {
        .el-button {
          width: 110px;
          height: 38px;
          border: 1px solid @primaryColor;
          border-radius: 4px;
          color: @primaryColor;

          &.buy {
            margin-top: 20px;
            border: none;
            background: linear-gradient(0deg, #ff6700 0%, #ff9400 100%);
            color: #fff;
          }
        }
      }
    }
  }

  .item:nth-of-type(4n) {
    margin-right: 0;
  }
}
</style>
