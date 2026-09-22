<template>
  <div class="main">
    <div class="location w1200" style="margin-bottom: 30px">
      <el-breadcrumb :separator-icon="ArrowRight">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>业务介绍 </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="all">
      <div class="leftcss">
        <div
          class="navleft"
          style="height: 44px"
          v-for="item in arr"
          :key="item.id"
          @click="changefn(item)"
        >
          <div class="one" :class="{ active: ids == item.id }">
            <img
              v-if="ids != item.id"
              style="width: 8px; height: 8px"
              src="../../assets/guaishouwan/sanjiaoxing.png"
              alt=""
            />
            <img
              v-else
              style="width: 8px; height: 8px"
              src="../../assets/guaishouwan/sanjiaoxing-active.png"
              alt=""
            />
            <div class="title">{{ item.name }}</div>
          </div>
        </div>
      </div>
      <div class="rightcss" v-if="flag == false">
        <div style="width: 880px; padding: 0 20px; border: 1px solid #2e2a44">
          <div class="nav" v-for="item in noticedatass" :key="item.id" @click="getInfo(item)">
            <div>
              <img style="width: 240px; height: 135px" :src="item.titleImg" alt="" />
            </div>
            <div style="color: #fff; margin-left: 30px">
              <div>{{ item.noticeTitle }}</div>
              <div>创建时间：{{ item.createTime }}</div>
            </div>
          </div>
        </div>
        <!-- <div class="tit flex topbar">
          <span style="cursor: pointer; color: #fff; font-size: 16px"
            >{{ obj.name ? obj.name : '用户权限' }} <i></i
          ></span>
          <span class="ones"></span>
        </div>
        <div class="cooperate rightone">
          <div class="img" v-html="noticedata.noticeContent ? noticedata.noticeContent : ''"></div>
        </div> -->
      </div>
      <div class="rightcss" v-if="flag">
        <div class="tit flex topbar" style="width: 890px">
          <span style="cursor: pointer; color: #fff; font-size: 16px"
            >{{ noticedata.noticeTitle ? noticedata.noticeTitle : '用户权限' }} <i></i
          ></span>
          <span class="ones"></span>
        </div>
        <div class="cooperate rightone" style="width: 910px">
          <div class="img" v-html="noticedata.noticeContent ? noticedata.noticeContent : ''"></div>
        </div>
      </div>
    </div>
    <div style="padding-top: 20px"></div>
  </div>
  <div>
    <Footer :home="true" />
  </div>
</template>
    
    <script>
import kefu from '../../api/kefu';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { computed, reactive, watch, ref, toRefs, onMounted, markRaw } from 'vue';
export default {
  setup() {
    const router = useRouter();
    const state = reactive({
      arr: [
        {
          name: '交易声明',
          id: 1,
          path: '',
          noticeType: 'jysm'
        },
        {
          name: '找回包赔',
          id: 3,
          path: '',
          noticeType: 'zhbp'
        },
        {
          name: '挂牌代售',
          id: 4,
          path: '',
          noticeType: 'gpds'
        },
        {
          name: '交易流程',
          id: 5,
          path: '',
          noticeType: 'jylc'
        },
        {
          name: '常见问题',
          id: 6,
          path: '',
          noticeType: 'cjwt'
        },
        {
          name: '新闻公告',
          id: 7,
          path: '',
          noticeType: 'xwgg'
        },
        {
          name: '追回案例',
          id: 8,
          path: '',
          noticeType: 'zhgg'
        }
      ],
      ids: 1,
      obj: {
        name: '',
        noticeType: ''
      },
      noticedata: {},
      noticedatass: [],
      flag: false
    });

    const getnotice = async (type, id) => {
      console.log(type, id);
      const res = await kefu.getNotices(type);
      console.log(res);
      state.noticedatass = [];
      state.noticedatass = res;
      if (id) {
        state.noticedatass.forEach((item) => {
          if (item.noticeId == id) {
            state.flag = true;
            state.noticedata = {};
            state.noticedata = item;
          }
        });
      }
    };

    const changefn = async (item) => {
      state.flag = false;
      state.ids = item.id;
      state.obj = item;
      const res = await kefu.getNotices(state.obj.noticeType);
      console.log(res);
      state.noticedatass = [];
      state.noticedatass = res;
      //   if (item.id == 7) {
      //     router.push('/index/tousu');
      //   } else if (item.id == 8) {
      //     router.push('/index/other/contactkefu');
      //   }
    };

    const getInfo = (item) => {
      state.flag = true;
      state.noticedata = item;
    };

    watch(
      () => router.currentRoute.value.query,
      (newValue, oldValue) => {
        // let obj = router.currentRoute.value.query;
        // state.ids = obj.id;
        // state.obj.name = obj.name;
        // state.obj.noticeType = obj.notice;
        // getnotice(state.obj.noticeType);
      },
      { deep: true }
    );

    onMounted(() => {
      let obj = router.currentRoute.value.query;
      console.log('123333', obj);

      if (!obj.id) {
        state.obj.name = '交易声明';
        state.obj.noticeType = 'jysm';
        state.ids = 1;
      } else {
        state.arr.forEach((item) => {
          if (item.name == obj.name) {
            state.ids = item.id;
            state.obj.name = item.name;
            state.obj.noticeType = item.noticeType;
          }
        });
      }

      getnotice(state.obj.noticeType, obj.id);
    });

    return {
      ...toRefs(state),
      getnotice,
      changefn,
      getInfo
    };
  }
};
</script>
    
    <style lang="less" scoped>
.main {
  padding-top: 30px;
  margin: 0 auto;
  background-color: #1c1632;

  .all {
    margin: 0 auto;
    width: 1200px;
    min-height: 100px;
    display: flex;
    justify-content: space-between;
    .leftcss {
      width: 230px;
      height: 461px;
      padding: 0 10px;
      border: 1px solid #2e2a44;
      .one {
        height: 50px;
        line-height: 50px;
        text-align: center;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 18px;
        cursor: pointer;
        border-radius: 10px;
        .title {
          margin-left: 10px;
          color: #fff;
          font-size: 14px;
        }
      }

      .navleft {
      }

      .navleft:last-child {
        border-bottom: 0;
      }

      .one:hover {
        background-color: #452933;
      }
    }
    .rightcss {
      width: 930px;
      min-height: 100px;

      .nav {
        padding-top: 20px;
        height: 135px;
        padding-bottom: 20px;
        border-bottom: 1px solid #2e2a44;
        display: flex;
        align-items: center;
      }
      .tit {
        width: 890px;
        font-size: 14px;
        font-weight: 400;
        color: #666;
        line-height: 50px;
        background-color: #1c1632;
        border-top: 1px solid #2e2a44;
        border-left: 1px solid #2e2a44;
        border-right: 1px solid #2e2a44;
        border-bottom: 0 solid #000;
        padding: 0 15px;
      }

      .rightone {
        width: 780px;
        min-height: 432px;
        padding: 0 15px;
        background-color: #1c1632;
        border: 1px solid #2e2a44;
        border-top: 0 solid #000;
        padding: 10px;
      }
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

.active {
  height: 40px;
  line-height: 40px;
  background-color: #452933 !important;
  color: #fff !important;
}

::v-deep .el-breadcrumb__inner {
  color: #fff;
}

::v-deep .el-breadcrumb__inner.is-link {
  color: #fff !important;
}

:deep(.location .el-breadcrumb__inner) {
  color: #fff;
}

:deep(.location .el-breadcrumb__inner.is-link) {
  color: #fff !important;
}
</style>