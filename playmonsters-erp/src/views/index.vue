<template>
  <!-- <div class="app-container home" :class="{ colorfff: currentTab == 3 || currentTab == 4 }"> -->
  <!-- <div class="pcUrl" @click="copy">
      复制链接
      </div>
    <el-tabs v-model="currentTab" @tab-click="tabChange" v-loading="loading">
      
      <el-tab-pane :label="tab.name" :name="tab.value" v-for="tab in tabsList" :key="tab.value">
        <div v-if="currentTab == 1 || currentTab == 2">
          <div class="banner">
            <img src="/static/img/banner1.png" alt="怪兽玩代售">
          </div>
          <div class="videos-box" v-if="videoListList && videoListList.length">
            <div class="title-box flex">
              <div class="icon"></div>
              <div class="tit">功能介绍</div>
            </div>
            <el-carousel indicator-position="none" :autoplay="false" arrow="always">
              <el-carousel-item v-for="(item, index) in videoListList" :key="index">
                <div class="carousel-item-box">
                  <div v-if="item && item.length">
                    <div class="img-box" v-for="img in item" :key="img.id">
                      <img :src="img.imgUrl" alt="" />
                      <div class="videoTit">{{ img.name }}</div>
                      <div class="play-icon" @click="openVideo(img)" >
                        <i class="el-icon-video-play"></i>
                      </div>
                      <div class="zhezhao"></div>
                      
                    </div>
                  </div>
                </div>
              </el-carousel-item>
            </el-carousel>
          </div>
          <div class="msg-box flex">
            <div class="msg-txt flex">
              <div class="icon"></div>
              <div class="titt flex">
                <div class="tit">系统公告：</div>
                <div class="txt" v-if="notices">{{ notices.noticeTitle }}</div>
              </div>
            </div>

            <div class="rt el-icon-arrow-right">
            </div>
          </div>
        </div>
        <div v-if="currentTab == 3 || currentTab == 4">
          <div class="imgs-box" v-if="videosList && videosList.length">
            <img :src="item.imgUrl" alt="" v-for="item in videosList" :key="item.id">
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
    <el-dialog title="视频" :visible.sync="dialogVisible" width="800px" @close="dialogVisible = false; currentVideo = null">
      <div v-if="currentVideo">
        <video class="video-box" autoplay controls="controls" :src="currentVideo.url"></video>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false; currentVideo = null">关闭</el-button>
      </span>
    </el-dialog> -->
  <!-- </div> -->
  <!-- <div class="main">
    <div class="title">统计访问量</div>
    <div style="display: flex; justify-content: center; margin-top: 33px">
      <div>
        <el-date-picker
          style="width: 300px"
          v-model="timer"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
        >
        </el-date-picker>
      </div>
      <div style="margin-left: 12px">
        <el-button
          type="primary"
          icon="el-icon-search"
          @click="getTodayInfo()"
          class="zdy-btn"
          >搜索</el-button
        >
        <el-button
          type="info"
          icon="el-icon-refresh"
          plain
          @click="resetQuery"
          class="zdy-btn zdy-btn-refresh"
          >重置</el-button
        >
      </div>
    </div>
    <div style="margin-top: 20px">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="name" label="渠道来源" align="center">
        </el-table-column>
        <el-table-column prop="count" label="访问次数" align="center">
        </el-table-column>
      </el-table>
    </div>
  </div> -->
  <div></div>
</template>

<script>
import homeApi from "@/api/home/index"
import videosManage from '@/api/PCWebImages/videosManage'
import { getFangwenJilu } from '@/api/system/user'
export default {
  name: "Index",
  data() {
    return {
      loading: false,
      tabsList: [{
        name: '商家联盟saas系统',
        value: '1'
      },
      {
        name: '独立版系统',
        value: '2'
      },
      {
        name: '合作商家',
        value: '3'
      },
      {
        name: '系统客户',
        value: '4'
      }
      ],
      currentTab: '1',
      notices: null,
      videosList: [],
      videoListList: [],
      dialogVisible: false,
      currentVideo: null,
      tableData: [],
      timer: [],
      form: {
        createTimeStart: null,
        createTimeEnd: null
      }
    };
  },
  created() {
    this.getNoticeList()
    this.getvideosManage()
    this.getTodayInfo()
  },
  components: {},
  methods: {
    resetQuery() {
      this.form = {
        createTimeStart: null,
        createTimeEnd: null
      }
      this.timer = []
      this.getTodayInfo()
    },
    async getTodayInfo() {
      if (this.timer && this.timer.length == 2) {
        this.form.createTimeStart = this.timer[0] + " 00:00:00";
        this.form.createTimeEnd = this.timer[1] + " 23:59:59";
      } else {
        this.form.createTimeStart = "";
        this.form.createTimeEnd = "";
      }
      const res = await getFangwenJilu(this.form)
      console.log('res', res);
      this.tableData = res.data
    },
    // 复制推广链接
    copy() {
      this.$copyText('http://notice.yududs.com').then(() => {
        this.$message('已复制到剪切板')
      })
    },
    // 打开视频
    openVideo(item) {
      this.currentVideo = item
      this.dialogVisible = true
    },
    // 获取视频教程列表
    getvideosManage() {
      let obj = {
        systemType: ''
      }
      obj.systemType = this.currentTab
      videosManage.getList(obj).then(res => {
        this.videosList = res.rows
        if (this.currentTab == 1 || this.currentTab == 2) {
          this.setVideoData()
        }
      })
    },
    // 组装视频轮播数据
    setVideoData() {
      let j = 0
      let arr = []
      arr[j] = []
      this.videosList.forEach((item, i) => {
        if (!arr[j]) {
          arr[j] = []
        }
        arr[j].push(item)
        if ((i + 1) % 4 == 0) {
          j++
        }
      })
      this.videoListList = arr
      console.log("%c Line:83 🍊 this.videoListList", "color:#e41a6a", this.videoListList);
    },
    // tab切换
    tabChange() {
      this.getvideosManage()
    },
    getNoticeList() {
      homeApi.getNoticeList({
        status: 0,
        noticeType: '2'
      }).then(res => {
        console.log('%c 🍸 res: ', 'font-size:20px;background-color: #E41A6A;color:#fff;', res);
        if (res.rows && res.rows.length) {
          this.notices = res.rows[0]
        }

      })
    }
  }
};
</script>

<style scoped lang="scss">
.app-container {
  min-width: 940px;
  max-width: 1720px;
  background-color: #f8f8f8;
  padding-bottom: 50px;
  color: #2c2c2c;
  position: relative;

  &.colorfff {
    background-color: #fff;
  }
}
.pcUrl {
  // height: 20px;
  // width: 200px;

  position: absolute;
  top: 30px;
  right: 30px;
  z-index: 99;
  color: #409eff;
  cursor: pointer;
  a {
    text-decoration: underline;
    color: #409eff;
  }
}

.banner {
  width: 100%;
  margin-bottom: 20px;

  img {
    width: 100%;
  }
}

.videos-box,
.msg-box {
  width: 98%;
  margin: 0 auto;
  margin-bottom: 20px;
  border-radius: 10px;

  background-color: #fff;

  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.15);
}

.title-box {
  font-size: 24px;
  height: 50px;
  border-bottom: 1px solid #e1e1e1;
  align-items: center;
  margin-bottom: 10px;
  padding-left: 20px;

  .icon {
    width: 40px;
    height: 40px;
    background: url("/static/icon/Frame 18.png") 100% 100%;
    margin-right: 10px;
  }
}

.carousel-item-box {
  padding: 40px 0;

  > div {
    width: calc(100% - 120px);
    margin: 0 auto;
    display: flex;
    flex-wrap: nowrap;
    overflow: hidden;
  }

  .img-box {
    width: 25%;
    position: relative;
    .zhezhao {
      width: calc(100% - 20px);
      height: 100%;
      background-color: rgba(0, 0, 0, 0.5);
      position: absolute;
      left: 10px;
      top: 0;
      border-radius: 10px;
    }
    .videoTit {
      position: absolute;
      width: 100%;
      text-align: center;
      bottom: 10px;
      left: 0;
      font-size: 18px;
      color: #fff;
      z-index: 9;
    }
    .play-icon {
      font-size: 40px;
      position: absolute;
      color: #fff;
      left: calc(50% - 20px);
      top: calc(50% - 20px);
      z-index: 9;
      cursor: pointer;
    }

    img {
      display: block;
      width: calc(100% - 20px);
      margin: 0 auto;
      height: 201px;
      border-radius: 10px;
      cursor: pointer;
    }
  }
}

.msg-box {
  height: 125px;
  margin-top: 30px;
  padding: 20px;
  align-items: center;
  flex-wrap: nowrap;
  justify-content: space-between;

  .icon {
    flex-shrink: 0;
    width: 40px;
    height: 40px;
    background: url("/static/icon/Frame 19.png") 100% 100%;
  }

  .tit {
    font-size: 24px;
    line-height: 30px;
  }

  .txt {
    line-height: 30px;
  }

  .msg-txt {
    width: 88%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    flex-wrap: nowrap;
    align-items: center;
    .titt {
      margin-left: 10px;
    }
  }

  .rt {
    font-size: 30px;
  }
}

.imgs-box {
  img {
    width: 184px;
    height: 184px;
    border-radius: 12px 12px 12px 12px;
    margin-right: 30px;
    margin-bottom: 40px;
  }
}

.video-box {
  width: 100%;
  height: auto;
}

.app-container ::v-deep .el-tabs__item.is-active {
  padding-left: 20px;
  color: #fff;
  text-align: center;
  background: #409eff;
  border-radius: 8px 8px 0px 0px;
  opacity: 1;
}

::v-deep .el-tabs--top .el-tabs__item.is-top:nth-child(2) {
  padding-left: 20px;
}

::v-deep .el-tabs__nav-wrap::after {
  background-color: #409eff;
}

::v-deep .el-tabs__item {
  color: #409eff;
  font-size: 24px;
}

::v-deep .el-carousel__arrow {
  background-color: #c1c1c1;
}

::v-deep .el-carousel__arrow:hover {
  background-color: #409eff;
}

::v-deep .el-tabs--top .el-tabs__item.is-top:last-child {
  padding-right: 20px;
}

// .main {
//   padding: 30px;
//   .title {
//     text-align: center;
//     font-size: 18px;
//     font-weight: 700;
//   }
// }
</style>

