<template>
  <el-dialog
    :visible.sync="isShow"
    :close-on-click-modal="false"
    width="800px"
    :title="title"
    draggable
    @close="cancel"
  >
    <div class="search_box">
      <el-input
        placeholder="请输入关键词进行搜索"
        v-model="keyword"
        style="width: 400px"
        @change="searchFn"
      ></el-input>
      <el-button type="primary" class="btn" @click="searchFn">搜索</el-button>
      <el-button
        type="primary"
        plain
        class="btn"
        @click="back"
        v-show="pageState == 3"
        >返回</el-button
      >
    </div>
    <!-- v-infinite-scroll="nextPage" -->
    <div
      v-show="pageState == 1 || pageState == 3"
      class="msg_history_box"
      ref="msgListBox"
      v-infinite-scroll="nextPage"
      v-loading="loading"
    >
      <div
        class="msg_item"
        :id="'msg_' + msg.id"
        v-for="(msg, i) in msgList"
        :key="i"
      >
        <div class="other_msg msg" v-if="msg.formUser != loginUserInfo.hxId">
          <div class="avatar" style="width: 66px;height: 86px;">
            <img
              :src="msg.fromUserAvatar ? msg.fromUserAvatar : avatars"
              alt="头像"
              style="width: 50px; height: 50px"
            />
          </div>
          <div class="msg_cont">
            <div class="name">{{ msg.fromUserName||msg.formUser }}
            <span style="margin-left:15px">{{msg.createTime}}</span>
            </div>
            <div class="msg_info">
              <div class="txt_msg" v-if="msg.type == 'text'">
                <!-- {{ msg.content }} -->
                <span v-html="messageStyle(msg.content)"></span>
              </div>
              <div class="txt_msg" v-if="msg.type == 'custom'">
                <div class="img_msg" v-if="msg.customType == 'image'">
                  <el-image
                    fit="contain"
                    :src="msg.content"
                    class="img"
                    :preview-src-list="viewImgUrls"
                    :initial-index="getImgIndex(msg.content)"
                  ></el-image>
                </div>
                <div class="img_msg" v-if="msg.customType == 'video'">
                  <video :src="msg.content" class="img video" controls></video>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-show="pageState == 2" class="msg_history_box msg_search_box" v-loading="loading">
      <div class="msg_item" v-for="(msg, i) in searchResultList" :key="i">
        <div class="other_msg msg">
          <div class="avatar" style="width: 66px;height: 66px;">
            <img
              :src="msg.fromUserAvatar ? msg.fromUserAvatar : avatars"
              alt="头像"
              style="width: 50px; height: 50px"
            />
          </div>
          <div class="msg_cont">
            <div class="name">
              {{ msg.fromUserName }}
              <span class="time">{{ msg.createTime }}</span>
            </div>
            <div class="msg_info">
              <div class="txt_msg" v-if="msg.type == 'text'">
                {{ msg.content }}
              </div>
            </div>
          </div>
        </div>
        <div>
          <el-button type="text" @click="viewSXW(msg)">查看上下文</el-button>
        </div>
      </div>
    </div>
  </el-dialog>
</template>


<script>
// 自定义接口
import avatars from "../../../assets/images/avatar.png";
import { getHistoryMessageCustom, searchInSession } from "../../../api/custom";
export default {
    props: {
    showDialog: {
      type: Boolean,
      default: () => false,
    },
    sessionId:{
      type:String,
      default:() => {}
    }
  },
  data() {
    return {
      avatars,
      msgList: [],
      isHasPre: true,
      isHasNext: true,
      chatType: "singleChat",
      loading: false,
      // sessionId: "",
      searchResultList: [],
      pageState: 1,
      loginUserInfo: '',
      msgListBox: {},
      scrollTop: {},
      keyword: "",
      remarkList: '',
      time: "",
      title:'聊天记录',
      isShow:false
    };
  },
  computed: {
    messageStyle() {
      return (msg) => {
        // 将消息中的/n 替换成<br/>
        msg = msg.replace(/\n/g, "<br/>");
        if (msg.indexOf("@") > -1) {
          let arr1 = msg.split("@");
          let arr2 = arr1[1].split(" ");
          let user = "@" + arr2[0] + " ";
          arr2.shift();
          let lastmsg = "";
          if (arr2.length >= 1) {
            arr2.map((r) => {
              lastmsg = lastmsg + r;
            });
          }
          return `${arr1[0]}<span style="color:cornflowerblue;cursor:pointer">${user}</span>${lastmsg}`;
        } else {
          return msg;
        }
      };
    },
    viewImgUrls() {
      let arr = [];
      if (this.msgList && this.msgList.length) {
        for (let i = 0; i < this.msgList.length; i++) {
          let msg = this.msgList[i];
          if (msg.customType === "image") {
            arr.push(msg.content);
          }
        }
      }
      return arr;
    },
    getImgIndex() {
      return (img) => {
        return this.viewImgUrls.indexOf(img);
      };
    },

  },
  created() {
  },
  methods: {
    scrollListenerHandler() {
      this.scrollTop = this.$refs.msgListBox.scrollTop;
        console.log(this.$refs.msgListBox.scrollTop);
    },
    prePage() {
      console.log("%c Line:108 🍿 滚动到顶^^^^^^^^^", "color:#42b983");
      let firstId;
      if (this.msgList && this.msgList.length) {
        firstId = this.msgList[0].id;
      }
      this.getMsgListPage(firstId, 1, 20);
    },
    nextPage() {
      // 最后一个消息id
      // 滚动到底部
      console.log("%c Line:108 🍿 滚动到底部", "color:#42b983");
      if (this.msgList && this.msgList.length) {
        let lastId = this.msgList[this.msgList.length - 1].id;
        this.getMsgListPage(lastId, 2, 20);
      }
    },
    // 获取分页消息
    getMsgListPage(id, type, limit) {
      if (
        (this.isHasPre && type == 1) ||
        (this.isHasNext && type == 2) ||
        type == 3
      ) {
        this.loading = true;
        getHistoryMessageCustom(this.sessionId, id, type, limit).then((res) => {
      
          this.loading = false;
          if (type == 1) {
            // 上一页
            this.msgList = res.rows.concat(this.msgList);
            this.scrollMessageList(id);
            if (res.rows.length == 0) {
              this.isHasPre = false;
            }
          } else if (type == 2) {
            // 下一页
            this.msgList = this.msgList.concat(res.rows);
            if (res.rows.length == 0) {
              this.isHasNext = false;
            }
          } else if (type == 3) {
            this.msgList = res.rows;
          }
        });
      }
    },
    //搜索记录
    searchFn() {
      // keyword
      searchInSession(this.sessionId, this.keyword).then((res) => {
        this.pageState = 2;
        this.searchResultList = res.rows;
      });
    },
    viewSXW(msg) {
      //
      this.isHasPre = true;
      this.isHasNext = true;
      getHistoryMessageCustom(this.sessionId, msg.id, 3, 10).then((res) => {
        this.pageState = 3;
        this.msgList = res.rows;
        this.scrollMessageList(msg.id);
      });
    },
    back() {
      if (this.pageState == 3) {
        this.pageState = 2;
      }
    },
    cancel() {
      this.$emit("close");
    },
    scrollMessageList(msgId) {
      //direction滚动方向 bottom向下滚动 normal向上滚动

      this.$nextTick(() => {
        const currentMsgEl = document.getElementById("msg_" + msgId);
        currentMsgEl.scrollIntoView(true);
      });
    },
  },


  watch: {
    "showDialog": {
      handler(newVal, oldVal) {
        console.log("%c Line:302 🥒 newVal", "color:#ed9ec7", newVal);
        if (newVal == true) {
          this.isShow = true
          this.pageState = 1;
          this.$nextTick(() => {
            this.isHasPre = true;
            this.msgListBox.scrollTop = 10000;
            this.$refs.msgListBox.addEventListener("scroll", this.scrollListenerHandler);
          });
          this.keyword = "";
        
          if (this.sessionId) {
            getHistoryMessageCustom(this.sessionId).then((res) => {
              console.log(
                "%c Line:56 🍧 聊天历史自己录初始化十条res",
                "color:#2eafb0",
                res
              );
              this.msgList = res.rows;
              if (res.rows && res.rows.length) {
                this.scrollMessageList(res.rows[res.rows.length - 1].id);
              }
            });
          }
        } else {
          this.isShow = false
        }
      },
      immediate: true,
      deep: true,
    },
    'scrollTop': {
      handler(newVal, oldVal) {
        console.log('1111111',newVal);
        // console.log(newVal111,this.scrollTop);
        // console.log("%c Line:103 🍪 newVal", "color:#ed9ec7", newVal.value);
        if (newVal == 0) {
        //   // 请求上一页
          this.prePage();
        }
      },
      immediate: true,
      deep: true,
    },
  },
};
</script>

<style lang="scss" scoped>
.search_box {
  display: flex;
  // margin-bottom: 10px;
  padding: 10px;
  background-color: #fff;

  .btn {
    margin-left: 10px;
  }
}

.msg_history_box {
  padding: 10px;
  padding-top: 10px;
  width: 100%;
  height: calc(100vh - 400px);
  overflow-y: auto;
  // border: 1px solid #e0dfdf;
  box-sizing: border-box;
  // background-color: #fff;
  border-top: 3px solid #79bbff;
  background-color: #f9f9f9;

  .msg_item {
    display: flex;
    justify-content: space-between;
    margin-bottom: 5px;
  }

  .avatar {
    img {
      border-radius: 5px;
      width: 50px;
      height: 50px;
    }
  }

  .msg {
    display: flex;

    .msg_cont {
      padding-top: 5px;
      max-width: 500px;
    }

    .name {
      color: #999;
      margin-bottom: 5px;
    }
  }
  .msg_info {
    background-color: #fff;
    padding: 10px;
    border-radius: 5px;
    line-height: 22px;
  }

  .mine_msg {
    .avatar {
      margin-left: 10px;
    }

    .name {
      text-align: right;
    }
    .msg_info {
      background-color: #c1e3fc;
      padding: 10px;
      border-radius: 5px;
      line-height: 22px;
    }
  }

  .other_msg {
    .avatar {
      margin-right: 10px;
    }
    .msg_info {
      background-color: #fff;
      padding: 10px;
      border-radius: 5px;
      line-height: 22px;
    }
  }

  .img_msg {
    // max-height: 200px;
    // width: 200px;
    .img {
      height: 200px;
      width: 100%;
    }
    .video {
      width: 350px;
    }
  }
}

.msg_search_box {
  background-color: #fff;
  .avatar {
    margin-right: 10px;
  }
  .msg_info {
    padding-left: 0 !important;
    padding-top: 0 !important;
  }
  .msg_item {
    padding: 5px 15px;
    border-bottom: 1px solid #ccc;
  }

  .time {
    margin-left: 10px;
  }
}
</style>