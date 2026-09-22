<template>
  <el-dialog
    class="pick-dialog"
    title="换绑信息"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    width="700px"
    top="30vh"
  >
    <div class="form-box" v-loading="loading">
      <div class="account-info" v-if="info" ref="infobox">
        <div class>
          <p class="text-box">
            <span class="tit">商品ID：</span>
            <span class="cont">{{info.id}}</span>
          </p>
          <p class="text-box">
            <span class="tit">游戏类型：</span>
            <span class="cont">{{info.type}}</span>
          </p>
          <p class="text-box">
            <span class="tit">区服：</span>
            <span class="cont">{{info.regional}}</span>
          </p>
          <!-- <p class="text-box">
            <span class="tit">实名状态：</span>
            <span class="cont">{{info.realName}}</span>
          </p>
          <p class="text-box">
            <span class="tit">英雄数量：</span>
            <span class="cont">{{info.heros}}</span>
          </p>
          <p class="text-box">
            <span class="tit">皮肤数量：</span>
            <span class="cont">{{info.skins}}</span>
          </p> -->
          <p class="text-box">
            <span class="tit">平台售价：</span>
            <span class="cont red">￥{{info.price}}</span>
          </p>
          <p class="text-box">
            <span class="tit">分销售价：</span>
            <span class="cont red">￥{{info.shopprice}}</span>
          </p>
          <p>
            <span class="tit">简介内容：</span>
            <span class="cont">{{info.des}}</span>
          </p>

          <p class="text-box">
            <span class="tit">账号：</span>
            <span class="cont">{{info.account}}</span>
          </p>
          <p class="text-box">
            <span class="tit">密码：</span>
            <span class="cont">{{info.pwd}}</span>
          </p>
          <p>
            <span class="tit">换绑手机号：</span>
            <span class="cont">{{info.tel}}</span>
          </p>
          <p>
            <span class="tit">买家备注：</span>
            <span class="cont red">{{info.remark||'无'}}</span>
          </p>
          <p>
            <span class="tit">注意事项：</span>
            <span class="cont red">{{info.supyremake||'无'}}</span>
          </p>
        </div>
        <p>
          <span class="tit">截图：</span>
          <span class="cont" v-if="info.jietuList&&info.jietuList.length">
            <el-image
              v-for="img in info.jietuList"
              :key="img"
              style="width: 100px; height: 100px"
              :src="img"
              :preview-src-list="info.jietuList"
            ></el-image>
          </span>
        </p>
        <p>
          <span class="tit">其他：</span>
          <span class="cont" v-if="info.otherList&&info.otherList.length">
            <el-image
              v-for="(img,ind) in info.otherList"
              :key="img"
              style="width: 100px; height: 100px"
              :src="img"
              :preview-src-list="info.otherList"
              :ref="img+ind"
            ></el-image>
          </span>
        </p>
      </div>
      <div class="copy">
        <el-button type="primary" size="mini" @click="keyCopy">一键复制</el-button>
      </div>
      <!-- <div>
        <div style="width:600px">
          <el-image
            v-for="(img,ind) in allList"
            :key="img"
            style="width: 500px;"
            :src="img"
            :preview-src-list="allList"
            :ref="img+ind"
          ></el-image>
        </div>
      </div> -->
      <div>
        <iframe src="/static/copy/show.html" id="copyFrame" name="copyFrame" ref="copyFrame" frameborder="0" style="width:500px;height:500px;" class="hidden"></iframe>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import orderApi from "@/api/pickUp/order.js";
export default {
  name: "bindInfo",
  data() {
    return {
      dialogVisible: false,
      orderId: "",
      info: null,
      loading: false,
      copyVlaue: "",
      allList: [],
      isShow:false
    };
  },
  created() {},
  methods: {
    open(id, flag) {
      this.orderId = id;
      this.dialogVisible = true;
      this.getDetail(id, flag);
    },
    getDetail(id,flag) {
      this.loading = true;
      if (flag) {
        orderApi.getDetail2(id).then(res => {
          this.loading = false;
          this.info = res.data;
        });
      } else {
        orderApi.getDetail(id).then(res => {
          this.loading = false;
          this.info = res.data;
        });
      }
    },
    // 复制商品信息
    keyCopy() {
      const obj1 = window.frames['copyFrame'] // 获得对应iframe的window对象
      // this.info={
      //       otherList : ['https://game.ikbh.top/ff8080818658f3d201865adcb6c702b121031FKU44S6-0-lp.jpg','https://game.ikbh.top/ff8080818658f3d201865adcbd1402b2210416122252-1-1200.jpg'],
      //       jietuList : ['https://game.ikbh.top/ff8080818658f3d201865adcb6c702b121031FKU44S6-0-lp.jpg','https://game.ikbh.top/ff8080818658f3d201865adcbd1402b2210416122252-1-1200.jpg'],
      //       des:'简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介'
      //     } 
          obj1.copyDatas = JSON.parse(JSON.stringify(this.info))  //传入
          obj1.domakeimg()
    }
  }
};
</script>
<style scoped lang="scss">
.red {
  color: #f56c6c;
  font-weight: 600;
}
.center {
  text-align: center;
}
.pick-dialog >>> .el-dialog {
  margin-top: 100px !important;
}
.form-box {
  max-height: 800px;
  overflow-y: auto;
}
.account-info {
  p {
    display: flex;
    margin: 8px 0;
    .tit {
      font-weight: 600;
      width: 100px;
      flex-shrink: 0;
      text-align: right;
    }
    .el-image {
      margin-right: 10px;
      margin-bottom: 10px;
    }
  }
  .text-box {
    display: inline-block;
    width: 300px;
    .tit {
      display: inline-block;
    }
  }
}
.copy {
  text-align: center;
  margin-bottom: 10px;
}
.hidden{
      	width: 0;
    	height: 0;
    	opacity: 0;
    	position: absolute;
    	top: -1000%;
    	left: -1000%;
    	z-index: -9999999;
      }
</style>
