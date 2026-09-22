<template>
  <div class="app-container" v-loading="loading">
    <h2>查询全站商品</h2>
    <div class="search-box">
      <el-input placeholder="请输入xyr开头的编号ID进行查询。【外部对接编号】" v-model="input2" @keyup.enter.native="search">
        <template slot="append">
          <div class="pointer" @click="search">
            <span class="el-icon-search"></span>
            <span>搜索</span>
          </div>
        </template>
      </el-input>
      <span class="teach pointer" @click="openTeach">
        <span class="el-icon-question"></span>
        使用教程
      </span>
    </div>
    <div class="account-info" v-if="info">
      <!-- 商品ID，游戏类型，区服，实名状态，英雄皮肤数量，售价，简介内容 商品状态 -->
      <div @click="openUrl" class="pointer">
        <div class="flex attr-box">
          <p class="text-box">
            <span class="tit">商品ID：</span>
            <span class="cont">{{ info.id }}</span>
          </p>
          <p class="text-box">
            <span class="tit">游戏名称：</span>
            <span class="cont">{{ info.gameName }}</span>
          </p>
          <p class="text-box">
            <span class="tit">区服：</span>
            <span class="cont">{{ info.qufu }}</span>
          </p>
          <p class="text-box" v-for="(item, i) in customAttr" :key="i">
            <span class="tit">{{ item.key }}：</span>
            <span class="cont">{{ item.value }}</span>
          </p>


          <p class="text-box">
            <span class="tit">售价：</span>
            <span class="cont">￥{{ info.price }}</span>
          </p>
        </div>

        <p>
          <span class="tit">商品标题：</span>
          <span class="cont">{{ info.title }}</span>
        </p>
        <p>
          <span class="tit">简介内容：</span>
          <span class="cont">{{ info.des }}</span>
        </p>
      </div>

      <p>
        <span class="tit">商品状态：</span>
        <span class="cont" v-if="info.saleState == 1" style="color:#00dd33">{{ saleState[info.saleState] }}</span>
        <span class="cont" v-else style="color:#dd0033">{{ saleState[info.saleState] }}</span>
      </p>
      <div class="foot" v-show="isShow">
        <el-button type="primary" @click="picking">提号</el-button>
      </div>
      <span class="close-icon el-icon-close pointer" @click="closeInfo"></span>
    </div>

    <h5Dialog ref="h5Dialog"></h5Dialog>
    <pickDialog ref="pickDialog"></pickDialog>
    <teachDialog ref="teachDialog"></teachDialog>
    <div class="order-page">
      <orderPage ref="orderPage"></orderPage>
    </div>
  </div>
</template>

<script>
import h5Dialog from "./h5Dialog.vue";
import teachDialog from "./teachDialog.vue";
import pickDialog from "./pickDialog.vue";
import orderPage from "../order/index.vue";
import searchApi from "@/api/pickUp/search.js";
import gameConfigApi from "../../../api/gameConfig";
export default {
  name: "pick",
  components: {
    h5Dialog,
    pickDialog,
    orderPage,
    teachDialog
  },
  data() {
    return {
      loading: false,
      input2: "",
      url:
        "http://www.ikbh.top/show.html?YXLX_ID=%E7%8E%8B%E8%80%85%E8%8D%A3%E8%80%80&id=false&ZHID=123322",
      isShow: false,
      info: null,
      saleState: {
        0: '待审核',
        1: '未出售',
        2: '交易中',
        3: '已出售',
        4: '已下架',
        5: '问题账号'
      },
      customAttr: []
    };
  },
  created() { },
  methods: {
    search() {
      // 请求后台
      this.loading = true;
      searchApi
        .search({ id: this.input2 })
        .then(res => {
          this.loading = false;
          if (res.code == 200) {
            this.info = res.data;
            this.info.qufu =
              `${this.info.systemName || ''} / ${this.info.carrierName || ''} / ${this.info.groupName || ''} / ${this.info.serverName || ''}`
            this.getAttrList()
            this.isShow = true;
          }
        })
        .catch(err => {
          this.loading = false;
        });
    },
    // 获取游戏属性列表
    getAttrList() {
      this.loading = true
      gameConfigApi.getGameConfig({ gameId: this.info.gameId }).then(res => {
        this.loading = false
        if (res.code == 200) {
          let attrs = res.rows
          let arr = []
          if (attrs.length) {
            attrs.forEach(r => {
              if ((r.fieldType == 'input' || r.fieldType == 'number') && r.isEnable == '0') {
                // this.info[r.aavName]
                console.log("%c Line:138 🍔 this.info[r.aavName]", "color:#2eafb0", this.info[r.aavName]);
                arr.push({
                  key: r.fieldName,
                  value: this.info[r.aavName]
                })
              }

            });
            this.customAttr = JSON.parse(JSON.stringify(arr))
            console.log("%c Line:145 🥖 this.customAttr", "color:#ed9ec7", this.customAttr);
          }
        }
      });
    },
    openUrl() {
      // this.$refs.h5Dialog.open(this.h5Url + this.info.id);
    },
    // 查看教程
    openTeach() {
      this.$refs.teachDialog.open();
    },
    // 关闭详情
    closeInfo() {
      this.info = null;
    },
    ff5() {
      this.search()
      this.$refs.orderPage.getList();
    },
    picking() {
      if (this.info.saleState != 1) {
        this.$alert(
          `该账号状态是${this.saleState[this.info.saleState]}，不能提号！`,
          "提示",
          {
            confirmButtonText: "确定",
            callback: action => { }
          }
        );
        return;
      }
      this.$refs.pickDialog.open(this.info);
    }
  }
};
</script>
<style scoped lang="scss">
.app-container {
  position: relative;

  .teach {
    position: absolute;
    top: 36px;
    right: 10px;
    color: #666;
  }
}

h2 {
  text-align: center;
}

.pointer {
  cursor: pointer;
}

.search-box {
  width: 700px;
  margin: 0 auto;
}

.account-info {
  position: relative;
  min-height: 200px;
  max-height: 500px;
  max-width: 1200px;
  margin: 0 auto;
  overflow-y: auto;
  // border: 1px solid #e3e3e3;
  margin-top: 20px;
  padding: 20px;
  border-radius: 10px;
  margin-bottom: 20px;
  box-shadow: 2px 2px 5px 1px #e3e3e3;

  p {
    display: flex;
    margin: 5px 0;

    .tit {
      font-weight: 600;
      width: 120px;
      flex-shrink: 0;
      text-align: right;
    }
  }
  .attr-box{
    flex-wrap: wrap;
  }
  .text-box {
    width: 350px;
    display: flex;
    flex-shrink: 0;
    .tit {
      display: inline-block;
    }
  }

  .close-icon {
    font-size: 24px;
    color: #999;
    position: absolute;
    top: 10px;
    right: 10px;
  }
}

.foot {
  text-align: center;
}

.order-page {
  // border: 1px solid #e3e3e3;
  box-shadow: 2px 2px 7px 2px #e3e3e3;
  border-radius: 4px;
  margin-top: 20px;
}
</style>
